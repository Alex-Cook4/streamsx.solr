package com.ibm.streams.solr;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.analysis.util.TokenizerFactory;

public class SolrStemmerEngine {
	private StopFilterFactory stopFilterFactory;
	private Tokenizer tokenizer;
	private SnowballPorterFilterFactory kStemFactory;
	private SynonymFilterFactory synonymFilterFactory;
	private LowerCaseFilterFactory lowerCaseFilterFactory;

	public SolrStemmerEngine(String luceneMatchVersion, String language,
			String synonymFile, String stopWordFile, Boolean ignoreCase,
			Boolean expand) throws IOException, ClassNotFoundException, InterruptedException {
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		Map<String, String> tokenizerArgs = new HashMap<String, String>();
		tokenizerArgs.put("luceneMatchVersion", luceneMatchVersion);

		// tokenizer
		TokenizerFactory tokenFactory = new StandardTokenizerFactory(
				tokenizerArgs);
		tokenizer = tokenFactory.create();

		//setup filter factory arguments
		Map<String, String> filterFactoryArgs = new HashMap<String, String>();
		filterFactoryArgs.put("luceneMatchVersion", luceneMatchVersion);
		filterFactoryArgs.put("words", stopWordFile);
		filterFactoryArgs.put("ignoreCase", ignoreCase.toString());
	
		
		// stop filter factory
		stopFilterFactory = new StopFilterFactory(filterFactoryArgs);
		

		stopFilterFactory.inform(new ClasspathResourceLoader(this.getClass()));
		
		// K stem filter
		Map<String, String> kStemFilterFacotoryArgs = new HashMap<String, String>();
		kStemFilterFacotoryArgs.put("luceneMatchVersion", luceneMatchVersion);
		kStemFilterFacotoryArgs.put("language", language);
		kStemFactory = new SnowballPorterFilterFactory(
				kStemFilterFacotoryArgs);
		kStemFactory.inform(new ClasspathResourceLoader(this.getClass()));

		// synonyms filter factory
		filterFactoryArgs.put("expand", expand.toString());
		filterFactoryArgs.put("synonyms", synonymFile); 
		synonymFilterFactory = new SynonymFilterFactory(filterFactoryArgs);
		synonymFilterFactory.inform(new ClasspathResourceLoader(this.getClass()));
		
		// lower case filter
		lowerCaseFilterFactory = new LowerCaseFilterFactory(tokenizerArgs);
		
	}

	public String getStems(String fullWords) throws IOException {
		StringReader inputText = new StringReader(fullWords);
		tokenizer.setReader(inputText);
		TokenStream stopTokenStream = stopFilterFactory.create(tokenizer);
		TokenStream kStemTokenStream = kStemFactory.create(stopTokenStream);
		TokenStream synonymTokenStream = synonymFilterFactory.create(kStemTokenStream);
		TokenStream finalTokenStream = lowerCaseFilterFactory.create(synonymTokenStream);
		
		// process token stream
		finalTokenStream.reset();
		CharTermAttribute termAttrib = (CharTermAttribute) finalTokenStream
				.getAttribute(CharTermAttribute.class);
		Collection<String> tokens = new ArrayList<String>();

		while (finalTokenStream.incrementToken()) {
			tokens.add(termAttrib.toString());
		}

		finalTokenStream.end();
		finalTokenStream.close();

		return tokens.toString();
	}

}
