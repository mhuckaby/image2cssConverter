#!/bin/bash
export MAVEN_OPTS='-Xdebug -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n'

if [ "$1" == "all" ]; then
	cpwd=`pwd`
	cd ..
	mvn clean install -Dmaven.test.skip=true
	cd $cpwd
fi

mvn jetty:run -Dmaven.test.skip=true
