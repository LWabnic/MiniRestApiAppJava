# MiniRestApp

MiniRestApp is a small Java 17 solution consisting of two main projects: CsvProcessor (MiniRestAppClient) and WebAPI (MiniRestApiServer).

## CsvProcessor (Client Application)

CsvProcessor is a console application that reads a CSV file containing customer data. The customer data includes the following fields:

- Customer Ref
- Customer Name
- Address Line 1
- Address Line 2
- Town
- County
- Country
- Postcode

The CSV file is read using the opencsv library, which takes care of parsing the CSV format and mapping the data to Customer objects.

Each Customer object is then serialized to JSON format and sent to a REST API endpoint (`api/customers`) provided by the Server Application (WebAPI) using a POST request. The JSON serialization and the HTTP requests are handled by classes from the `org.json` and `org.springframework.http` packages, respectively.

The path of the CSV file is expected to be passed as a command line argument when running the CsvProcessor application.

## WebAPI (Server Application)

WebAPI is a Spring Boot project that provides the REST API for managing customer data. It receives the data from the client application, CsvProcessor, and handles the data persistency.

The API includes two main endpoints:

- **POST api/customers**: Accepts a Customer object in JSON format in the request body and saves it to a SQL Server database using Spring Data JPA. The Customer object is expected to include all the fields mentioned above.

- **GET api/customers/{id}**: Retrieves a Customer with the specified id from the database and returns it in JSON format.

The data is stored in a SQL Server database, and Spring Data JPA is used as the ORM (Object-Relational Mapper) for data access. The `CustomerRepository` interface is the JPA repository that represents the database session and can be used to query and save instances of the `Customer` class.

The `CustomerController` class contains the action methods for the POST and GET endpoints.

## Testing

The solution uses JUnit as the testing framework and follows a Test-Driven Development (TDD) approach. This means that tests were written for each feature before the actual implementation.

Tests were written to verify the correct parsing of the CSV file, the correct sending of HTTP requests, and the correct saving and retrieving of data in the database. The Mockito library was used to mock dependencies for the tests, and the H2 in-memory database was used to simulate the database for testing purposes.
