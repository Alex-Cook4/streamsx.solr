package com.ibm.streamsx.solr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocumentList;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class SolrQueryEngine {
	SolrClient solrClient;
	
	public SolrQueryEngine(String solrURL) {
		solrClient = new HttpSolrClient(solrURL);
	}

	public JSONObject  sendQuery(String queryString) throws Exception {
		SolrQuery query = getQuery(queryString);
		QueryResponse queryResponse = solrClient.query(query); //getHTML(queryString);
		return getQueryJSON(queryResponse);
	}

	public JSONObject sendQuery(String collection, String queryString) throws Exception {
		SolrQuery query = getQuery(queryString);
		QueryResponse queryResponse = solrClient.query(collection, query); //getHTML(queryString);
		return getQueryJSON(queryResponse);
	}

	private JSONObject getQueryJSON(QueryResponse qp) throws JSONException {
		SolrDocumentList docList= qp.getResults();
		JSONObject returnResults = new JSONObject();
		Map<Integer, Object> solrDocMap = new HashMap<Integer, Object>();
		int counter = 1;
		for(@SuppressWarnings("rawtypes") Map singleDoc : docList)
		{
		  solrDocMap.put(counter, new JSONObject(singleDoc));
		  counter++;
		} 
		returnResults.put("docs", solrDocMap);
		return returnResults;
	}

	private SolrQuery getQuery(String queryString) {
		queryString = ClientUtils.escapeQueryChars(queryString);
		SolrQuery query = new SolrQuery(queryString);
		return query;
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
