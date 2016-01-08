package com.ibm.streams.solr;
import com.ibm.streams.operator.metrics.Metric.Kind;
import com.ibm.streams.operator.model.InputPortSet.WindowMode;
import com.ibm.streams.operator.model.InputPortSet.WindowPunctuationInputMode;
import com.ibm.streams.operator.model.OutputPortSet.WindowPunctuationOutputMode;

@com.ibm.streams.operator.model.PrimitiveOperator(name="SolrStemmer", namespace="com.ibm.streamsx.solr", description="Java Operator SolrStemmer")
@com.ibm.streams.operator.model.InputPorts(value={@com.ibm.streams.operator.model.InputPortSet(description="Port that ingests tuples", cardinality=1, optional=false, windowingMode=WindowMode.NonWindowed, windowPunctuationInputMode=WindowPunctuationInputMode.Oblivious), @com.ibm.streams.operator.model.InputPortSet(description="Optional input ports", optional=true, windowingMode=WindowMode.NonWindowed, windowPunctuationInputMode=WindowPunctuationInputMode.Oblivious)})
@com.ibm.streams.operator.model.OutputPorts(value={@com.ibm.streams.operator.model.OutputPortSet(description="Port that produces tuples", cardinality=1, optional=false, windowPunctuationOutputMode=WindowPunctuationOutputMode.Generating), @com.ibm.streams.operator.model.OutputPortSet(description="Optional output ports", optional=true, windowPunctuationOutputMode=WindowPunctuationOutputMode.Generating)})
@com.ibm.streams.operator.model.Libraries(value={"opt/downloaded/*", "opt/downloaded/stopwords.txt"})
@com.ibm.streams.operator.model.SharedLoader()
@com.ibm.streams.operator.internal.model.ShadowClass("com.ibm.streams.solr.SolrStemmer")
@javax.annotation.Generated("com.ibm.streams.operator.internal.model.processors.ShadowClassGenerator")
public class SolrStemmer$StreamsModel extends com.ibm.streams.operator.AbstractOperator
 {

@com.ibm.streams.operator.model.Parameter(optional=true)
@com.ibm.streams.operator.internal.model.MethodParameters({"value"})
public void setSynonymFile(java.lang.String value) {}

@com.ibm.streams.operator.model.Parameter(optional=true)
@com.ibm.streams.operator.internal.model.MethodParameters({"value"})
public void setExpand(boolean value) {}

@com.ibm.streams.operator.model.Parameter(optional=true)
@com.ibm.streams.operator.internal.model.MethodParameters({"value"})
public void setIgnoreCase(boolean value) {}

@com.ibm.streams.operator.model.Parameter(optional=true)
@com.ibm.streams.operator.internal.model.MethodParameters({"value"})
public void setLuceneMatchVersion(java.lang.String value) {}

@com.ibm.streams.operator.model.Parameter(optional=true)
@com.ibm.streams.operator.internal.model.MethodParameters({"value"})
public void setStopWordFile(java.lang.String value) {}

@com.ibm.streams.operator.model.Parameter(optional=true)
@com.ibm.streams.operator.internal.model.MethodParameters({"value"})
public void setLanguage(java.lang.String value) {}
}