FROM maven:3-jdk-8
ADD . .
RUN mvn clean install -Pprod
ENTRYPOINT ["java", "-jar", "sms-server-api/target/sms-server-api-1.4.jar"]