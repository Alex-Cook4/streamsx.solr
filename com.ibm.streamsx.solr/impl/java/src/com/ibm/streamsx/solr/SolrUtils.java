package com.ibm.streamsx.solr;

public class SolrUtils {

	public SolrUtils() {
	}
	
	public static String getCollectionURL(String solrURL, String collection) {
		if (!solrURL.endsWith("/"))
			solrURL += "/";
		String collectionURL = solrURL + collection;
		return collectionURL;
	}
	
}
