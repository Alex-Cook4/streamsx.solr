To run these tests, you must do the following setup:
1. You must uncomment the dedupe updateRequestProcessorChain in the solrconfig.xml for collection techproducts(you must restart your server after modifying this file): 
 	solr-5.3.1/example/techproducts/solr/techproducts/conf
 	(you can also replace the solrconfig.xml file with the one found in this directory)
2. Start your solr server with the following command (make sure all instances of the solr server are stopped before this and that you have deleted the techproducts collection:  bin/solr delete -p 8984 -c techproducts):
	bin/solr start -p 8984 -s "example/techproducts/solr"
3. Build using make 
4. ./runTests.sh http://<solr-host>:<solr-port>/solr/ techproducts


Verification (URL: http://<solr-host>:<solr-port>/solr/techproducts/select/?wt=xml&q=*:*&sort=id%20asc&rows=2000
1. Check for ids in the range 100-399.
2. Make sure there are no ids in the 400-499 range.
3. 100-199 look for name: ManyTypesTest on odd ids and FeatureRemovalTest on even ids.
4. 200-299 look for name: DefaultSetTest for all values. 
5. 300-399 ids ending in 0-4 should have name ManyTypesSetAll. Ids ending in 5-9 should have name BadTupleTest.
6. Make sure there are 100 tuples with NoUniqueKeyTest as their name as well as randomly generated id fields. 
7. If you look at the application health graphs in Studio, only one application should be entirely green (the TestBadTupleMap app because it has tuple flow to the ErrorWriter operator). The rest should have yellow ErrorWriters, indicating no errors. 
