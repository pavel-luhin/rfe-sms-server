FROM openjdk:8

COPY sms-server-api/target/sms-server.jar /opt/sms-server.jar

ENTRYPOINT ["java", "-jar", "/opt/sms-server.jar"]