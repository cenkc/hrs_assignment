# HRS Assignment : Invisible Pay Challenge: Backend Engineer

# Running the application thru IntelliJ IDEA with Maven
run `"mvn spring-boot:run"` command inside of a IntelliJ's built-in terminal

# Running the application thru IntelliJ IDEA with Maven Wrapper
run `"mvnw spring-boot:run"` command inside of a IntelliJ's built-in terminal

# API Documentation (via Swagger)
```
http://localhost:8081/swagger-ui.html
http://localhost:8081/v2/api-docs
```

# App Monitoring
`http://localhost:8081/actuator`

# Invoking the convert API via cURL command
```
curl -X POST \
  http://localhost:8081/api/currency/convert \
  -H 'Content-Type: application/json' \
  -d '{
    "sourceCurrency": "USD",
    "targetCurrency": "EUR",
    "amount": 5
}'
```

# Invoking the VAT lookup API via cURL command
```
curl -X POST http://localhost:8081/api/vat/lookup -H 'Content-Type: application/json' -d '{"vatNumber": "LU20260743"}'

-- Some test numbers :
-- SE556703748501 --> "CountryCode": "SE", "BusinessName": "Spotify AB"
-- GB100534180 --> "CountryCode": "GB", "BusinessName": "ING FINANCE SOLUTION LTD"
-- NL805734958B01 --> "CountryCode": "NL", "BusinessName": "BOOKING.COM B.V."

```

# Invoking the current time API via cURL command
``` 
curl -X GET http://localhost:8081/api/time/current
```

# Dockerfile inside the project
```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/be-assignment-0.0.1-SNAPSHOT.jar hrs-app.jar
ENTRYPOINT ["java","-jar","hrs-app.jar"]
```
# check if docker installed
```
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ docker --version
Docker version 19.03.6, build 369ce74a3c
```

# build container
```
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ docker build -t hrs-docker .
Sending build context to Docker daemon  50.97MB
Step 1/4 : FROM openjdk:8-jdk-alpine
8-jdk-alpine: Pulling from library/openjdk
e7c96db7181b: Pull complete 
f910a506b6cb: Pull complete 
c2274a1a0e27: Pull complete 
Digest: sha256:94792824df2df33402f201713f932b58cb9de94a0cd524164a0f2283343547b3
Status: Downloaded newer image for openjdk:8-jdk-alpine
 ---> a3562aa0b991
Step 2/4 : VOLUME /tmp
 ---> Running in 185a261c3e7c
Removing intermediate container 185a261c3e7c
 ---> c522b7c43159
Step 3/4 : ADD target/be-assignment-0.0.1-SNAPSHOT.jar hrs-app.jar
 ---> 4b9f25f4842b
Step 4/4 : ENTRYPOINT ["java","-jar","hrs-app.jar"]
 ---> Running in b5d0b6e42f2b
Removing intermediate container b5d0b6e42f2b
 ---> 63230e413b88
Successfully built 63230e413b88
Successfully tagged hrs-docker:latest
```
# list images
```
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
hrs-docker         latest              63230e413b88        9 seconds ago       155MB
openjdk             8-jdk-alpine        a3562aa0b991        10 months ago       105MB
```

# run the container
```
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ docker run -p8081:8081 hrs-docker
```
#if you get this error, make sure you killed all the instances of the application and run above command again
```
docker: Error response from daemon: driver failed programming external connectivity on endpoint serene_wright (cc8359b52e8d72ecf706bed7b7a1846718e2074ca555cfd7a38aa80011a7f419): Error starting userland proxy: listen tcp 0.0.0.0:8081: bind: address already in use.
ERRO[0000] error waiting for container: context canceled 
```

# run the container again
```
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ docker run -p8081:8081 hrs-docker
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.0.RELEASE)

2020-03-10 10:35:29.322  INFO 1 --- [           main] c.c.h.b.BeAssignmentApplication          : Starting BeAssignmentApplication v0.0.1-SNAPSHOT on 71a2050c0399 with PID 1 (/hrs-app.jar started by root in /)
.....
2020-03-10 10:35:38.620  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http) with context path ''
2020-03-10 10:35:38.623  INFO 1 --- [           main] c.c.h.b.BeAssignmentApplication          : Started BeAssignmentApplication in 10.14 seconds (JVM running for 10.967)
```

# invoke /api/currency/convert endpoint
```
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ curl -X POST \
>   http://localhost:8081/api/currency/convert \
>   -H 'Content-Type: application/json' \
>   -d '{
>     "sourceCurrency": "USD",
>     "targetCurrency": "EUR",
>     "amount": 5
> }'
{"amount":4.36170,"sourceCurrency":"USD","targetCurrency":"EUR"}
```

# invoke /api/vat/lookup endpoint
```
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ curl -X POST http://localhost:8081/api/vat/lookup -H 'Content-Type: application/json' -d '{"vatNumber": "LU20260743"}'
{"countryCode":"LU"}
```

# invoke /api/time/current endpoint
```
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ curl localhost:8081/api/time/current
{"currentTime":"2020-03-10 10:42:08.742 UTC Tue"}
```

# docker-compose.yml inside the project
```
version: '2'

services:
  instance_1:
    image: hrs-docker:latest
    ports:
      - "8081:8081"

  instance_2:
    image: hrs-docker:latest
    ports:
        - "8082:8081"

  instance_3:
    image: hrs-docker:latest
    ports:
        - "8083:8081"
```

# run
``` 
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ docker-compose up

Creating network "hrs_assignment-master_default" with the default driver
Creating hrs_assignment-master_instance_1_1 ... done
Creating hrs_assignment-master_instance_3_1 ... done
Creating hrs_assignment-master_instance_2_1 ... done
Attaching to hrs_assignment-master_instance_2_1, hrs_assignment-master_instance_1_1, hrs_assignment-master_instance_3_1
...
```

# invoke /api/time/current endpoint over 3 different ports
```
cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ curl localhost:8081/api/time/current
{"currentTime":"2020-03-10 22:01:55.489 UTC Tue"}

cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ curl localhost:8082/api/time/current
{"currentTime":"2020-03-10 22:02:01.663 UTC Tue"}

cenkc@cenk-linux:~/devenv/workspaces/job/hrs/hrs_assignment-master$ curl localhost:8083/api/time/current
{"currentTime":"2020-03-10 22:02:05.062 UTC Tue"}
```
