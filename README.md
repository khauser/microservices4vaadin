# microservices4vaadin ![CircleCI](https://circleci.com/gh/khauser/microservices4vaadin.png?style=shield&circle-token=e56d14269e12d73dcc8b45b8dad847985d4e20fb)
Exemplary application to show the SSO and OAuth2 secured integration of microservices with Spring Cloud and Vaadin. Main concepts in this projects are:
* Microservices ("Software that Fits in Your Head")
* Secured Gateway (SSO and OAuth2)
* Service Discovery
* Circuit Breaking
* Shared Session overall Services
* Event store to fulfill CQRS principles

## Architecture:
![Architecture](/doc/Architecture.png)

* **Authserver**:
  * Authentification and authorization service
  * Allows user login and also user registration via REST
  * Generates spring session (persisted in Redis) which also holds the security context
* **Configserver**
  * Centralized configuration of each service
* **Edge**:
  * SSO Gateway to Frontend and also directly to the Backend
  * UI for the landing page, the login and the registration panels
  * Gets the security context and the user data from spring session
* **Eventstore**:
  * Distribute events across microservices via RabbitMQ and persist them in MongoDB
* **Frontend**:
  * Vaadin frontend with some simple but responsive UI
  * Load balanced (Ribbon) access to backend
  * Gets user data from spring session
* **UserService**:
  * Represents the user domain
* **Backend**:
  * Simple but secured REST resource as backend for the frontend
* **Discovery**: service discovery with eureka
* **Turbine**+**Hystrixdashboard**: use hystrix as circuit breaker

ToDo:
* add a backend service

## Main frameworks
* Spring: [Boot] (http://projects.spring.io/spring-boot/), [Data JPA] (http://projects.spring.io/spring-data-jpa), [Session] (http://projects.spring.io/spring-session), [Cloud Security] (http://cloud.spring.io/spring-cloud-security)
* [Vaadin] (https://www.vaadin.com/)
* Netflix: [Zuul] (https://github.com/Netflix/zuul), [Eureka] (https://github.com/Netflix/eureka), [Hystrix] (https://github.com/Netflix/Hystrix)
* [AxonFramwork] (http://www.axonframework.org/)
* [Rancher] (http://rancher.com/)

## Installation
* install JDK 8
* install MySQL 5.7
* install Redis+RabbitMQ+MongoDB (you can also use the [docker-compose.yml](docker-compose.yml) file
* Run `gradlew clean build` to compile and build the application
* Run `start-all.bat` to start the list of services
* `http://localhost` should bring you to the landing page (with a redirect to https)

## Development:
* Git, Eclipse with Gradle IDE (Buildship), and [lombok] (https://projectlombok.org/)

Set up project:
* checkout git repository
* run `docker-compose up -d` do start dependent Redis, RabbitMQ and MongoDB services
* `gradlew clean build` to compile project
* run `start-all.bat` in windows or `start-all.sh` in unix

Hints:
* Database for the authserver `microservice4vaadin_authserverdb` and for the userservice `microservices4vaadin_userservicedb` needs to be added by hand in MySQL. Initial test credentials then are `ttester@test.de/quert6`.
