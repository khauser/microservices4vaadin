#!/bin/bash

CONTAINER_IP='http://rancher-metadata.rancher.internal/latest/self/container/primary_ip'

STATUS_CODE=$(curl --write-out %{http_code} --silent --output /dev/null ${CONTAINER_IP})

if [ "$STATUS_CODE" -eq "200" ] ; then
    EUREKA_INSTANCE_HOSTNAME=$(curl http://rancher-metadata.rancher.internal/latest/self/container/primary_ip)
    echo "Instance hostname: " . $EUREKA_INSTANCE_HOSTNAME
    exec java -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker -Deureka.instance.preferIpAddress=true -Deureka.instance.ipAddress=$EUREKA_INSTANCE_HOSTNAME -Deureka.instance.hostname=$EUREKA_INSTANCE_HOSTNAME /app.jar
else
    exec java -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker /app.jar
fi
