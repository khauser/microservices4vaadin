start /D microservices\discovery               %CD%\gradlew buildDocker
start /D microservices\authserver              %CD%\gradlew buildDocker
start /D microservices\edge                    %CD%\gradlew buildDocker
start /D microservices\frontend                %CD%\gradlew buildDocker
#start /D microservices\turbine                 %CD%\gradlew buildDocker
#start /D microservices\hystrixdashboard        %CD%\gradlew buildDocker
