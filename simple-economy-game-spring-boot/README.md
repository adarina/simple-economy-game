# Backend

## Instructions

### Maven

To run application open terminal. In root application folder for Linux or Mac type `./mvnw clean install` and `mvnw.cmd clean install` for Windows

### IntelliJ IDEA

Open IntelliJ IDEA and select *File > Open...* Choose the project directory and click *OK*. *Select File > Project Structure...* and ensure that the Project SDK and language level are set to use **Java 11**. Open the Maven view with *View > Tool Windows > Maven*. In the Maven view, run the `compile` phase under Lifecycle and then the `mvn exec:java` goal to run the app.