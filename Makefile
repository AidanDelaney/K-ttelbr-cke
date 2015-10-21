all: build deploy run

build:
	mvn package -DpromoteTransitiveDependencies=true

deploy:
	sudo docker-compose build

run:
	sudo docker-compose up
