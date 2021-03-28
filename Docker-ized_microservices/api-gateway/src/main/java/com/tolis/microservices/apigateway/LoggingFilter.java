package com.tolis.microservices.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

// Logging every request that goes through the Api Gateway
// Things like Authentication can go here for example.
@Component
public class LoggingFilter implements GlobalFilter{

	private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub
		//Do the log and then do the execution of the request
		logger.info("Path of the request recieved -> {}", exchange.getRequest().getPath());
		
		return chain.filter(exchange);
	}
	
}
