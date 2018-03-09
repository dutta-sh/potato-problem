# potato-problem

1. This mavenized project is built using spring boot and uses a bundled tomcat server running on port 8080.
2. The project can be built using build.bat
3. The build executes test cases and packages an uber jar in the target folder by the name: potato-problem.jar
4. The jar contains default application.properties file and log4j2.xml file
5. The jar can be executed using execute.bat (logging happens on console in this case)
6. execute.bat gives options to override the default property file (using custom.properties and custom-log4j2.xml) from command line
7. By importing the project into Intellij, all test cases as well as the application can be executed from there
8. Application has been written and tested using Intellij IDEA, Chrome browser (GET) and Postman (GET and POST)

To ensure the webservice is running, a healthcheck on can be done at:
http://localhost:8080/

To get bags from marketplace use one of the following GET calls:
http://localhost:8080/get
http://localhost:8080/get/{count}

To add a bag to the market place do the following POST call with the below mentioned JSON sample as body:
http://localhost:8080/add

{
    "potatoCount": 75,
    "price": 10,
    "supplier": "Patatas Ruben",
    "packDate": "2018-03-08 10:06:02 PM UTC"
}
