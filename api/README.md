# API

OpenAPI representation of legacy state of REST API as base for an up-to-date implementation.

The entrypoint of the API can be found int the [api.yaml](src/main/resources/api.yaml)

## General Information

Uses `openapi-generator-maven-plugin` to generate a Spring Boot 3 (e.g. using `jakarta` instead of `javax` imports) compatible server API as interfaces with dto models.

See [components](src/main/resources/components/README.md) and [paths](src/main/resources/paths/README.md) readme files for further explanation of the structure.

## Getting Started

Running `mvn clean install` will (re-)create the API server side.

## Represented use cases

### driver

1. [x] create driver
2. [x] read drivers
3. [x] update driver

### taxi

1. [x] create taxi
2. [x] read taxis

### customer

1. [x] create customer
2. [x] read customers

### location

1. [x] create location
2. [x] read locations

### shift

1. [x] create shift
2. [x] read shifts
3. [x] read shift
4. [x] update shift

### trip

1. [x] create trip
2. [x] read trips
3. [x] delete trip