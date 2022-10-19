# BankingApi

This Banking API has been built with the following resources:

1. "api/v1/accounts" - To facilate creation of accounts and retrieving them. 
2. "api/v1/customers" - To facilate creation of customers and retrieving them. 
3. "api/v1/deposit” - To facilate deposit to a target account. 
4. "api/v1/transfer” - To facilate moving money from a source account to a target account. 
5. "api/v1/withdraw" - To facilate withdrawal from a target account. 
6. "api/v1/transactions"   - To facilate report generation of all transactions in an account.  


To run the program, follow these steps:

1. Run docker-compose for the creation of the mongo DB. The docker file is located "src/main/docker-compose.yaml"
2. Run the spring application by opening the terminal and type "mvn spring-boot:run"
3. Open the Postman collection: "src/test/java/com/example/BankingAPI/BankingAPI.postman_collection.json" and run it on Postman.

Ensure that you have Java 17 or higher installed. 

Just modify the field values. Every operation has data validators so no need to worry. 

If any question comes by, email me @ngangawillie84@gmail.com
