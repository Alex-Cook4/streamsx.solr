package com.ibm.streamsx.solr;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.en.KStemFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.analysis.util.TokenizerFactory;

public class SolrStemmerEngine {
	private StopFilterFactory stopFilterFactory;
	private Tokenizer tokenizer;
	private SnowballPorterFilterFactory snowballPorterFactory;
	private KStemFilterFactory kStemFactory;
	private SynonymFilterFactory synonymFilterFactory;
	private LowerCaseFilterFactory lowerCaseFilterFactory;
	private String stemmerType;

	public SolrStemmerEngine(String incomingStemmerType, String luceneMatchVersion, String language,
			String synonymFile, String stopWordFile, Boolean ignoreCase,
			Boolean expand) throws Exception {
		stemmerType = incomingStemmerType;
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
		
		if (stemmerType.equalsIgnoreCase("kstem")){
	        // K stem filter
	        Map<String, String> ksffparam = new HashMap<String, String>(); 
			kStemFactory = new KStemFilterFactory(ksffparam); 
		} else if (stemmerType.equalsIgnoreCase("snowball")){
			// Snowball stem filter
			Map<String, String> snowballPorterFactorArgs = new HashMap<String, String>();
			snowballPorterFactorArgs.put("luceneMatchVersion", luceneMatchVersion);
			snowballPorterFactorArgs.put("language", language);
			snowballPorterFactory = new SnowballPorterFilterFactory(
					snowballPorterFactorArgs);
			snowballPorterFactory.inform(new ClasspathResourceLoader(this.getClass()));
		} else {
			throw new Exception("Stemmer type not valid!");
		}
		// synonyms filter factory
		filterFactoryArgs.put("expand", expand.toString());
		filterFactoryArgs.put("synonyms", synonymFile); 
		synonymFilterFactory = new SynonymFilterFactory(filterFactoryArgs);
		synonymFilterFactory.inform(new ClasspathResourceLoader(this.getClass()));
		
		// lower case filter
		lowerCaseFilterFactory = new LowerCaseFilterFactory(tokenizerArgs);
		
	}

	public String getStems(String fullWords) throws Exception {
		StringReader inputText = new StringReader(fullWords);
		tokenizer.setReader(inputText);
		TokenStream stopTokenStream = stopFilterFactory.create(tokenizer);
		TokenStream stemmerTokenStream;
		
		if (stemmerType.equalsIgnoreCase("kstem")){
			stemmerTokenStream = kStemFactory.create(stopTokenStream);
		} else if (stemmerType.equalsIgnoreCase("snowball")){
			stemmerTokenStream = snowballPorterFactory.create(stopTokenStream);
		} else {
			throw new Exception("Stemmer type not valid!");
		}
		
		TokenStream synonymTokenStream = synonymFilterFactory.create(stemmerTokenStream);
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
