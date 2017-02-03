# lagom-poc

## flyway-maven
Flyway is an open-source database migration tool. This project integrates H2 datbase and flyway tool and migrations written in this project are in SQL.</br>
The migrations are created in the directory `src/main/resources/db/migration/`</br>
These migrations can be executed using `mvn flyway:migrate`</br>
To check if the migrations have been successfully applied, issue `mvn flyway:info`</br>
