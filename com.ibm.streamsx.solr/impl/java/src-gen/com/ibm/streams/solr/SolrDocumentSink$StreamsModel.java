package com.ibm.streams.solr;
import com.ibm.streams.operator.metrics.Metric.Kind;
import com.ibm.streams.operator.model.InputPortSet.WindowMode;
import com.ibm.streams.operator.model.InputPortSet.WindowPunctuationInputMode;
import com.ibm.streams.operator.model.OutputPortSet.WindowPunctuationOutputMode;

@com.ibm.streams.operator.model.Libraries(value={"opt/downloaded/*"})
@com.ibm.streams.operator.model.PrimitiveOperator(name="SolrDocumentSink", namespace="com.ibm.streamsx.solr", description="Java Operator SolrDocumentSink")
@com.ibm.streams.operator.model.InputPorts(value={@com.ibm.streams.operator.model.InputPortSet(description="Port that ingests tuples", cardinality=1, optional=false, windowingMode=WindowMode.NonWindowed, windowPunctuationInputMode=WindowPunctuationInputMode.Oblivious)})
@com.ibm.streams.operator.model.OutputPorts(value={@com.ibm.streams.operator.model.OutputPortSet(description="Error Port", optional=true)})
@com.ibm.streams.operator.internal.model.ShadowClass("com.ibm.streams.solr.SolrDocumentSink")
@javax.annotation.Generated("com.ibm.streams.operator.internal.model.processors.ShadowClassGenerator")
public class SolrDocumentSink$StreamsModel extends com.ibm.streams.operator.AbstractOperator
 {

@com.ibm.streams.operator.model.Parameter(optional=false)
@com.ibm.streams.operator.internal.model.MethodParameters({"attributeName"})
public void setUniqueIdentifierAttribute(java.lang.String attributeName) {}

@com.ibm.streams.operator.model.Parameter(optional=false)
@com.ibm.streams.operator.internal.model.MethodParameters({"value"})
public void setSolrURL(java.lang.String value) {}

@com.ibm.streams.operator.model.Parameter(optional=false)
@com.ibm.streams.operator.internal.model.MethodParameters({"value"})
public void setCollection(java.lang.String value) {}
}