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

CI\CD: `k8s \ helm \ jenkins` - gateway + cash + transfer + accounts + notifications

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

Run infra (postgres, keycloak): `sh ./scripts/start-infra`

Helm release: `helm install bank ./bank-chart -n dev`

Helm release uninstall: `helm uninstall bank -n dev`

Check k8s pods: `kubectl get pods -n dev`

Helm chart lint: `helm lint ./bank-chart`

Helm chart tests: `helm test bank -n dev`

Kibana installation: `helm install kibana elastic/kibana -f .helm/infra-chart/charts/kibana/values.yaml -n dev`

Elasticsearch installation: `helm install elasticsearch elastic/elasticsearch -f .helm/infra-chart/charts/elasticsearch/values.yaml -n dev`

Logstash installation: `helm install logstash elastic/logstash -f .helm/infra-chart/charts/logstash/values.yaml -n dev`

## Actuator endpoints
Opened actuator endpoints list:
- health
- info
- metrics

Example: `localhost:8081/actuator/metrics`
