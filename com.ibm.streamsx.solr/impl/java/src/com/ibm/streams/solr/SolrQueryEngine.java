package com.ibm.streams.solr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SolrQueryEngine {

	public String sendQuery(String queryString) throws Exception {
		String queryResponse = getHTML(queryString);
		return queryResponse;
	}

	 public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      
	      return result.toString();
	   }

	public String buildQuery(String collectionURL, String responseFormat,
			int numberOfRows, boolean omitHeader, String queryLogic) {
		//by putting queryLogic first, our responseFormat and number of rows will be overridden by queries
		String queryString = collectionURL + 
				"/select/?" + queryLogic + 
				"&wt=" + responseFormat + 
				"&rows=" + String.valueOf(numberOfRows) + 
				"&omitHeader=" + String.valueOf(omitHeader);
		return queryString;
	}
}
