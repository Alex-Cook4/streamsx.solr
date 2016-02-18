/* Generated by Streams Studio: December 11, 2015 12:07:35 PM EST */
package com.ibm.streams.solr;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.ibm.streams.operator.AbstractOperator;
import com.ibm.streams.operator.OperatorContext;
import com.ibm.streams.operator.OperatorContext.ContextCheck;
import com.ibm.streams.operator.OutputTuple;
import com.ibm.streams.operator.StreamingInput;
import com.ibm.streams.operator.StreamingOutput;
import com.ibm.streams.operator.Tuple;
import com.ibm.streams.operator.TupleAttribute;
import com.ibm.streams.operator.Type.MetaType;
import com.ibm.streams.operator.compile.OperatorContextChecker;
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
import org.apache.solr.common.SolrInputDocument; 
import org.apache.solr.client.solrj.response.UpdateResponse;

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
	private List<SolrInputDocument> docBuffer;
	private int maxDocumentBufferAge = 10000;
	private Timer docBufferTimer; 
	private final String MAP_ATTRIBUTE = "atomicUpdateMap";
	private boolean inputMapExists = true;
	private String uniqueKeyName = "";
	
	public static final String DESCRIPTION = 
			"This operator takes in a set of attributes and a map on its import port. "
			+ "Those attributes are committed to a Solr collection on a configurable interval (time or number of tuples). "
			+ "The map (attribute: atomicUpdateMap) must specify for an attribute the type of update: set, add, remove, removeregex, or inc. "
			+ "The map should NOT include the uniqueIdentifier attribute, as this is provided by a parameter."
			+ "If no map is provided, all attributes will be committ";
	
	@Parameter(optional = true, description = "Incoming attribute to be used as the unique id.  If the uniqueKeyAttribute is not specified,"
    		+ " a random UUID will be generated as the unique key and the uniqueKeyName parameter must be specified.")
    public void setUniqueKeyAttribute(TupleAttribute<Tuple, String> attributeName){
    	uniqueKeyAttribute = attributeName;
    }
	
    @Parameter(optional = true, description = "Name of the unique key in the Solr collection. If this is not specified, "
    		+ "the name of the uniqueKeyAttribute will be used by default. If the uniqueKeyAttribute is not specified,"
    		+ " a random UUID will be generated as the unique key and this parameter must be specified.")
    public void setUniqueKeyName(String value){
    	uniqueKeyName = value;
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
    		+ "be flushed based on size and will rely on soley on time-based flushing.")
    public void setDocumentCommitSize(int value){
    	documentCommitSize = value;
    }
    
    @Parameter(optional = true, description="Max time allowed for the document buffer to fill before automatically flushing. "
    		+ "Time in milliseconds. Default: 1000. If -1, buffer will never flush based on time and will rely solely on count-based flushing.")
    public void setMaxDocumentBufferAge(int value){
    	maxDocumentBufferAge  = value;
    }

    @Override
    public synchronized void shutdown() throws Exception {
        OperatorContext context = getOperatorContext();
        Logger.getLogger(this.getClass()).trace("Operator " + context.getName() + " shutting down in PE: " + context.getPE().getPEId() + " in Job: " + context.getPE().getJobId() );
        docBufferTimer.cancel();
        solrClient.close();       
        super.shutdown();
    }
    
    
    /*
     * Make sure that either uniqueKeyAttribute or uniqueKeyName are specified. 
     */
    @ContextCheck(compile = true)
	public static void checkDependentParameters(OperatorContextChecker checker) {
    	OperatorContext context = checker.getOperatorContext();
    	if (!context.getParameterNames().contains("uniqueKeyAttribute")){
    		if (!context.getParameterNames().contains("uniqueKeyName")){
        		checker.setInvalidContext("You must specify either the uniqueKeyAttribute or the uniqueKeyName parameters.", new String[] {});
        	}
    	}
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
        
        if (uniqueKeyName.isEmpty()){
        	uniqueKeyName = uniqueKeyAttribute.getAttribute().getName();
        }
        
        docBuffer = new ArrayList<SolrInputDocument>();
        docBufferTimer = new Timer();
        resetDocBuffer();        		
	}

	private String getCollectionURL() {
		if (!solrURL.endsWith("/"))
			solrURL += "/";
		String collectionURL = solrURL + collection;
		return collectionURL;
	}


    @Override
    public synchronized void process(StreamingInput<Tuple> stream, Tuple tuple)
            throws Exception {    	
    	SolrInputDocument doc = new SolrInputDocument();
    	
    	//if user provided update action map, use it.
    	if (inputMapExists){
	    	@SuppressWarnings("unchecked")
			Map<String,String> atomicUpdateMap = (Map<String, String>) tuple.getMap(MAP_ATTRIBUTE);
	    	
	    	for (Map.Entry<String, String> entry : atomicUpdateMap.entrySet()){
	    		addFieldToDocument(tuple, doc, entry);
	    	}
	    	
    	} else {
    		//default action is to set all attributes
    		for (String attributeName : tuple.getStreamSchema().getAttributeNames()){
    			doc.addField(attributeName, tuple.getObject(attributeName));
    		}
    	}
    	
    	if (!doc.containsKey(uniqueKeyName)){
    		addUniqueIdentifierFieldToDocument(tuple, doc);
    	}
    	
    	docBuffer.add(doc);
    	
    	if (documentCommitSize > 0
    			&& docBuffer.size() >= documentCommitSize){
    		System.out.println("Comitting from process...");
	    	//Add the documents then clear
	    	commitAndClearBuffer();
    	}
    	
    }

	private void addUniqueIdentifierFieldToDocument(Tuple tuple, SolrInputDocument doc) {
		String uniqueKeyValue;
		if (uniqueKeyAttribute != null){
			uniqueKeyValue = uniqueKeyAttribute.getValue(tuple);
		} else {
			//generate random unique key if it doesn't exist
			uniqueKeyValue = UUID.randomUUID().toString();
		}
		
		try{
    		doc.addField(uniqueKeyName, uniqueKeyValue);
    	} catch (Exception e) {
    		e.printStackTrace();
    		trace.log(TraceLevel.ERROR, "Failed to add unique identifier field: " + e.getMessage());
    		submitToErrorPort(e);
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
			submitToErrorPort(e);
		}
	}

	private synchronized void commitAndClearBuffer() {
		if(!docBuffer.isEmpty()){
			try {
				sendDocuments(docBuffer);
			} catch (Exception e){
				e.printStackTrace();
				trace.log(TraceLevel.ERROR, "Error while committing documents: " + e.getMessage() );
				submitToErrorPort(e);
			}
			resetDocBuffer();
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
		UpdateResponse response = solrClient.add(docBuffer2);
		if(trace.isInfoEnabled())
			trace.log(TraceLevel.INFO, "Solr Client add response status: " + response.getStatus());
		//solrClient.commit();
		System.out.println("Comitting...");
	}
    
    private void submitToErrorPort(Exception e) {
		if (validErrorPort) {
			StreamingOutput<OutputTuple> streamingOutput = getOutput(0);
			OutputTuple otup = streamingOutput.newTuple();
			otup.setString(0, e.getMessage());
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
			System.out.println("Comitting from TimerTask...");
			commitAndClearBuffer();
		}     	
    }
	
    
}
