# Project for Fetch Rewards

A project for Fetch Rewards' Senior Backend Engineer position. This creates a web service that matches the specifications listed [here](https://fetch-hiring.s3.us-east-1.amazonaws.com/points.pdf).

[View a video demonstration of this project](https://youtu.be/10v-_JJKJvg)

  

## Project Setup
To run, do the following:

 1. Dowload and install Java JDK 11 (https://developers.redhat.com/products/openjdk/download)
 2. Download and install maven (https://maven.apache.org/install.html)
 3. In cmd or terminal, navigate to root folder (`fetch-rewards-project`) and run
	
		mvn spring-boot:run

	This will start up the web service at http://localhost:8080/.

4. Test the API with an API testing tool. Here, we'll use Postman. Install and run the desktop version (https://www.postman.com/downloads/)
5. The following endpoints should be available. Send requests to them with Postman to test the application.
	* http://localhost:8080/transactions/transaction - POST to add a transaction
	* http://localhost:8080/transactions - GET to retrieve all transactions 
	* http://localhost:8080/balance - GET to retrieve all payers' point balances
	* http://localhost:8080/spend-points - POST to spend points

	 The [demo video](https://youtu.be/10v-_JJKJvg) shows how all the endpoints work and how to test them with Postman.
