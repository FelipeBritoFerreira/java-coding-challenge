# Senior Java Engineer Coding challenge
 
## Install and Run
### Requirements
1. Docker/Docker Compose
2. maven
3. JDK 1.8
4. Git client

### How to Run?
Current setup provides you two ways to run the application


**Development mode**
- Open the project in your favourite IDE (Ex. IntelliJ IDEA).
- Run docker container for MySQL database
```
 docker-compose -f ./docker/docker-compose-dev.yml up -d
```
- Run project from your IDE

Application resources available:
 - Host URL: [http://localhost:8080](http://localhost:8080)
 - Swagger API Docs:  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
 
 
**Production mode**
 - Create application JAR by running
```
mvn clean install 
```
- Run docker containers fot Mysql and the Application:
```
docker-compose -f ./docker/docker-compose-prod.yml up --build -d
```
Application resources available:
  - Host URL: [http://localhost:9001](http://localhost:9001)
  - Swagger API Docs:  [http://localhost:9001/swagger-ui.html](http://localhost:9001/swagger-ui.html)
 
 ## How to submit your code
 - Fork current repository
 - Create a separate branch for your updates in the **Forked** GitHub repository. Your branch name should follow the template:
 ```
 cc_{first name}_{last name}
 ```
 - Create as many commits as you wish
 - Create Pull Request with your First and Last name as a Title and short description of which tasks were completed.
 - Notify the Hiring Manager when you are Done
  
  ## Tasks to DO:
  ### Task 1
 
 Currently, V1 Product object in API responses has the following structure:
 ```
{
  "brand": "string",
  "name": "string",
  "sellerUuid": "string",
  "stock": 0,
  "uuid": "string"
}
```
 
  Based on business requirements All Product Get endpoints should be updated to contain links on seller resources. It was decided to release the second version of the API (call it V2) with the following changes:
  - V2 Product endpoints should respond with the Product object of structure:
  ```
{
  "brand": "string",
  "name": "string",
  "seller": {
     "uuid": "string",
     "_links": {
        "self":{
            "href":"http://localhost:8080/sellers/{seller_uuid}"
        }
     }
  },
  "stock": 0,
  "uuid": "string"
}
```
- V2 Product objects sent in requests(POST and PUT actions) should remain the same as in V1.
- API V1 should NOT be changed for end customer (applications that implement API V1 should never break).
- V2 should be properly Documented (See Swagger API Docs)

### Task 2.

**Current behaviour:**

If Product Update (PUT request) changes current Stock of the product, then the seller of this product receives Email with warning.

**Behaviour to implement:**

If Product Update (PUT request) changes current Stock of the product, then the seller of this product receives Email and/or SMS with warning.
What type of notifications should be sent to seller SHOULD be defined in application.properties file (global settings for all sellers).

**IMPORTANT:** Implementation of communication with 3rd party SMS providers is not needed. Instead, please create log entry with following text template:
```
SMS Warning sent to {seller_UUID} (Phone: {seller_Phone}): {Product_name} Product stock changed
```

### Task 3

**Behaviour to implement:**
- API V2 should contain ``/api/v2/sellers/top10`` GET Endpoint.
- This endpoint should return array of maximum 10 sellers ordered by count of products they have for sale (count of entries in product table) from the largest to the smallest number. 
- New endpoint should be properly documented (See Swagger API Docs).

