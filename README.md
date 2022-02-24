# Open Vote app

[![Hits-of-Code](https://hitsofcode.com/github/drsdgdbye/open-vote-app?branch=master)](https://hitsofcode.com/github/drsdgdbye/open-vote-app/view?branch=master)

This app was created for assistance in monitoring the elections of deputies of the State Duma of Russia, regional and local elections.
The app allows checking candidates and voting in parallel to create own clear voting statistics.

## Tech stack

+ Java 8 https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white
+ Spring Boot https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
+ Spring Data JPA
+ Spring Security
+ Spring Mail
+ Angular https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
+ JHipster
+ PostgreSQL https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
+ Liquibase
+ JUnit 5

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. Node.js:
After installing Node you will need to run this command when dependencies change in package.json.

```
npm install
```
Run the following commands in two separate terminals

```
./mvnw


npm start
```

## Building for production

### Packaging as jar

To build the final jar and optimize the application for production, run:

```
./mvnw -Pprod clean verify
```
This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:
```
java -jar target/*.jar
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Testing

To launch your application's tests, run:

```
./mvnw verify
```
