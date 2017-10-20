# Java Task

## Avoid Out of Memory reading huge TEXT files.

In order to run this project, use the command below. The result will be shown on console.

```
mvnw clean install spring-boot:run
```

DemoApplication is the main class of this project. The layers are separated between domain, repository, usecase, design based on "The Clean Architecture".
In addition to Spring Boot, I used the H2 database and the Lombok library.
I tried to keep myself in the main focus, avoiding an out-of-memory exception while reading the file. So I kept this project pretty simple.
For testing, I used JUnit and some Mockito features. Unfortunately, I had very little time to work on it because of my current job.