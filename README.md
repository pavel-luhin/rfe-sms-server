# RFE Sms Server
RFE Sms Server - is a simple application that provides api to send sms notifications. The main difference between this and other existing applications is that this one were created especially for Belarusian State University projects, such as https://cv.bsu.by and other internal ones. Under the hood this application using http://websms.by service.
This application contains users management system, ability to send sms directly from server or from external applications, collecting statistics of sent sms, duplicating sms messages to email.

# Getting Started
First you have to create database schema and user for it.
```
CREATE DATABASE sms_server;
CREATE USER 'sms-server-user'@'localhost' IDENTIFIED BY 'passkey0';
GRANT ALL ON sms_server.* TO 'sms-server-user'@'localhost';
FLUSH PRIVILEGES;
```
To start application you need to have docker installed on you machine. Then you just have to type
```
docker run pluhin/sms-server:latest java -jar sms-server.jar
```
While start you need to specify the following environment variables for the application:
* ```-Dspring.datasource.url``` or ```-Ddatabase.url``` in older version - url of database schema. In this example ```jdbc:mysql://localhost:3306/sms_server```
* ```-Dspring.datasource.username``` or ```-Ddatabase.username``` in older version - username of database user. In this example ```sms-server-user```
* ```-DDspring.datasource.password``` or ```-Ddatabase.password``` in older version - password of database user. In this example ```some-password```
* ```-Dsms.test``` - in older versions test flag, send real or mock request to websms system. Deprecated. Use build profiles instead.
* ```-Dserver.url``` - server domain or IP address with port where application is going to be deployed. Needed to send in email to newly registered users.
* ```-Dcredentials.cache.enabled``` - in older versions flat to enable or disable credentials cache. Deprecated since version 1.4. Credentials cache is active by default now.
* ```-Dserver.port``` - specifies port to bind to server. 8080 is by default

#Alternative start without docker
To start application you need to get Git, JRE that provides Java 8, Apache Maven at least 3.2 version, and MySQL at least 5.6 version.
First, you need to clone this repository.
```
git clone git@github.com:p-luhin/rfe-sms-server.git
```
Then you have to step into the cloned directory and specify branch you want to deploy. The latest stable version will always be in master branch that is active by default, but you can specify any branch you want.
Then you have to build application in jar file. To do this you need from project directory specify the following command
```
mvn clean install -Pprod
```
This will compile and pack all project files in jar file.
The project contains two profiles: ```prod``` and ```local```. The main difference is that production profile sends requests directly to websms server, and local one returns random or predefined data.

This set of mysql commands will create database schema, user and grant all access to created user.
Then you need to specify the following environment variables for the application:
* ```-Dspring.datasource.url``` or ```-Ddatabase.url``` in older version - url of database schema. In this example ```jdbc:mysql://localhost:3306/sms_server```
* ```-Dspring.datasource.username``` or ```-Ddatabase.username``` in older version - username of database user. In this example ```sms-server-user```
* ```-DDspring.datasource.password``` or ```-Ddatabase.password``` in older version - password of database user. In this example ```some-password```
* ```-Dsms.test``` - in older versions test flag, send real or mock request to websms system. Deprecated. Use build profiles instead.
* ```-Dserver.url``` - server domain or IP address with port where application is going to be deployed. Needed to send in email to newly registered users.
* ```-Dcredentials.cache.enabled``` - in older versions flat to enable or disable credentials cache. Deprecated since version 1.4. Credentials cache is active by default now.
* ```-Dserver.port``` - specifies port to bind to server. 8080 is by default

After installing, you have execute jar file named ```sms-server-X.jar```, where X - application version from ```target``` folder.

To start application you need to execute ```java -jar sms-server-X.jar``` where ```sms-server-X.jar``` - is the name of the generated jar file.
After running this command, you can access sms server on ```http://localhost:8080``` 
