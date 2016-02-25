package com.ibm.streamsx.solr;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class StemmerTest {
	static SolrStemmerEngine solrStemmerEngine;
	private static String luceneMatchVersion = "LUCENE_51";
	private static String language = "English";
	private static String synonymFile = "synonyms.txt";
	private static String stopWordFile = "stopwords.txt";
	private static Boolean ignoreCase = true;
	private static Boolean expand = false;
	private static String stemmerType = "snowball";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	System.out.println(url.getFile());
        }
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		solrStemmerEngine = new SolrStemmerEngine(stemmerType, luceneMatchVersion, language, synonymFile, stopWordFile, ignoreCase, expand);
		String fullWords = "apples, bananas, hearing coding loving killing making be walked talked sorted love glove" ;  
		String stemmedTokens = solrStemmerEngine.getStems(fullWords);
		System.out.println(stemmedTokens);
	}

}
