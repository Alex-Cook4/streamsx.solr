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
.PHONY: all TestManyTypes TestDefaultSetAll TestManyTypesSetAll TestNoUniqueKey TestBadTupleMap clean-TestManyTypes clean-TestDefaultSetAll clean-TestManyTypesSetAll clean-TestNoUniqueKey clean-TestBadTupleMap clean-appset check-tk

CPAPPWD=$(shell pwd)

SPLC_FLAGS                    ?= -a
SPLC_FLAGS_TestManyTypes     ?= $(SPLC_FLAGS) 
SPLC_FLAGS_TestDefaultSetAll  ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestManyTypesSetAll        ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestNoUniqueKey ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestBadTupleMap  ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestRemoveAttribute ?= $(SPLC_FLAGS)
SPLC_FLAGS_TestNegativeDocBufferTimer  ?= $(SPLC_FLAGS)
SPLC = $(STREAMS_INSTALL)/bin/sc
#STREAMSX_SOLR_TOOLKIT = $(STREAMSX_INSTALL)

SPL_CMD_ARGS                    ?= 
SPL_CMD_ARGS_TestManyTypes     ?= $(SPL_CMD_ARGS) 
SPL_CMD_ARGS_TestManyTypes	+= -t $(STREAMSX_SOLR_TOOLKIT)
SPL_CMD_ARGS_TestDefaultSetAll  ?= $(SPL_CMD_ARGS)
SPL_CMD_ARGS_TestDefaultSetAll  += -t $(STREAMSX_SOLR_TOOLKIT)
SPL_CMD_ARGS_TestManyTypesSetAll        ?= $(SPL_CMD_ARGS)
SPL_CMD_ARGS_TestManyTypesSetAll	+= -t $(STREAMSX_SOLR_TOOLKIT)
SPL_CMD_ARGS_TestNoUniqueKey ?= $(SPL_CMD_ARGS)
SPL_CMD_ARGS_TestNoUniqueKey += -t $(STREAMSX_SOLR_TOOLKIT)
SPL_CMD_ARGS_TestBadTupleMap  ?= $(SPL_CMD_ARGS)
SPL_CMD_ARGS_TestBadTupleMap  += -t $(STREAMSX_SOLR_TOOLKIT)
SPL_CMD_ARGS_TestRemoveAttribute ?= $(SPL_CMD_ARGS)
SPL_CMD_ARGS_TestRemoveAttribute += -t $(STREAMSX_SOLR_TOOLKIT)
SPL_CMD_ARGS_TestNegativeDocBufferTimer  ?= $(SPL_CMD_ARGS)
SPL_CMD_ARGS_TestNegativeDocBufferTimer  += -t $(STREAMSX_SOLR_TOOLKIT)


SPL_MAIN_COMPOSITE_TestManyTypes     = application::TestManyTypes
SPL_MAIN_COMPOSITE_TestDefaultSetAll  = application::TestDefaultSetAll
SPL_MAIN_COMPOSITE_TestManyTypesSetAll        = application::TestManyTypesSetAll
SPL_MAIN_COMPOSITE_TestNoUniqueKey = application::TestNoUniqueKey
SPL_MAIN_COMPOSITE_TestBadTupleMap  = application::TestBadTupleMap
SPL_MAIN_COMPOSITE_TestRemoveAttribute = application::TestRemoveAttribute
SPL_MAIN_COMPOSITE_TestNegativeDocBufferTimer  = application::TestNegativeDocBufferTimer

SPL_OUTPUT_DIR_TestManyTypes     = output/TestManyTypes
SPL_OUTPUT_DIR_TestDefaultSetAll  = output/TestDefaultSetAll
SPL_OUTPUT_DIR_TestManyTypesSetAll        = output/TestManyTypesSetAll
SPL_OUTPUT_DIR_TestNoUniqueKey = output/TestNoUniqueKey
SPL_OUTPUT_DIR_TestBadTupleMap  = output/TestBadTupleMap
SPL_OUTPUT_DIR_TestRemoveAttribute = output/TestRemoveAttribute
SPL_OUTPUT_DIR_TestNegativeDocBufferTimer  = output/TestNegativeDocBufferTimer


SPL_DATA_DIR_TestManyTypes     = $(CPAPPWD)/data/TestManyTypes
SPL_DATA_DIR_TestDefaultSetAll  = $(CPAPPWD)/data/TestDefaultSetAll
SPL_DATA_DIR_TestManyTypesSetAll        = $(CPAPPWD)/data/TestManyTypesSetAll
SPL_DATA_DIR_TestNoUniqueKey = $(CPAPPWD)/data/TestNoUniqueKey
SPL_DATA_DIR_TestBadTupleMap  = $(CPAPPWD)/data/TestBadTupleMap
SPL_DATA_DIR_TestRemoveAttribute = $(CPAPPWD)/data/TestRemoveAttribute
SPL_DATA_DIR_TestNegativeDocBufferTimer  = $(CPAPPWD)/data/TestNegativeDocBufferTimer


