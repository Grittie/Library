# Trainee library app

 [Spring Boot](http://projects.spring.io/spring-boot/) REST api.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.example.librarytrainee.LibraryTraineeApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## API paths
### Post
To create a book
```shell
/api/books/
```
To create/add author(s) to a book
```shell
/api/books/id:/author
```

### Get
Retrieve all books
```shell
/api/books	
```
Retrieve book by id
```shell
/api/books/:id
```

Retrieve all authors
```shell
/api/authors	
```
Retrieve author by id
```shell
/api/authors/:id	
```

Retrieve all authors from book
```shell
/api/books/:id/authors
```

### Put
Update book information
```shell
/api/books/:id/
```
Update author information
```shell
/api/authors/:id/
```

### Delete
Delete a book
```shell
/api/books/:id/
```
Delete an author
```shell
/api/authors/:id/
```
Delete an author from a book
```shell
/api/book/:id/authors/:id/
```