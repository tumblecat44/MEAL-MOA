
docker compose down &&
./gradlew bootJar &&
docker compose up --build
