FROM maven:3-jdk-8
ADD . .
RUN mvn clean install -Pprod
RUN mv sms-server-api/target/sms-server.jar /