SPL_SUBMIT_APPSET = appset

all: TestManyTypes TestDefaultSetAll TestManyTypesSetAll TestNoUniqueKey TestBadTupleMap TestRemoveAttribute TestNegativeDocBufferTimer $(SPL_SUBMIT_APPSET)

TestManyTypes: check-tk
	mkdir -p $(SPL_DATA_DIR_TestManyTypes)
	$(SPLC) $(SPLC_FLAGS_TestManyTypes) -M $(SPL_MAIN_COMPOSITE_TestManyTypes) \
          --output-dir=$(SPL_OUTPUT_DIR_TestManyTypes) --data-dir=$(SPL_DATA_DIR_TestManyTypes) $(SPL_CMD_ARGS_TestManyTypes)

TestDefaultSetAll: check-tk
	mkdir -p $(SPL_DATA_DIR_TestDefaultSetAll)
	$(SPLC) $(SPLC_FLAGS_TestDefaultSetAll) -M $(SPL_MAIN_COMPOSITE_TestDefaultSetAll) \
          --output-dir=$(SPL_OUTPUT_DIR_TestDefaultSetAll) --data-dir=$(SPL_DATA_DIR_TestDefaultSetAll) $(SPL_CMD_ARGS_TestDefaultSetAll)

TestManyTypesSetAll: check-tk
	mkdir -p $(SPL_DATA_DIR_TestManyTypesSetAll)
	$(SPLC) $(SPLC_FLAGS_TestManyTypesSetAll) -M $(SPL_MAIN_COMPOSITE_TestManyTypesSetAll) \
          --output-dir=$(SPL_OUTPUT_DIR_TestManyTypesSetAll) --data-dir=$(SPL_DATA_DIR_TestManyTypesSetAll) $(SPL_CMD_ARGS_TestManyTypesSetAll)

TestNoUniqueKey: check-tk
	mkdir -p $(SPL_DATA_DIR_TestNoUniqueKey)
	$(SPLC) $(SPLC_FLAGS_TestNoUniqueKey) -M $(SPL_MAIN_COMPOSITE_TestNoUniqueKey) \
          --output-dir=$(SPL_OUTPUT_DIR_TestNoUniqueKey) --data-dir=$(SPL_DATA_DIR_TestNoUniqueKey) $(SPL_CMD_ARGS_TestNoUniqueKey)

TestBadTupleMap: check-tk
	mkdir -p $(SPL_DATA_DIR_TestBadTupleMap)
	$(SPLC) $(SPLC_FLAGS_TestBadTupleMap) -M $(SPL_MAIN_COMPOSITE_TestBadTupleMap) \
          --output-dir=$(SPL_OUTPUT_DIR_TestBadTupleMap) --data-dir=$(SPL_DATA_DIR_TestBadTupleMap) $(SPL_CMD_ARGS_TestBadTupleMap)

TestRemoveAttribute: check-tk
	mkdir -p $(SPL_DATA_DIR_TestRemoveAttribute)
	$(SPLC) $(SPLC_FLAGS_TestRemoveAttribute) -M $(SPL_MAIN_COMPOSITE_TestRemoveAttribute) \
          --output-dir=$(SPL_OUTPUT_DIR_TestRemoveAttribute) --data-dir=$(SPL_DATA_DIR_TestRemoveAttribute) $(SPL_CMD_ARGS_TestRemoveAttribute)

TestNegativeDocBufferTimer: check-tk
	mkdir -p $(SPL_DATA_DIR_TestNegativeDocBufferTimer)
	$(SPLC) $(SPLC_FLAGS_TestNegativeDocBufferTimer) -M $(SPL_MAIN_COMPOSITE_TestNegativeDocBufferTimer) \
          --output-dir=$(SPL_OUTPUT_DIR_TestNegativeDocBufferTimer) --data-dir=$(SPL_DATA_DIR_TestNegativeDocBufferTimer) $(SPL_CMD_ARGS_TestNegativeDocBufferTimer)

