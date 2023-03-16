# Spring Boot Backend

Spring Boot implementation of Taxi Management backend.

## Getting Started

[//]: # (TODO)

## Liquibase

Uses Liquibase for automatically, version-controlled database changes

### Generate changelog from existing database

Update the jdbc-string as well as the username/password combination in `liquibase.properties` and execute the following command:

    mvn liquibase:generateChangeLog -P liquibase

which will use the `liquibase` profile defined in `pom.xml`

### Best Practices

Best practices when using Liquibase can be found here https://www.liquibase.org/get-started/best-practices

## Implemented Use Cases

### driver

1. [x] create driver
2. [x] read drivers
3. [x] update driver

### taxi

1. [x] create taxi
2. [x] read taxis
3. [x] update taxi

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
