package com.ibm.streamsx.solr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SolrQueryEngine {
	
	public SolrQueryEngine() {
	}

	public String sendQuery(String queryString) throws IOException {
		String queryResponse = getHTML(queryString);
		return queryResponse;
	}
	
	 public static String getHTML(String urlToRead) throws IOException {
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
	      conn.disconnect();
	      return result.toString();
	   }

	public String buildQuery(String collectionURL, String responseFormat,
			int numberOfRows, boolean omitHeader, String queryLogic) {
		//by putting queryLogic first, our responseFormat and number of rows will be overridden by queries
		String queryString = collectionURL + 
				"/select/?" + queryLogic;
		
		if (!responseFormat.isEmpty()){
			queryString += "&wt=" + responseFormat; 
		}
		
		if (numberOfRows > 0){
			queryString += "&rows=" + String.valueOf(numberOfRows);
		} 
		
		queryString += "&omitHeader=" + String.valueOf(omitHeader);
		return queryString;
	}
	
	public String buildQuery(String collectionURL, String queryLogic) {
		//by putting queryLogic first, our responseFormat and number of rows will be overridden by queries
		String queryString = collectionURL + 
				"/select/?" + queryLogic;
		return queryString;
	}
}
