# microservices4vaadin
Exemplary application to show the SSO and OAuth2 secured integration of microservices with Spring Cloud and Vaadin. Main concepts in this projects are:
* Microservices ("Software that Fits in Your Head")
* Secured Gateway (SSO and OAuth2)
* Service Discovery
* Circuit Breaking

## Architecture:
![Architecture](/doc/Architecture.png)

* **Authserver**:
  * Authentification and authorization service
  * Allows user login and also user registration via REST
  * Generates spring session (persisted in Redis) which also holds the security context
* **Edge**:
  * SSO Gateway to Frontend and also directly to the Backend
  * UI for the landing page, the login and the registration panels
  * Gets the security context and the user data from spring session
* **Frontend**:
  * Vaadin frontend with a simple "Hello, world!"
  * Load balanced (Ribbon) access to backend
  * Gets user data from spring session
* **Backend**:
  * Simple but secured REST resource as backend for the frontend
* **Discovery**: service discovery with eureka
* **Turbine**+**Hystrixdashboard**: use hystrix as circuit breaker

ToDo:
* fix http://stackoverflow.com/questions/34400416/enableredishttpsession-fails-with-vaadin to setup spring security with SSO
* add a backend service

## Main frameworks
* Spring: [Boot] (http://projects.spring.io/spring-boot/), [Data JPA] (http://projects.spring.io/spring-data-jpa), [Session] (http://projects.spring.io/spring-session), [Cloud Security] (http://cloud.spring.io/spring-cloud-security)
* [Vaadin] (https://www.vaadin.com/)
* Netflix: [Zuul] (https://github.com/Netflix/zuul), [Eureka] (https://github.com/Netflix/eureka), [Hystrix] (https://github.com/Netflix/Hystrix)

## Installation
* install JDK 8
* install MySQL
* install Redis+RabbitMQ (you can also use the [docker-compose.yml](docker-compose.yml) file
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
