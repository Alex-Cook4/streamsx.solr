package com.ibm.streams.solr;

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
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	System.out.println(url.getFile());
        }
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		solrStemmerEngine = new SolrStemmerEngine(luceneMatchVersion, language, synonymFile, stopWordFile, ignoreCase, expand);
		String fullWords = "apples, bananas, hearing coding loving killing making be walked talked sorted love glove" ;  
		String stemmedTokens = solrStemmerEngine.getStems(fullWords);
		System.out.println(stemmedTokens);
	}

}
