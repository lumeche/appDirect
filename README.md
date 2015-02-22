This application is a evaluation from appDirect to apply for senior software engineer.
It was made with JAVA, maven and spring-boot.

Since it contains the web server embedded (Apache tomcat) there is no need to build a war file from it and deploy it in
any web container.

BUILD APPLICATION
In order to compile run "mvn clean package". It will generate a target folder where the jar file is located.

START SERVER
To start the server run "java -jar ./target/appDirect-1.0-SNAPSHOT.jar.
This will start the server by default in 8080

ACCEDING THE SERVER
The server has published two handlers.

/isAlive
Simple page that shows if the server is alive

/
Contains the information about the excising subscriptions and the users Assigned.