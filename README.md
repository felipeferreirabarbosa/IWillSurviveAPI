## I Will Survive - A TRZ (The Resident Zombie) API

This backend API whose purpose store informations of survivors in the apocalyptic scenario caused by Twiter Virus. Consist in a RESTful API make with Spring 2.3.4, Hibernate, Java 1.8, Lombok, JPA, JUnit for the tests and Swagger for the documentation.

### Instructions and recommendations for running the API

This project was developed at IDE Intellij (IDEA Community 2020.2.3), so I recommend that it be tested on it. To begin, run the main class IWillSurviveApplication.java.

The operations to create a survivor in database(/create), trade items (/trade) and update the last location of a survivor (/{idSurvivor}/location) will be in localhost:8080/api/survivors.

The operations to snitch a infected survivor (/reportInfected) will be in localhost:8080/api/infectionReport.

The operations to see the healthy survivors (/healthy), the infected survivors (/infected), the percent of healthy (/percentOfNonInfected) and infected (percentOfInfected), the lost items (lostPoints) and items per survivor (/itemsPerSurvivor) will be in localhost:8080/api/report.

To see more, the documentation will be at http://localhost:8080/swagger-ui.html.

### Some data to test the API
At Postman:

POST http://localhost:8080/api/survivors/create

body: 
--First
{
    "name": "Felipe",
    "age": 25,
    "gender": "M",
    "lastLocation":"(-4.000,128.000)",
    "inventory":"Fiji Water:10,AK47:5,Campbell Soup:2"
}

--Secound
{
    "name": "Lina",
    "age": 22,
    "gender": "F",
    "lastLocation":"(-4.000,128.000)",
    "inventory":"AK47:10,First Aid Pouch:15,Fiji Water:1"
}

So:

Update Location:

PUT http://localhost:8080/api/survivors/2/location

body:
{
    "location":"(47.00,2.00)"
}

Trade
PUT http://localhost:8080/api/survivors/trade

body:
{
    "idTrader": 1,
    "itemsTrader": "Campbell Soup:2",
    "idReceiver": 2,
    "itemsReceiver": "First Aid Pouch:1,Fiji Water:1"
}
