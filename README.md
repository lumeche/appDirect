This application is a evaluation from appDirect to apply for senior software engineer.
It was made with JAVA, maven and spring-boot.

Since it contains the web server embedded (Apache tomcat) there is no need to build a war file from it and deploy it in
any web container.

BUILD APPLICATION
In order to compile run "mvn clean package". It will generate a target folder where the jar file is located.

START SERVER
To start the server run "java -jar