/* Generated by Streams Studio: December 11, 2015 12:07:35 PM EST */
package com.ibm.streams.solr;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;

import com.ibm.streams.operator.AbstractOperator;
import com.ibm.streams.operator.OperatorContext;
import com.ibm.streams.operator.OutputTuple;
import com.ibm.streams.operator.StreamingInput;
import com.ibm.streams.operator.StreamingOutput;
import com.ibm.streams.operator.Tuple;
import com.ibm.streams.operator.TupleAttribute;
import com.ibm.streams.operator.Type.MetaType;
import com.ibm.streams.operator.log4j.TraceLevel;
import com.ibm.streams.operator.model.InputPortSet;
import com.ibm.streams.operator.model.InputPortSet.WindowMode;
import com.ibm.streams.operator.model.InputPortSet.WindowPunctuationInputMode;
import com.ibm.streams.operator.model.InputPorts;
import com.ibm.streams.operator.model.Libraries;
import com.ibm.streams.operator.model.OutputPortSet;
import com.ibm.streams.operator.model.OutputPorts;
import com.ibm.streams.operator.model.Parameter;
import com.ibm.streams.operator.model.PrimitiveOperator;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;

@Libraries("opt/downloaded/*")
@PrimitiveOperator(name="SolrDocumentSink", namespace="com.ibm.streamsx.solr",
description = SolrDocumentSink.DESCRIPTION )
@InputPorts({@InputPortSet(description="Port that ingests tuples", cardinality=1, optional=false, windowingMode=WindowMode.NonWindowed, windowPunctuationInputMode=WindowPunctuationInputMode.Oblivious)})
@OutputPorts(@OutputPortSet(description="Error Port" , optional=true) ) 
public class SolrDocumentSink extends AbstractOperator {
	SolrClient solrClient; 
	TupleAttribute<Tuple, String> uniqueKeyAttribute;
	
	private final Logger trace = Logger.getLogger(SolrDocumentSink.class
			.getCanonicalName());
	private boolean validErrorPort = false;
	private String collection;
	private String solrURL;
	private int documentCommitSize = 1;
	private List<SolrInputDocument> docBuffer = new ArrayList<SolrInputDocument>();;
	private int maxDocumentBufferAge = 10000;
	private Timer docBufferTimer = new Timer();; 
	private final String MAP_ATTRIBUTE = "atomicUpdateMap";
	private boolean inputMapExists = true;
	private ModifiableSolrParams requestParams;
	
	public static final String DESCRIPTION = 
			"This operator is used for writing tuples as Solr documents to a Solr collection. It takes in a set of attributes and a map on its import port. "
			+ "Those attributes are committed to a Solr collection on a configurable interval (time or number of tuples). "
			+ "The map (attribute: atomicUpdateMap) must be specified for an attribute the type of update: set, add, remove, removeregex, or inc. "
			+ "The map should NOT include the uniqueIdentifier attribute, as this is provided by a parameter. "
			+ "If no map is provided, all attributes will be committed as if the map were on \\\"set\\\". "
			+ "No ordering of the tuples within a buffer being committed is guaranteed.";
	
	@Parameter(optional = true, description = "Incoming attribute to be used as the unique id.")
    public void setUniqueKeyAttribute(TupleAttribute<Tuple, String> attributeName){
    	uniqueKeyAttribute = attributeName;
    }
    
    @Parameter(optional = false, description = "URL of Solr server. Example: http://g0601b02:8984/solr")
    public void setSolrURL(String value){
    	solrURL = value;
    }
    
    @Parameter(optional = false, description = "Solr collection to add documents to.")
    public void setCollection(String value){
    	collection = value;
    }
    
    @Parameter(optional = true, description="Number of tuples queued up before committing to Solr. Default: 1. If -1, buffer will never "
    		+ "be flushed based on size and will rely soley on time-based flushing.")
    public void setDocumentCommitSize(int value){
    	documentCommitSize = value;
    }
    
