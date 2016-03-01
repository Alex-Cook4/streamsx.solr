package com.ibm.streamsx.solrJavaTests;


import java.net.URI;

import com.ibm.streamsx.solr.SolrQueryEngine; 

public class QueryTest { 

	private static SolrQueryEngine queryEngine;
	private static String solrURL = "http://g0601b02.pok.hpc-ng.ibm.com:8984/solr/techproducts";
	private static String queryLogic = "*:*&sort=id%20asc&fq=cat:electronics&fl=id,cat,name";
	/**
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		queryEngine = new SolrQueryEngine( );
		
		System.out.println(queryLogic);
		
		String queryString = queryEngine.buildQuery(solrURL, queryLogic);
		System.out.println(queryString);
		String queryResponse = queryEngine.sendQuery(queryString);
		System.out.println(queryResponse);
	}

}
