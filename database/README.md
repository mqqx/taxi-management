# Database

Local representation of database for easy development.

## Getting Started

1. Ensure docker is running (e.g. see [how to install docker desktop](https://docs.docker.com/desktop/install/mac-install/) if you're new to docker)

2. Start local development mariadb `taxi_management` database by running `docker-compose.yaml`

Tables will be created and updated automatically through `Liquibase` changelog on `backend-spring` [backend-spring](../backend-spring/README.md) startup.