    @Parameter(optional = true, description="Max time allowed for the document buffer to fill before automatically flushing. "
    		+ "Time in milliseconds. Default: 10000. If -1, buffer will never flush based on time and will rely solely on count-based flushing.")
    public void setMaxDocumentBufferAge(int value){
    	maxDocumentBufferAge  = value;
    }
    
    @Parameter(optional = true, description = "Add Solr request parameters. Parameters should be comma separatated like so: \\\"name=value\\\",\\\"name=value\\\". "
    		+ "Use this parameter to use a custom updateRequestProcessorChain (\\\"update.chain=<chain-name>\\\").")
    public void setSolrRequestParams(List<String> values) throws MalformedSolrParameterException{
    	if (values != null)
    		requestParams = getRequestParams(values);
    }
    
    @Override
    public synchronized void shutdown() throws Exception {
        OperatorContext context = getOperatorContext();
        Logger.getLogger(this.getClass()).trace("Operator " + context.getName() + " shutting down in PE: " + context.getPE().getPEId() + " in Job: " + context.getPE().getJobId() );
        docBufferTimer.cancel();
        solrClient.close();       
        super.shutdown();
    }
    
	
	@Override
	public synchronized void initialize(OperatorContext context)
			throws Exception {
    	// Must call super.initialize(context) to correctly setup an operator.
		super.initialize(context);
        Logger.getLogger(this.getClass()).trace("Operator " + context.getName() + " initializing in PE: " + context.getPE().getPEId() + " in Job: " + context.getPE().getJobId() );
        String collectionURL = getCollectionURL();
        solrClient = new HttpSolrClient(collectionURL);
        
        if (context.getStreamingInputs().get(0).getStreamSchema().getAttributeIndex(MAP_ATTRIBUTE) < 0){
        	inputMapExists = false;
        }
        
        if (!context.getStreamingOutputs().isEmpty()){
        	StreamingOutput<OutputTuple> output = context.getStreamingOutputs().get(0);
        	if (output.getStreamSchema().getAttribute(0).getType().getMetaType() ==  MetaType.RSTRING){
        		validErrorPort = true;
        		trace.log(TraceLevel.INFO, "Found a valid error port to submit errors to.");
        	} 
        }
        
        if (!validErrorPort){
        	trace.log(TraceLevel.WARN, "No valid error port was found to submit errors to. Attribute must be of type rstring");
        }

        resetDocBuffer();        		
	}


    @Override
    public synchronized void process(StreamingInput<Tuple> stream, Tuple tuple)
            throws Exception {    	
    	SolrInputDocument doc = generateSolrDocFromTuple(tuple);
    	
    	docBuffer.add(doc);
    	
    	if (documentCommitSize > 0
    			&& docBuffer.size() >= documentCommitSize){
    		if(trace.isDebugEnabled())
    			trace.log(TraceLevel.INFO,"Comitting from process...");
	    	//Add the documents then clear
	    	commitAndClearBuffer();
    	}
    	
    }

    
    /*
     * Support functions of the initialize and process methods
     */
    
    
    private ModifiableSolrParams getRequestParams(List<String> reqParamStrings) throws MalformedSolrParameterException {
		ModifiableSolrParams params = new ModifiableSolrParams();
		for (String req : reqParamStrings){
			try{
				String paramName = req.split("=")[0];
				String paramValue = req.split("=")[1];
				params.add(paramName, paramValue);
			} catch (ArrayIndexOutOfBoundsException e){
				throw new MalformedSolrParameterException("Parameters should be comma separated and in the form name=value.");
			}
			
		}
		return params;
	}

