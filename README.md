# potato-problem

1. This mavenized project is built using spring boot and uses a bundled tomcat server running on port 8080.
2. The project can be built using build.bat
3. The build executes test cases and packages an uber jar in the target folder by the name: potato-problem.jar
4. The jar contains default application.properties file and log4j2.xml file
5. The jar can be executed using execute.bat
6. execute.bat gives options to override the default property file (using custom.properties, which can in turn be used to override log4j2 file) from command line
7. By importing the project into Intellij, all test cases as well as the application can be executed from there
8. Application has been written and tested using Intellij IDEA, Chrome browser (GET) and Postman (GET and POST)

To ensure the webservice is running, a healthcheck on the context root can be done at: http://localhost:8080/
