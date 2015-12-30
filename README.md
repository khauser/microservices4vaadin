# microservices4vaadin
Sample application to show the secured integration of microservices and vaadin

Services:
* authserver: Authentification and authorization service with user login and registration
* edge: gateway for SSO and session generation
* frontend: Vaadin frontend with a simple "Hello, world!"
* discovery: service discovery with eureka
* turbine+hystrixdashboard: use hystrix as circuit breaker

ToDo:
* fix http://stackoverflow.com/questions/34400416/enableredishttpsession-fails-with-vaadin to setup spring security with SSO
* add a backend service

Prerequisites:
* install java 8
* install mysql
* install redis

Development:
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
