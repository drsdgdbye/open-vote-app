# Open Vote app

This app was created for assistance in monitoring the elections of deputies of the State Duma of Russia, regional and local elections.
The app allows checking candidates and voting in parallel to create own clear voting statistics.

## Tech stack

+ Java 8
+ Spring Boot
+ Spring Data JPA
+ Spring Security
+ Spring Mail
+ Thymeleaf
+ JHipster
+ PostgreSQL
+ Liquibase
+ JUnit 5
+ Swagger 2
+ Mapstruct

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
