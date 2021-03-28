package com.tolis.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	//Configure Routes (add custom filters on specific paths)
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		
		return builder.routes()
				//Sample
				.route(p -> p
						//Note: Here i can match Routes based on Path (like this), based on Host, on Request Method, on Query Parameter, ...
						.path("/get") //If the path is /get
						.filters(f -> f
								.addRequestHeader("MyHeader", "MyURI")
								.addRequestParameter("Param", "MyValue"))
						.uri("http://httpbin.org:80")) //Redirect it at this uri
				
				//Path Rewriting:
				//fixing dual repetition in the routes (eg. http://localhost:8765/currency-exchange/currency-exchange/from/USD/to/INR)
				//I ill check for "currency-exchange" and then redirect to using the Naming Server, also to do Load Balancing
				//I want to have a url of this kind: http://localhost:8765/currency-exchange/from/USD/to/INR
				.route(p -> p.path("/currency-exchange/**") // ** = anything followed
						.uri("lb://currency-exchange")) // lb = Load Balancing, and the name of microservice on the Eureka server.
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-new/**") // Lets say i want to Redirect it to the feign url (with a filter)
						.filters(f -> f.rewritePath(
								"/currency-conversion-new/(?<segment>.*)", 
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))				
				.build();
	}
}
