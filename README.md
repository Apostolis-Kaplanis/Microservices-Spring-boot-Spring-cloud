## Table of Contents  
- [Introduction](#brief_description)  
- [Analytical Description](#analytical_description)
- [Installation](#installation)
- [Usage](#usage)

<a name="brief_description"/>

# Microservices-Spring-boot-Spring-cloud 
Implementation of multiple restful Microservices cycle with Spring Cloud

Briefly, in this project I created a couple microservices. 
Then try solving some of the problems that occure in this kind of architecture.

---
<a name="analytical_description"/>

## What does it do? (An analytical theoretical description)

### Cloud [Microservices](https://martinfowler.com/articles/microservices.html) Solutions
* [Centralized Configuration Management](https://dzone.com/articles/microservices-architectures-centralized-configurat) ([Spring Cloud Config Server](https://cloud.spring.io/spring-cloud-config/reference/html/#_quick_start))
* Location Transparency - [Naming Server (Eureka)](https://www.javatpoint.com/eureka-naming-server)
* Load Distribution (Spring Cloud Load Balancer)
* Visibility and Monitoring ([Zipkin](https://zipkin.io/))
* [API Gateway](https://www.redhat.com/en/topics/api/what-does-an-api-gateway-do#:~:text=An%20API%20gateway%20is%20an,and%20return%20the%20appropriate%20result.) ([Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/#gateway-starter))
* Fault Tolerance ([Resilience4j](https://github.com/resilience4j/resilience4j))

Every microservice is responsible for a specific thing. It runs in a specific port.

#### Configuration
##### *Problem*
When having multiple microservices, they have their own configuration (application, database, URLs of other microservices, ...). Moreover each of them have configuration for different environments (dev, QA, production). 

##### *Best practices Solution*
Therefore the best practice techinque is to manage them is via a **Centralized Configuration**. With *Spring Cloud Config* server we can put configuration in a git repository and all the changes will be automatically updated to all microservices.

#### Service Discovery & Load Distribution
##### *Problem*
While every microservice is up, in each user's request, they may need to communicate with each other, working together. The number of instances of a microservice should be changing over the time, depending of the load (Think the example of "Back Friday" at an eCommerce system). While hardcoding URLs is not an option, auto-scalling is possible.

##### *Best practices Solution*
The best practice technique is to use the concepts of Service Discovery. The component that provides the service is called **Naming Server**. So all instances of all microservices register themselves with that. Whenever a microservice wants to talk to another microservices, it asks the Naming Server about the available instances. While auto-scalling is achived, *Spring Cloud Load Balancer* is responsible for *Load Distribution* among microservices.

#### API Gateway
##### *Problem*
Where to implement all the features that are *common* across microservices? AuthN, AuthZ, Logging, Service Discovery, Circuit Breaker, Rate Limiting, Load Balancing, Query Transformation, IP Whitelisting
##### *Best practices Solution*
An API gateway. Managing requests, building Routes and filters to modify requests and responses before or after sending the downstream request.

Application | Ports
------------ | -------------
Limits Microservice | 8080, 8081, ...
Spring Cloud Config Server | 8888
Currency Exchange Microservice | 8000, 8001, 8002, ..
Currency Conversion Microservice | 8100, 8101, 8102, ...
Netflix Eureka Naming Server | 8761
API Gateway | 8765
Zipkin Distributed Tracing Server | 9411



<a name="installation"/>

## Installation
1. Using **Docker**
Pull the images from docker repo:
(I dockerized *Naming Server, currency-exchange/conversion* and *api-gateway*)

```
docker pull apostol1s/mmv2-naming-server
docker pull apostol1s/mmv2-currency-exchange-service
docker pull apostol1s/mmv2-currency-conversion-service
docker pull apostol1s/mmv2-api-gateway
```

Other Images you may need to if you dont have 
`docker pull openzipkin/zipkin`

Download `docker-compose.yaml` and from that directory run 

`docker-compose up`

2. From github
Import as *Existing Maven Projects* and run as *Java application* all projects

<a name="usage"/>

## Usage
You can check:

The URIs (through api-gateway):

http://localhost:8765/currency-exchange/from/USD/to/INR

http://localhost:8765/currency-conversion-feign/from/USD/to/INR/quantity/10

http://localhost:8765/currency-conversion-new/from/USD/to/INR/quantity/10

Eureka *Naming Server* for monitoring from 

http://localhost:8761/eureka

Zipkin tracing requests from

http://localhost:9411/zipkin

---

If any problem occure and need help, contact me.
