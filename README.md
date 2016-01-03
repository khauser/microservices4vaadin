# microservices4vaadin
Sample application to show the secured integration of microservices and vaadin

Services:
* **authserver**: Authentification and authorization service with user login and also allows user registration via REST
* **edge**: Gateway for SSO and UI for the landing page, the login and the registration panels
* **frontend**: Vaadin frontend with a simple "Hello, world!"
* **backend**: Simple but secured REST resource as backend for the frontend
* **discovery**: service discovery with eureka
* **turbine**+**hystrixdashboard**: use hystrix as circuit breaker

ToDo:
* fix http://stackoverflow.com/questions/34400416/enableredishttpsession-fails-with-vaadin to setup spring security with SSO
* add a backend service

## Main frameworks
* [Spring Boot] (http://projects.spring.io/spring-boot/)
* [Spring Data JPA] (http://projects.spring.io/spring-data-jpa/)
* [Spring Session] (http://projects.spring.io/spring-session)
* [Spring Security] (http://projects.spring.io/spring-security/)
* [Vaadin] (https://www.vaadin.com/)
* [Zuul] (https://github.com/Netflix/zuul)
* [Eureka] (https://github.com/Netflix/eureka)
* [Hystrix] (https://github.com/Netflix/Hystrix)

## Installation
* install JDK 8
* install MySQL
* install Redis
* install RabbitMQ
* Run `gradlew clean build` to compile and build the application
* Run `start-all.bat` to start the list of services

## Development:
* Eclipse with Gradle IDE, and lombok (see hints for project import)

Set up project:
* checkout git repository
* "gradlew clean build" to compile project
* run start-all.bat or equivalent in unix

Hints:
* database for the authserver needs to be initialized by setting "ddl-auto: create", running the the authserver-service and importing *auth_init.sql* into "microservice4vaadin_authserverdb"
* **eclipse** has some problems with the subfolder structure of gradle projects. To handle this:
  - first copy microservices subfolders to the root folder
  - temporarily adjust *settings.gradle* with the new locations
  - import the gradle project to eclipse to create the ".project" files
  - and move the services back to the "microservices" folder.
  - With a new import all subprojects shall be there
