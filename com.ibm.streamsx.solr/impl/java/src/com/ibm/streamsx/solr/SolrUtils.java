package com.ibm.streamsx.solr;

import java.util.Date;

import com.ibm.streams.operator.AbstractOperator;
import com.ibm.streams.operator.OperatorContext;
import com.ibm.streams.operator.OutputTuple;
import com.ibm.streams.operator.StreamingOutput;
import com.ibm.streams.operator.Type.MetaType;

public class SolrUtils {

	public SolrUtils() {
	}
	
	public static String getCollectionURL(String solrURL, String collection) {
		if (!solrURL.endsWith("/"))
			solrURL += "/";
		String collectionURL = solrURL + collection;
		return collectionURL;
	}
	
    public static void submitToErrorPort(AbstractOperator oper, int port, String error, boolean validErrorPort) {
		if (validErrorPort) {
			StreamingOutput<OutputTuple> streamingOutput = oper.getOutput(port);
			OutputTuple otup = streamingOutput.newTuple();
			Date date = new Date();
			otup.setString(0, date.toString() + ": " + error);
			try {
				streamingOutput.submit(otup);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public static boolean operatorContainsValidErrorPort(OperatorContext context, int port) {
		boolean validErrorPort = false;
        if (context.getStreamingOutputs().size() - 1 >= port){
        	StreamingOutput<OutputTuple> output = context.getStreamingOutputs().get(port);
        	if (output.getStreamSchema().getAttribute(0).getType().getMetaType() ==  MetaType.RSTRING){
        		validErrorPort = true;
        	} 
        }
        
        return validErrorPort;
	}
	
}
