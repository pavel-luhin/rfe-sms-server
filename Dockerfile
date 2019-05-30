FROM maven:3-jdk-8
ADD . .
RUN mvn clean install -Pprod
ENTRYPOINT ["java", "-jar", ""]