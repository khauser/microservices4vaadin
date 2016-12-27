#!/bin/bash

nohup java -jar  microservices/config/build/libs/config-0.0.1-SNAPSHOT.jar > config.log &
sleep 10 & wait %1 &
nohup java -jar  microservices/eventstore/build/libs/eventstore-0.0.1-SNAPSHOT.jar > eventstore.log &
nohup java -jar  microservices/discovery/build/libs/discovery-0.0.1-SNAPSHOT.jar > discovery.log &
nohup java -jar  microservices/authserver/build/libs/authserver-0.0.1-SNAPSHOT.jar > authserver.log &
nohup java -jar  microservices/edge/build/libs/edge-0.0.1-SNAPSHOT.jar > edge.log &
nohup java -jar  microservices/userservice/build/libs/userservice-0.0.1-SNAPSHOT.jar > userservice.log &
nohup java -jar  microservices/frontend/build/libs/frontend-0.0.1-SNAPSHOT.jar > frontend.log &
#nohup java -jar  microservices/hystrixdashboard/build/libs/hystrixdashboard-0.0.1-SNAPSHOT.jar > hystrixdashboard.log &
#nohup java -jar  microservices/turbine/build/libs/turbine-0.0.1-SNAPSHOT.jar > discovery.log &
