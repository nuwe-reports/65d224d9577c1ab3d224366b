# Accwe Hospital Java Back End Developer challenge

This project solves the Backend programming challenge with Java and Spring Boot to create a microservice for a hospital.

## Coding of the POST method of appointment for the creation of a new appointment.
It has been solved taking into account the relevant validations for the data and that two appointments can overlap in time and in the same room through the `Appointment` model function, `overlaps(Appointment appointment)`.

The note in the code suggests a better way to solve this:
I do not consider this to be efficient in a database with many appointments. The ideal would be to first extract the logic to a service, query the database and treat this as a batch, fetching the appointments as you iterate through an ordered list and only until an overlap is encountered. But this cannot be done due to limitations in editing files other than this one and the test files.

## Step 2. Implementation of the unit tests for the different entities and their controllers.
Unit tests have been implemented for the different entities and controllers, trying to maintain a 100% coverage.

## Step 3. Clean code.
A clean code structure has been maintained and JavaDoc has been implemented for a better understanding of the implemented functionalities.

## Step 4. Docker for deployment.
Two Dockerfile files have been made to deploy the Spring Boot Docker with Maven and Mysql with a database called `accwe-hospital` to which the Spring Boot Docker will connect.

In `Dockerfile.maven` you will build a Maven image, copy the project source code and the `pom.xml`, perform a clean install and run Spring Boot to build your Tomcat server.

In `Dockerfile.mysql` a MySql image is builded, the database to be created at startup and the password for the root user are set as environtment variables.


A `docker-compose.yml` file has also been created where a network is established to raise the containers built through the Dockerfile and the relevant configurations, environment variables, etc. to run properly, creating volumes so that the data is maintained between executions.
In order to use this file, we need to create an `.env` file where the environment variables needed to run the file will be set. An `example.env` file has been generated where these variables appear in order to facilitate the configuration. The example configuration will be sufficient for the `.env` file. The `.env` file is not in the repository as it could contain sensitive information in the future, in the hypothetical case of scaling the application.

## Optional step. UML diagram.
Generated through a `.puml`file with `PlantUML`

```
                                ,-----------------------.                               
                                |Appointment            |                               
                                |-----------------------|                               
                                |- id: bigint           |                               
                                |- finishes_at: datetime|                               
                                |- starts_at: datetime  |                               
                                |- doctor_id: bigint    |                               
                                |- patient_id: bigint   |                               
                                |- room_id: varchar(255)|                               
                                `-----------------------'                               
          --------------------------------- |--------------------------                
          | doctor_id                       | patient_id              |                 
,--------------------------.  ,--------------------------.            |                 
|Doctors                   |  |Patient                   |            | room_name                 
|--------------------------|  |--------------------------|   ,-------------------------.
|- id: bigint              |  |- id: bigint              |   |Room                     |
|- age: int                |  |- age: int                |   |-------------------------|
|- email: varchar(255)     |  |- email: varchar(255)     |   |- room_name: varchar(255)|
|- first_name: varchar(255)|  |- first_name: varchar(255)|   `-------------------------'
|- last_name: varchar(255) |  |- last_name: varchar(255) |                              
`--------------------------'  `--------------------------'                              
```
