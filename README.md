# Glorified Raspberry PI Metering

"Of course it's a toy"

## Build

gradle build

## Running

### Production

java -jar glorified-temperature-metering/build/libs/glorified-temperature-metering.jar

### Mock temperature collection

java -Dspring.profiles.active=dev -jar glorified-temperature-metering/build/libs/glorified-temperature-metering.jar

## API Documentation

OpenApi documentation:
- http://localhost:8080/swagger-ui/index.html
- http://localhost:8080/api-docs
- http://localhost:8080/api-docs.yaml

## TODO Next

- mariadb runtime configuration
- simple ui with htmx
- database aggregation
- improve ui
- ui with history
- backend with Go
- backend with node.js
- ui with react, vue or similar heavy thingy
