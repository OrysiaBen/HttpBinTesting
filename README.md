# HttpBinTesting

Some test for the site http://httpbin.org
## Set-Up

 - Inviroment: Intellij idea community edition
 - Language: Java 8
 -  Libraries :
 
		1) org.testng:testng:7.0.0
    
		2) org.oiue.services:org.oiue.service.http.client.apache:1.0.11
    
		3) org.json
		
		4) org.apache.logging.log4j:log4j-1.2-api:2.10.0
    
## Description
Apache HttpClient is what I selected for testing REST. Why I used it? Because in IT 
Academy SoftServe we used this and it is the most suitable for me.
The structure of my project is as follows:
1) in folder java I created 3 package:

     * httpBinClient (this package has 1 clases):
  
		** HttpBinClient 
   
     * utils (this package has 2 clases):
  
		** HttpBinConstants (for Constants)
    
		** HttpBinURL (for url)
     
     * System level:
     		** BaseHttpRequest (here are all the basic methods for REST)
     
2) in folder test I created 1 package:

	* httpBinTest (this package has 1 clases):
  
		** HttpBinClientTest (for testing HttpBinClien)
  
