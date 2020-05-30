OS=$(shell uname -s)

LOCAL_USERNAME=postgres
LOCAL_PASSWORD=postgres
LOCAL_DB_URL=jdbc:postgresql://localhost:5432/personnel_department

# Run migration for local environment
local-migration:
	 mvn flyway:migrate -Dflyway.user=${LOCAL_USERNAME} -Dflyway.url=${LOCAL_DB_URL} -Dflyway.password=${LOCAL_PASSWORD} -f ../pom.xml

start-local_aws:
	docker-compose -p resources_aws -f ./localstack/docker-compose.yaml up

down-local_aws:
	docker-compose -p resources_aws -f ./localstack/docker-compose.yaml down
