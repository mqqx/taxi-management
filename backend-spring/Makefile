GIT_HASH ?= $(shell git log --format="%h" -n 1)
APP_NAME = backend-spring


build-dev:
	docker build -t ${APP_NAME} .

build:
	docker build -t ${APP_NAME}:${GIT_HASH} .

run-dev:
	docker run --name ${APP_NAME} -d -it -p 8080:8080 ${APP_NAME}

run:
	docker run --name ${APP_NAME} -d -it -p 8080:8080 ${APP_NAME}:${GIT_HASH}

stop-dev:
	docker stop ${APP_NAME}

stop:
	docker stop ${APP_NAME}:${GIT_HASH}

clean-dev:
	docker rmi -f ${APP_NAME}

clean:
	docker rmi -f ${APP_NAME}:${GIT_HASH}