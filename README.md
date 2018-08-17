# spring-security-5

This is a 'simple as possible' example of how to use Spring Boot 2 with Spring Security 5 and JWT.

### Running the app with Docker:

This repo contains a Dockerfile and docker-compose.yml for convenience.

- `cd` to the root of this repository
- run `gradlew build`
- run `docker-compose up` 

### Create a user

```
curl -X POST \
      http://localhost:8080/user \
      -H 'Content-Type: application/json' \
      -d '{
        "username" : "tester@test.com",
        "password" : "password"
    }'
```

### Get an authentication token

```
curl -X POST \
  http://localhost:8080/oauth/token \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=password&username=tester%40test.com&password=password&client_id=myClient'
```