	private String getCollectionURL() {
		if (!solrURL.endsWith("/"))
			solrURL += "/";
		String collectionURL = solrURL + collection;
		return collectionURL;
	}
    
    
	private SolrInputDocument generateSolrDocFromTuple(Tuple tuple) {
		SolrInputDocument doc = new SolrInputDocument();
		//if user provided update action map, use it.
    	if (inputMapExists){
	    	@SuppressWarnings("unchecked")
			Map<String,String> atomicUpdateMap = (Map<String, String>) tuple.getMap(MAP_ATTRIBUTE);
	    	
	    	for (Map.Entry<String, String> entry : atomicUpdateMap.entrySet()){
	    		addFieldToDocument(tuple, doc, entry);
	    	}
	    	
	    	if (uniqueKeyAttribute != null){
	    		addUniqueIdentifierFieldToDocument(tuple, doc);
	    	}
	    	
    	} else {
    		//default action is to set all attributes
    		for (String attributeName : tuple.getStreamSchema().getAttributeNames()){
    			doc.addField(attributeName, tuple.getObject(attributeName));
    		}
    	}
    	
    	return doc;
	}

	private void addUniqueIdentifierFieldToDocument(Tuple tuple, SolrInputDocument doc) {
		if (!doc.getFieldNames().contains(uniqueKeyAttribute.getAttribute().getName())){
			try{
	    		doc.addField(uniqueKeyAttribute.getAttribute().getName(), uniqueKeyAttribute.getValue(tuple));
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		trace.log(TraceLevel.ERROR, "Failed to add unique identifier field: " + e.getMessage());
	    		submitToErrorPort(e.getMessage());
	    	}
		} else {
			if (trace.isInfoEnabled()){
				trace.log(TraceLevel.INFO, "Unique field attribute: " 
					+ uniqueKeyAttribute.getAttribute().getName() 
					+ " was already found in the document to be added.");
			}
		}
	}

	private void addFieldToDocument(Tuple tuple, SolrInputDocument doc, Map.Entry<String, String> entry) {
		Map<String,Object> fieldModifier = new HashMap<>(1);
		String attributeName = entry.getKey();
		String atomicAction = entry.getValue();
		fieldModifier.put(atomicAction, tuple.getObject(attributeName));
		try {
			doc.addField(attributeName, fieldModifier);
		} catch (Exception e){
			e.printStackTrace();
			trace.log(TraceLevel.ERROR, "Failed to add field to document: " + e.getMessage());
			submitToErrorPort(e.getMessage());
		}
	}

	private synchronized void commitAndClearBuffer() {
		if(!docBuffer.isEmpty()){
			try {
				sendDocuments(docBuffer);
			} catch (Exception e){
				e.printStackTrace();
				trace.log(TraceLevel.ERROR, "Error while sending documents: " + e.getMessage() );
				submitToErrorPort(e.getMessage() + "\n docBuffer: " + docBuffer.toString());
			}
			resetDocBuffer();
		} else {
			if(trace.isInfoEnabled())
				trace.log(TraceLevel.INFO,"Document buffer was empty when deleting.");
		}
	}



	private void resetDocBuffer() {
		docBuffer.clear();
		resetDocBufferTimer();
	}

	private void resetDocBufferTimer() {
		if (maxDocumentBufferAge > 0){
			docBufferTimer.cancel();
			docBufferTimer = new Timer();
			docBufferTimer.schedule(new CommitAndClearAgedDocBuffer(), maxDocumentBufferAge);
		}
	}

	private void sendDocuments(List<SolrInputDocument> docBuffer2) throws SolrServerException, IOException {
		UpdateRequest request = new UpdateRequest();
		request.setParams(requestParams);
		request.add(docBuffer2);
		NamedList<Object> response = solrClient.request(request);
		if(trace.isInfoEnabled())
			trace.log(TraceLevel.INFO, "Attempting to commit. Solr Client add response status: " + response.toString());
		solrClient.commit();
	}
    
    private void submitToErrorPort(String error) {
		if (validErrorPort) {
			StreamingOutput<OutputTuple> streamingOutput = getOutput(0);
			OutputTuple otup = streamingOutput.newTuple();
			otup.setString(0, error);
			try {
				streamingOutput.submit(otup);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
    
    class CommitAndClearAgedDocBuffer extends TimerTask {
		@Override
		public void run() {
			if(trace.isDebugEnabled())
				trace.log(TraceLevel.DEBUG,"Comitting from TimerTask...");
			commitAndClearBuffer();
		}     	
    }
	
    
}
