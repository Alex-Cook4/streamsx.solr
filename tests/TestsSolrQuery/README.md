To run these tests, you must do the following setup:
1. Start your solr server with the following command (make sure all instances of the solr server are stopped before this and that you have deleted the techproducts collection:  bin/solr delete -p 8984 -c techproducts):
	bin/solr start -p 8984 -s "example/techproducts/solr"
2. Build using make 
3. ./runTests.sh http://<solr-host>:<solr-port>/solr/ techproducts

Verify TestQuerySimple:
	Console output of ResponsePrinter should have 10 lines of approximately this: 
		{solr_response="<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><result name=\"response\" numFound=\"12\" start=\"0\"><doc><str name=\"id\">0579B002</str><str name=\"name\">Canon PIXMA MP500 All-In-One Photo Printer</str><arr name=\"cat\"><str>electronics</str><str>multifunction printer</str><str>printer</str><str>scanner</str><str>copier</str></arr></doc><doc><str name=\"id\">100-435805</str><str name=\"name\">ATI Radeon X1900 XTX 512 MB PCIE Video Card</str><arr name=\"cat\"><str>electronics</str><str>graphics card</str></arr></doc><doc><str name=\"id\">6H500F0</str><str name=\"name\">Maxtor DiamondMax 11 - hard drive - 500 GB - SATA-300</str><arr name=\"cat\"><str>electronics</str><str>hard drive</str><str>memory</str></arr></doc></result></response>"}

Verify TestCollectionAsAttribute:
	Console output of ResponsePrinter should have 10 lines of approximately this: 
        {solr_response="<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><result name=\"response\" numFound=\"4\" start=\"0\"><doc><str name=\"id\">F8V7067-APL-KIT</str><float name=\"weight\">4.0</float><float name=\"price\">19.95</float></doc><doc><str name=\"id\">IW-02</str><float name=\"weight\">2.0</float><float name=\"price\">11.5</float></doc><doc><str name=\"id\">MA147LL/A</str><float name=\"weight\">5.5</float><float name=\"price\">399.0</float></doc><doc><str name=\"id\">9885A004</str><float name=\"weight\">6.4</float><float name=\"price\">329.95</float></doc></result></response>"}

Verify TestPassingForwardAttributes:
	Console output of ResponsePrinter should have 10 lines of approximately this (notice the non-solr attributes): 
        {random_string="0.414113664533943",random_int=0,random_float=0.127788514364511,solr_response="<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><result name=\"response\" numFound=\"17\" start=\"0\"><doc><str name=\"id\">GB18030TEST</str><float name=\"price\">0.0</float><bool name=\"inStock\">true</bool></doc><doc><str name=\"id\">MA147LL/A</str><float name=\"price\">399.0</float><bool name=\"inStock\">true</bool></doc><doc><str name=\"id\">TWINX2048-3200PRO</str><float name=\"price\">185.0</float><bool name=\"inStock\">true</bool></doc></result></response>"}

    
Verify TestQueryError:
	Console output of ErrorPrinter should have 10 lines of approximately this: 
        {error="Tue Mar 01 06:55:00 EST 2016: Query http://localhost:8983/solr/techproducts/select/?q=*:*&sort=id asc&omitHeader=true failed with error: java.io.IOException: Server returned HTTP response code: 400 for URL: http://localhost:8983/solr/techproducts/select/?q=*:*&sort=id asc&omitHeader=true"}

