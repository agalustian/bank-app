## Awesome bank
Add money to card, ship it everybody.

- [Tech stack](#Tech-stack)
- [Known issues](#Known-issues)
- [Rules for contributors](#Rules-for-contributors)
- [Architecture](#Architecture)
    - [Future concerns](#Future-concerns)
- [Project structure](#Project-structure)
- [Useful commands](#Usefull-commands)

## Tech stack
Language: `Java 21`

Framework: `Spring core + Spring boot + Spring webMVC + Spring JPA + Spring security + Spring cloud`

Test tools: `Spring boot test \ JUNIT 5 \ Jupiter \ Mockito \ TestContainers`

Build tools: `Gradle`

HTML templates: `Thymeleaf`

Observability: `Actuator`

## Rules for contributors:
We're using:
- conventional-commits;
- folder structure convention;
- avoiding GOD files.

We're respecting:
- SOLID;
- DRY;
- KISS;
- YAGNI;
- and other good principles.

## Architecture
Project use `MVC` architecture

### Future concerns:
- Moving to `SPA` instead of templates.

## Project structure
Multimodule concept.

#### Shopfront module
- controllers - for HTTP requests
- models - as business model unit
- services - as business models orchestrator
- repositories - as layer for database encapsulation
- resources/templates - view templates

## Usefull commands

Run unit tests: `sh ./gradlew test`

Run compiling all clients + java: `sh ./gradlew compileJava`

Run integration tests: `sh  ./gradlew integrationTest`

Build jar: `sh  ./gradlew build` - path `build/libs/[service]-0.0.1.jar`

Build Dockerfile (in each module):  `sh ./scripts/build-docker-images.sh`

Run infra (postgres, keycloak, consul): `sh ./scripts/start-infra`

## Actuator endpoints
Opened actuator endpoints list:
- health
- info
- metrics

Example: `localhost:8081/actuator/metrics`