$(SPL_SUBMIT_APPSET): 
	echo $(SPL_OUTPUT_DIR_TestManyTypes)/$(SPL_MAIN_COMPOSITE_TestManyTypes).adl > $(SPL_SUBMIT_APPSET)
	echo $(SPL_OUTPUT_DIR_TestDefaultSetAll)/$(SPL_MAIN_COMPOSITE_TestDefaultSetAll).adl >> $(SPL_SUBMIT_APPSET)
	echo $(SPL_OUTPUT_DIR_TestManyTypesSetAll)/$(SPL_MAIN_COMPOSITE_TestManyTypesSetAll).adl >> $(SPL_SUBMIT_APPSET)
	echo $(SPL_OUTPUT_DIR_TestNoUniqueKey)/$(SPL_MAIN_COMPOSITE_TestNoUniqueKey).adl >> $(SPL_SUBMIT_APPSET)
	echo $(SPL_OUTPUT_DIR_TestBadTupleMap)/$(SPL_MAIN_COMPOSITE_TestBadTupleMap).adl >> $(SPL_SUBMIT_APPSET)
	echo $(SPL_OUTPUT_DIR_TestRemoveAttribute)/$(SPL_MAIN_COMPOSITE_TestRemoveAttribute).adl >> $(SPL_SUBMIT_APPSET)
	echo $(SPL_OUTPUT_DIR_TestNegativeDocBufferTimer)/$(SPL_MAIN_COMPOSITE_TestNegativeDocBufferTimer).adl >> $(SPL_SUBMIT_APPSET)
	sed -i 's/::/./g' $(SPL_SUBMIT_APPSET)

clean: clean-TestManyTypes clean-TestDefaultSetAll clean-TestManyTypesSetAll clean-TestNoUniqueKey clean-TestBadTupleMap clean-TestRemoveAttribute clean-TestNegativeDocBufferTimer clean-appset

clean-TestManyTypes:
	rm -rf $(SPL_DATA_DIR_TestManyTypes)
	$(SPLC) $(SPLC_FLAGS_TestManyTypes) -C -M $(SPL_MAIN_COMPOSITE_TestManyTypes) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestManyTypes) --data-dir=$(SPL_DATA_DIR_TestManyTypes) 

clean-TestDefaultSetAll:
	rm -rf $(SPL_DATA_DIR_TestDefaultSetAll)
	$(SPLC) $(SPLC_FLAGS_TestDefaultSetAll) -C -M $(SPL_MAIN_COMPOSITE_TestDefaultSetAll) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestDefaultSetAll) --data-dir=$(SPL_DATA_DIR_TestDefaultSetAll) 

clean-TestManyTypesSetAll: 
	rm -rf $(SPL_DATA_DIR_TestManyTypesSetAll)
	$(SPLC) $(SPLC_FLAGS_TestManyTypesSetAll) -C -M $(SPL_MAIN_COMPOSITE_TestManyTypesSetAll) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestManyTypesSetAll) --data-dir=$(SPL_DATA_DIR_TestManyTypesSetAll) 

clean-TestNoUniqueKey: 
	rm -rf $(SPL_DATA_DIR_TestNoUniqueKey)
	$(SPLC) $(SPLC_FLAGS_TestNoUniqueKey) -C -M $(SPL_MAIN_COMPOSITE_TestNoUniqueKey) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestNoUniqueKey) --data-dir=$(SPL_DATA_DIR_TestNoUniqueKey) 

clean-TestBadTupleMap: 
	rm -rf $(SPL_DATA_DIR_TestBadTupleMap)
	$(SPLC) $(SPLC_FLAGS_TestBadTupleMap) -C -M $(SPL_MAIN_COMPOSITE_TestBadTupleMap) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestBadTupleMap) --data-dir=$(SPL_DATA_DIR_TestBadTupleMap) 

clean-TestRemoveAttribute: 
	rm -rf $(SPL_DATA_DIR_TestRemoveAttribute)
	$(SPLC) $(SPLC_FLAGS_TestRemoveAttribute) -C -M $(SPL_MAIN_COMPOSITE_TestRemoveAttribute) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestRemoveAttribute) --data-dir=$(SPL_DATA_DIR_TestRemoveAttribute) 

clean-TestNegativeDocBufferTimer: 
	rm -rf $(SPL_DATA_DIR_TestNegativeDocBufferTimer)
	$(SPLC) $(SPLC_FLAGS_TestNegativeDocBufferTimer) -C -M $(SPL_MAIN_COMPOSITE_TestNegativeDocBufferTimer) \
	  --output-dir=$(SPL_OUTPUT_DIR_TestNegativeDocBufferTimer) --data-dir=$(SPL_DATA_DIR_TestNegativeDocBufferTimer) 

clean-appset:
	rm -f $(SPL_SUBMIT_APPSET)
