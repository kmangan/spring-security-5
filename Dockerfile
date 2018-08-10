FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/spring-security-5-*.jar app.jar
ENV JAVA_OPTS="-server -Xms256m -Xmx256m -Xmn160m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]