#!/bin/bash

SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPTPATH=$(dirname "$SCRIPT")
#echo $SCRIPTPATH

(cd microservices/config; $SCRIPTPATH/gradlew buildDockerContainer)
(cd microservices/eventstore; $SCRIPTPATH/gradlew buildDockerContainer)
(cd microservices/discovery; $SCRIPTPATH/gradlew buildDockerContainer)
(cd microservices/authserver; $SCRIPTPATH/gradlew buildDockerContainer)
(cd microservices/edge; $SCRIPTPATH/gradlew buildDockerContainer)
(cd microservices/userservice; $SCRIPTPATH/gradlew buildDockerContainer)
(cd microservices/frontend; $SCRIPTPATH/gradlew buildDockerContainer)
#(cd microservices/hystrixdashboard; $SCRIPTPATH/gradlew buildDockerContainer)
#(cd microservices/turbine; $SCRIPTPATH/gradlew buildDockerContainer)

