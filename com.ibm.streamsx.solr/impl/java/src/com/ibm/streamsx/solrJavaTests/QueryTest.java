package com.ibm.streamsx.solrJavaTests;

import org.codehaus.jettison.json.JSONObject;

import com.ibm.streamsx.solr.SolrQueryEngine; 

public class QueryTest { 

	private static SolrQueryEngine queryEngine;
	private static String solrURL = "http://g0601b02.pok.hpc-ng.ibm.com:8984/solr/techproducts/";
	private static String queryLogic = "/select/?wt=xml&q=cat=electronics&fq=cat:*&fl=id,cat&rows=200";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		queryEngine = new SolrQueryEngine(solrURL );
		
		System.out.println(queryLogic);
		JSONObject queryResponse = queryEngine.sendQuery(queryLogic);
		System.out.println(queryResponse);
	}

}
