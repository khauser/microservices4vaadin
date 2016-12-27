#!/bin/bash

SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPTPATH=$(dirname "$SCRIPT")
#echo $SCRIPTPATH

(cd microservices/config; $SCRIPTPATH/gradlew pushDockerImage)
(cd microservices/eventstore; $SCRIPTPATH/gradlew pushDockerImage)
(cd microservices/authserver; $SCRIPTPATH/gradlew pushDockerImage)
(cd microservices/discovery; $SCRIPTPATH/gradlew pushDockerImage)
(cd microservices/edge; $SCRIPTPATH/gradlew pushDockerImage)
(cd microservices/userservice; $SCRIPTPATH/gradlew pushDockerImage)
(cd microservices/frontend; $SCRIPTPATH/gradlew pushDockerImage)
