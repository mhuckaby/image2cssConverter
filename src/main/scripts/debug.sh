export JAVA_OPTS='-Xdebug -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=y'

#mvn clean install assembly:single

java $JAVA_OPTS -cp target/${project.artifact}-${project.version}-jar-with-dependencies.jar -jar target/${project.artifact}-${project.version}-jar-with-dependencies.jar $@

