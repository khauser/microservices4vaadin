start /D microservices\authserver              %CD%\gradlew bootRun
start /D microservices\discovery               %CD%\gradlew bootRun
start /D microservices\edge                    %CD%\gradlew bootRun

start /D microservices\frontend                %CD%\gradlew bootRun

REM start /D microservices\turbine                 %CD%\gradlew bootRun
REM start /D microservices\hystrixdashboard        %CD%\gradlew bootRun

