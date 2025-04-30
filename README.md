# client-service

./mvnw clean package -DskipTests
docker build -t client-service-api .
docker run -p 9093:9093 client-service-api

docker run -p 9093:9093 \
-e DATABASE_URL=jdbc:postgresql://192.168.100.197:5432/client-db \
-e DATABASE_USERNAME=postgres \
-e DATABASE_PASSWORD=Postgres2025! \
-e SPRING_RABBITMQ_HOST=192.168.100.197 \
-e SPRING_RABBITMQ_PORT=5672 \
-e SPRING_RABBITMQ_USERNAME=guest \
-e SPRING_RABBITMQ_PASSWORD=guest \
client-service-api

netstat -ano | findstr :9093
taskkill /PID 2660 /F

npx kill-port 8080

make build
make docker-build
make up
make logs
make clean

---
make docker-build
make up
---

docker exec -it client-service-postgres psql -U postgres -d client-db
client-db=#
\dt
docker exec -it client-service-postgres psql -U postgres -d postgres -c 'DROP DATABASE IF EXISTS "client-db";'
docker exec -it client-service-postgres psql -U postgres -d postgres -c 'CREATE DATABASE "client-db";'

docker exec -it client-service-postgres psql -U postgres -d postgres -c "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'client-db';"

public | flyway_schema_history | ...
SELECT * FROM flyway_schema_history;

docker logs -f client-service-api

choco install make
pacman -S make

===
✅ Passo a passo para obter o Maven Wrapper real
🔧 1. Execute este comando se você tiver o Maven instalado:
bash
Copy
Edit

mvn -N io.takari:maven:wrapper
Esse comando irá gerar:

pgsql
Copy
Edit
📁 .mvn/wrapper
├── maven-wrapper.jar ✅ (real)
└── maven-wrapper.properties
📄 mvnw
📄 mvnw.cmd
===
./mvnw clean package -DskipTests

make docker-push ENABLE_DOCKER_PUSH=true

======
Swagger UI: http://192.168.100.197:9094/api/swagger-ui/index.html
OpenAPI JSON: http://192.168.100.197:9094/api/v3/api-docs

ou 

Swagger UI: http://localhost:9094/api/swagger-ui/index.html
OpenAPI JSON: http://localhost:9094/api/v3/api-docs

# Gerar o arquivo openapi.yaml
curl http://localhost:9094/api/v3/api-docs.yaml -o openapi.yaml

-----
# Gerar o arquivo openapi.json
docker-compose up rabbit postgres config-server
docker-compose up -d rabbit postgres config-server
docker-compose logs -f config-server
