#!/bin/bash

SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPTPATH=$(dirname "$SCRIPT")
#echo $SCRIPTPATH

(cd microservices/discovery; $SCRIPTPATH/gradlew buildContainer)
(cd microservices/authserver; $SCRIPTPATH/gradlew buildContainer)
(cd microservices/edge; $SCRIPTPATH/gradlew buildContainer)
(cd microservices/frontend; $SCRIPTPATH/gradlew buildContainer)
#(cd microservices/hystrixdashboard; $SCRIPTPATH/gradlew buildContainer)
#(cd microservices/turbine; $SCRIPTPATH/gradlew buildContainer)

