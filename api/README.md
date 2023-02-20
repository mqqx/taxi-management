# API

OpenAPI representation of legacy state of REST API as base for an up-to-date implementation.

## General Information

Uses `openapi-generator-maven-plugin` to generate a Spring Boot 3 (e.g. using `jakarta` instead of `javax` imports) compatible server API as interfaces with dto models.

## Getting Started

Running `mvn clean install` will (re-)create the API server side.

## Represented use cases

### driver

1. [x] create driver
2. [x] read driver
3. [x] update driver

### taxi

1. [x] create taxi
2. [x] read taxi

### customer

1. [x] create customer
2. [x] read customer

### location

1. [x] create location
2. [x] read location

### shift

1. [x] create shift
2. [x] read shift
3. [x] update shift

### trip

1. [x] create trip
2. [x] read trip
3. [x] delete trip