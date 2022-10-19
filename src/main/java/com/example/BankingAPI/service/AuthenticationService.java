package com.example.BankingAPI.service;

public class AuthenticationService {
    //TODO WRAP AUTHENTICATION ACROSS USING AUTHENTICATION SERVER =>
    // CHECK THE FLOW DIAGRAM TO SEE MY THOUGHTS ON THIS HERE src/main/java/com/example/BankingAPI/service/Authentication_Service_Flow_Diagram.png
    /*
    The type of authentication needed here is called OAuth authentication where we will use the username and password to create a Basic authentication bearer. This will be sent to the Authentication server and a token will be issued and it will be used to access the resources server.
    Steps to issue the token:
    1. When a user open the deposit url but they aren’t logged in, the system will redirect them to the authentication url.
    For example: “http://127.0.0.1:8080/auth”. The client will enter their username and password and these details will be encoded by
    the base64-encoded value of username:password to generate the basic authentication bearer.

    2. The Authorization server will then deserialize the authentication bearer to obtain the username and password.
    If the username and password exists in the users database, the authentication server will generate a token and return to the user.
    The authentication server can implement the Spring security library. The token will have a certain time frame that needs to be refreshed.

    3. After authentication token has been acquired, the resources server will be accessed where the following resources will be accessed.
            "api/v1/accounts"
            "api/v1/customers"
            "api/v1/deposit”
            "api/v1/transfer”
            "api/v1/withdraw"
            "api/v1/transactions"
*/
}
