# OfferSuggestor
The api has following functionalities:  
Merchant Side:  
1) Search nearby Offers  
2) Get best parameters for nearby offers    

Customer Side:  
1) Get best nearby offers  

Steps to Run:
1) Create a new Project in VDP and add the apis: Merchant Search, Merchant Locator, Merchant Measurement, VMORC.  
2) Create a JAVA keystore (.jks file) as shown in: https://developer.visa.com/pages/working-with-visa-apis/two-way-ssl.   
3) Clone this repo, and import the folder in IntelliJ IDEA IDE for Java.  
4) Go to src/main/basics package.  
5) Update the keystore paths,username,passwords,etc authorization fields in MerchantLocatorCall.java, MerchantSearchCall.java, MerchantMeasurementCall.java, and OffersDataApiCall.java  
6) Run MainApplicationClass.java.  
7) Now, our api runs at localhost:8080. 
8) There are 3 accessible endpoints:  
a) localhost:8080/search  
b) localhost:8080/customer_search  
c) localhost:8080/filter  
9) Open index.html (Merchant Side UI) and enter StoreID to get results.  
10) Open customer.html (Customer Side UI) and enter postal code and category to get results.  
