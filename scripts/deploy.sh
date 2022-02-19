#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=AWSProj

echo ">> copy Build files"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo ">> check current running applications PID"
CURRENT_PID=$(pgrep -f1 ${PROJECT_NAME} | grep jar | awk '{print $1}')

echo "current application's pid : $CURRENT_PID"

if [ -z "$CURRENT_PID"]; then
        echo ">>no application is running.. no termination"
else
        echo ">> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo ">> deploy new application"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo ">> JAR Name : $JAR_NAME"

echo ">> chmod +x $JAR_NAME"
chmod +x $JAR_NAME

echo ">> run $JAR_NAME"
nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpath:/application-real.properties \
        -Dspring.profiles.active=real \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &


