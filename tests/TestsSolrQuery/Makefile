# begin_generated_IBM_copyright_prolog                             
#                                                                  
# This is an automatically generated copyright prolog.             
# After initializing,  DO NOT MODIFY OR MOVE                       
# **************************************************************** 
# Licensed Materials - Property of IBM                             
# 5724-Y95                                                         
# (C) Copyright IBM Corp.  2010, 2015    All Rights Reserved.      
# US Government Users Restricted Rights - Use, duplication or      
# disclosure restricted by GSA ADP Schedule Contract with          
# IBM Corp.                                                        
#                                                                  
# end_generated_IBM_copyright_prolog                               
.PHONY: all TestSolrQuerySimple TestCollectionAsAttribute TestPassingForwardAttributes TestQueryError TestBadTupleMap clean-TestSolrQuerySimple clean-TestCollectionAsAttribute clean-TestPassingForwardAttributes clean-TestQueryError clean-TestBadTupleMap clean-appset check-tk

CPAPPWD=$(shell pwd)

SPLC_FLAGS                    ?= -a
SPLC_FLAGS_TestSolrQuerySimple     ?= $(SPLC_FLAGS) 
SPLC_FLAGS_TestCollectionAsAttribute  ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestPassingForwardAttributes        ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestQueryError ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestBadTupleMap  ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestRemoveAttribute ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestNegativeDocBufferTimer  ?= $(SPLC_FLAGS)
SPLC = $(STREAMS_INSTALL)/bin/sc
#STREAMSX_SOLR_TOOLKIT = $(STREAMSX_INSTALL)

SPL_CMD_ARGS                    ?= 
SPL_CMD_ARGS_TestSolrQuerySimple     ?= $(SPL_CMD_ARGS) 
SPL_CMD_ARGS_TestSolrQuerySimple	+= -t $(STREAMSX_SOLR_TOOLKIT)
SPL_CMD_ARGS_TestCollectionAsAttribute  ?= $(SPL_CMD_ARGS)
SPL_CMD_ARGS_TestCollectionAsAttribute  += -t $(STREAMSX_SOLR_TOOLKIT)
SPL_CMD_ARGS_TestPassingForwardAttributes        ?= $(SPL_CMD_ARGS)
SPL_CMD_ARGS_TestPassingForwardAttributes	+= -t $(STREAMSX_SOLR_TOOLKIT)
SPL_CMD_ARGS_TestQueryError ?= $(SPL_CMD_ARGS)
SPL_CMD_ARGS_TestQueryError += -t $(STREAMSX_SOLR_TOOLKIT)


SPL_MAIN_COMPOSITE_TestSolrQuerySimple     = application::TestSolrQuerySimple
SPL_MAIN_COMPOSITE_TestCollectionAsAttribute  = application::TestCollectionAsAttribute
SPL_MAIN_COMPOSITE_TestPassingForwardAttributes        = application::TestPassingForwardAttributes
SPL_MAIN_COMPOSITE_TestQueryError = application::TestQueryError

SPL_OUTPUT_DIR_TestSolrQuerySimple     = output/TestSolrQuerySimple
SPL_OUTPUT_DIR_TestCollectionAsAttribute  = output/TestCollectionAsAttribute
SPL_OUTPUT_DIR_TestPassingForwardAttributes        = output/TestPassingForwardAttributes
SPL_OUTPUT_DIR_TestQueryError = output/TestQueryError

SPL_DATA_DIR_TestSolrQuerySimple     = $(CPAPPWD)/data/TestSolrQuerySimple
SPL_DATA_DIR_TestCollectionAsAttribute  = $(CPAPPWD)/data/TestCollectionAsAttribute
SPL_DATA_DIR_TestPassingForwardAttributes        = $(CPAPPWD)/data/TestPassingForwardAttributes
SPL_DATA_DIR_TestQueryError = $(CPAPPWD)/data/TestQueryError


SPL_SUBMIT_APPSET = appset

all: TestSolrQuerySimple TestCollectionAsAttribute TestPassingForwardAttributes TestQueryError $(SPL_SUBMIT_APPSET)

TestSolrQuerySimple: check-tk
	mkdir -p $(SPL_DATA_DIR_TestSolrQuerySimple)
	$(SPLC) $(SPLC_FLAGS_TestSolrQuerySimple) -M $(SPL_MAIN_COMPOSITE_TestSolrQuerySimple) \
          --output-dir=$(SPL_OUTPUT_DIR_TestSolrQuerySimple) --data-dir=$(SPL_DATA_DIR_TestSolrQuerySimple) $(SPL_CMD_ARGS_TestSolrQuerySimple)

