# lagom-poc

Building Reactive Java 8 application with Lagom framework. This is a classic CRUD application.

## Tools Integrated

### >> Flyway-maven
Flyway is an open-source database migration tool. This project integrates H2 datbase and flyway tool and migrations written in this project are in SQL.</br>
The migrations are created in the directory `src/main/resources/db/migration/`</br>
These migrations can be executed using `mvn flyway:migrate`</br>
To check if the migrations have been successfully applied, issue `mvn flyway:info`</br>

### >> SonarQube (version 6.2)
SonarQube (formerly known as Sonar) is an open-source platform for continuous inspection of code quality. Sonar is a web based code quality analysis tool for MAVEN based JAVA projects. It covers a wide area of code quality checkpoints which include: Architecture & Design, Complexity, Duplications, Coding Rules, Potential Bugs, Unit test etc.

#### Command to start SonarQube server
`YOUR_DIR_PATH/sonarqube/bin/[OS]/sonar.sh console`

#### SonarQube runs on port 9000 by default  `http://localhost:9000`

#### Default username & password for administrator
`username - admin`</br>
`password - admin`

#### Command to generate report on SonarQube server
`mvn clean - to clean the existing resources`</br>
`mvn install`</br>
`mvn sonar:sonar`

### >> Cobertura
Cobertura is a free Java tool that calculates the percentage of code accessed by tests. It can be used to identify which parts of your Java program are lacking test coverage. It is based on jcoverage.

#### Command to run test cases and generate code coverage report
`mvn cobertura:cobertura`

### >> Checkstyle
Checkstyle is a development tool to help programmers write Java code that adheres to a coding standard. The Checkstyle Plugin generates a report regarding the code style used by the developers.

#### Checktyle Plugin has some goals which are as follows
`checkstyle:checkstyle` is a reporting goal that performs Checkstyle analysis and generates a report on violations.<br>
`checkstyle:check` is a goal that performs Checkstyle analysis and outputs violations or a count of violations to the console, potentially failing the build. It can also be configured to re-use an earlier analysis.

#### Command to run checkstyle
`mvn checkstyle:checkstyle`<br>
`mvn checktyle:check`

### >> Findbugs
FindBugs looks for bugs in Java programs. It is based on the concept of bug patterns.FindBugs uses static analysis to inspect Java bytecode for occurrences of bug patterns.FindBugs is a static code analysis tool which identifies problems found from Java code.

#### Command to generate Findbugs report
`mvn findbugs:findbugs`

### >> PMD
A Maven plugin for the PMD toolkit, that produces a report on both code rule violations and detected copy and paste fragments, as well as being able to fail the build based on these metrics.
PMD has additional rules to check for cyclomatic complexity, Npath complexity, etc which allows you write healthy code.Another advantage of using PMD is CPD (Copy/Paste Detector).It finds out code duplication across projects and is not constrained to JAVA

#### The plugin has following goals
`pmd:pmd` creates a PMD site report based on the rulesets and configuration set in the plugin. It can also generate a pmd output file aside from the site report in any of the following formats: xml, csv or txt. <br>
`pmd:cpd` generates a report for PMD's Copy/Paste Detector (CPD) tool. It can also generate a cpd results file in any of these formats: xml, csv or txt.

#### Command to generate PMD reports
`mvn pmd:pmd`
