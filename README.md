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

#Invoking the VAT lookup API via cURL command
```
curl -X POST http://localhost:8081/api/vat/lookup -H 'Content-Type: application/json' -d '{"vatNumber": "LU20260743"}'
```

#Invoking the current time API via cURL command
``` 
curl -X GET http://localhost:8081/api/time/current
```