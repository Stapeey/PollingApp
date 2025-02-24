to run have java17 or later installed and maven installed

TO COPY

open up a terminal and enter the following:

git clone https://github.com/Stapeey/PollingApp.git

cd PollingApp

mvn clean install

TO CONFIGURE

navigate to: task/src/main/resources/application.properties

run your database server and pass its parameters into the config file:

spring.datasource.url=YOUR_DB_URL (for mysql: jdbc:mysql://localhost:3306/polls)

spring.datasource.username=YOURUSERNAME

spring.datasource.password=YOURPASSWORD

spring.datasource.driver-class-name=YOUR_DB_DRIVER (for mysql: com.mysql.cj.jdbc.Driver)

save

TO RUN

navigate to /PollingApp open a terminal and enter: mvn spring-boot:run

TO USE

open a webbrowser and visit the following: localhost:8080
and enjoy, view, and create polls, register, login, vote add images and see the results updating and displaying