TestCollectionAsAttribute: check-tk
	mkdir -p $(SPL_DATA_DIR_TestCollectionAsAttribute)
	$(SPLC) $(SPLC_FLAGS_TestCollectionAsAttribute) -M $(SPL_MAIN_COMPOSITE_TestCollectionAsAttribute) \
          --output-dir=$(SPL_OUTPUT_DIR_TestCollectionAsAttribute) --data-dir=$(SPL_DATA_DIR_TestCollectionAsAttribute) $(SPL_CMD_ARGS_TestCollectionAsAttribute)

TestPassingForwardAttributes: check-tk
	mkdir -p $(SPL_DATA_DIR_TestPassingForwardAttributes)
	$(SPLC) $(SPLC_FLAGS_TestPassingForwardAttributes) -M $(SPL_MAIN_COMPOSITE_TestPassingForwardAttributes) \
          --output-dir=$(SPL_OUTPUT_DIR_TestPassingForwardAttributes) --data-dir=$(SPL_DATA_DIR_TestPassingForwardAttributes) $(SPL_CMD_ARGS_TestPassingForwardAttributes)

TestQueryError: check-tk
	mkdir -p $(SPL_DATA_DIR_TestQueryError)
	$(SPLC) $(SPLC_FLAGS_TestQueryError) -M $(SPL_MAIN_COMPOSITE_TestQueryError) \
          --output-dir=$(SPL_OUTPUT_DIR_TestQueryError) --data-dir=$(SPL_DATA_DIR_TestQueryError) $(SPL_CMD_ARGS_TestQueryError)

$(SPL_SUBMIT_APPSET): 
	echo $(SPL_OUTPUT_DIR_TestSolrQuerySimple)/$(SPL_MAIN_COMPOSITE_TestSolrQuerySimple).adl > $(SPL_SUBMIT_APPSET)
	echo $(SPL_OUTPUT_DIR_TestCollectionAsAttribute)/$(SPL_MAIN_COMPOSITE_TestCollectionAsAttribute).adl >> $(SPL_SUBMIT_APPSET)
	echo $(SPL_OUTPUT_DIR_TestPassingForwardAttributes)/$(SPL_MAIN_COMPOSITE_TestPassingForwardAttributes).adl >> $(SPL_SUBMIT_APPSET)
	echo $(SPL_OUTPUT_DIR_TestQueryError)/$(SPL_MAIN_COMPOSITE_TestQueryError).adl >> $(SPL_SUBMIT_APPSET)
	sed -i 's/::/./g' $(SPL_SUBMIT_APPSET)

clean: clean-TestSolrQuerySimple clean-TestCollectionAsAttribute clean-TestPassingForwardAttributes clean-TestQueryError clean-appset

clean-TestSolrQuerySimple:
	rm -rf $(SPL_DATA_DIR_TestSolrQuerySimple)
	$(SPLC) $(SPLC_FLAGS_TestSolrQuerySimple) -C -M $(SPL_MAIN_COMPOSITE_TestSolrQuerySimple) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestSolrQuerySimple) --data-dir=$(SPL_DATA_DIR_TestSolrQuerySimple) 

clean-TestCollectionAsAttribute:
	rm -rf $(SPL_DATA_DIR_TestCollectionAsAttribute)
	$(SPLC) $(SPLC_FLAGS_TestCollectionAsAttribute) -C -M $(SPL_MAIN_COMPOSITE_TestCollectionAsAttribute) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestCollectionAsAttribute) --data-dir=$(SPL_DATA_DIR_TestCollectionAsAttribute) 

clean-TestPassingForwardAttributes: 
	rm -rf $(SPL_DATA_DIR_TestPassingForwardAttributes)
	$(SPLC) $(SPLC_FLAGS_TestPassingForwardAttributes) -C -M $(SPL_MAIN_COMPOSITE_TestPassingForwardAttributes) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestPassingForwardAttributes) --data-dir=$(SPL_DATA_DIR_TestPassingForwardAttributes) 

clean-TestQueryError: 
	rm -rf $(SPL_DATA_DIR_TestQueryError)
	$(SPLC) $(SPLC_FLAGS_TestQueryError) -C -M $(SPL_MAIN_COMPOSITE_TestQueryError) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestQueryError) --data-dir=$(SPL_DATA_DIR_TestQueryError) 

clean-appset:
	rm -f $(SPL_SUBMIT_APPSET)
