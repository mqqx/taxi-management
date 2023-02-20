# Spring Boot Backend

Spring Boot implementation of taxi-management backend.

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

