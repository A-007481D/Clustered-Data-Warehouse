.PHONY: build test up down clean logs help

help:
	@echo "Clustered Data Warehouse - Commands"
	@echo "---------------------------------------------"
	@echo "make build      : Compile the project w/out tests"
	@echo "make test       : Run Tests"
	@echo "make up         : Start the Application & DB in docker"
	@echo "make down       : Stop containers and clean volumes"
	@echo "make logs       : View application logs"
	@echo "make clean      : Clean Maven target directory"

build:
	mvn clean package -DskipTests

test:
	mvn clean verify

up:
	docker-compose up --build -d
	@echo "Waiting for the application to start..."
	@echo "App will be available at: http://localhost:8080"

down:
	docker-compose down -v

logs:
	docker-compose logs -f

clean:
	mvn clean