#!/bin/bash
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
#

SCRIPTDIR=$(dirname $0);
SOLR_SERVER=$1
SOLR_COLLECTION=$2

if [ "$#" -ne 2 ]; then
    echo "Must be of form ./runTests.sh <solr-server> <solr-collection>. Example: ./runTests.sh http://localhost:8983/solr techproducts"
    exit
fi

if [[ $STREAMS_DOMAIN_ID && $STREAMS_INSTANCE_ID ]]; then
    echo 
    echo "Starting the Solr tests."
    echo 
    echo "Using STREAMS_DOMAIN_ID environment variable: $STREAMS_DOMAIN_ID"
    echo "Using STREAMS_INSTANCE_ID environment variable: $STREAMS_INSTANCE_ID"
else
    echo
    echo "Default domain and instance environment variables are not set."  
    echo "Please source the streamsprofile.sh script."
    echo "Exiting without starting the Solr tests."
    echo
    exit
fi

if [ $STREAMS_ZKCONNECT ]; then
    echo "Using STREAMS_ZKCONNECT environment variable: $STREAMS_ZKCONNECT"
    zk_parameter="";
else
    echo "Using embedded ZooKeeper."
    zk_parameter="--embeddedzk";
fi

echo 
echo "Create the Streams domain: $STREAMS_DOMAIN_ID (if not already created)..."
streamtool mkdomain $zk_parameter --property sws.port=0 --property jmx.port=0 2>/dev/null

if [[ $? = 0 ]]; then
  # The Domain had to be created by this script.  
  # Record this so we can know to stop it when the app is stopped.
  touch $SCRIPTDIR/user_interface/DomainWasNotAlreadyCreated
  # Always do the genkey if we created the domain.
  echo "Generating automatic keys for the $STREAMS_DOMAIN_ID ..."
  streamtool genkey $zk_parameter
fi  

if [[ ! -e  $HOME/.streams/key/$STREAMS_DOMAIN_ID/$USER.pem ]]; then
  # Keep this check. We might not create the domain and still want to do the genkey check.
  echo
  echo "Generating automatic keys for the $STREAMS_DOMAIN_ID ..."
  streamtool genkey $zk_parameter
fi

echo
echo "Starting the Streams domain: $STREAMS_DOMAIN_ID (if not already running)..."
streamtool startdomain $zk_parameter 2>/dev/null 

echo
echo "Create the Streams instance: $STREAMS_INSTANCE_ID (if not already created)..."
streamtool mkinstance $zk_parameter 2>/dev/null

echo
echo "Starting the Streams instance: $STREAMS_INSTANCE_ID (if not already running)..."
streamtool startinstance $zk_parameter 2>/dev/null

PARAMETER_ARGS="-P solrServer=$SOLR_SERVER"
PARAMETER_ARGS="$PARAMETER_ARGS -P solrCollection=$SOLR_COLLECTION"

if [ -a newAppset ]
then
	rm newAppset
fi

for file in $(cat appset)
do 
	echo "$file $PARAMETER_ARGS" >> newAppset 
done

echo
echo "Starting the Solr jobs in the Streams instance..."
echo "streamtool submitjob $zk_parameter -f appset $PARAMETER_ARGS"
streamtool submitjob $zk_parameter -f newAppset

if [[ $? != 0 ]]; then
  echo  
  echo "The submit job was not successfull.  See error messages above."
  echo
  echo "Exiting without starting the Solr tests."
  echo
  exit
fi

# Short delay before launching firefox, allowing the UI Helper to generate its first set of data files.
# The UI Helper also has a short delay to allow the SPL file sinks to be initialized.
# Without these delays, firefox can come up initially with data from a prior run.
sleep 4

echo
echo "Start of the Solr Test applications is now complete."
echo
