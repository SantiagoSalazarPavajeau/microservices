package com.in28minutes.microservices.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

	// print or access information about the request
	private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, // information available in exchange
			GatewayFilterChain chain) {
	
		logger.info("Path of the request received -> {}", 
				exchange.getRequest().getPath()); // get the path of request
		return chain.filter(exchange); // let the execution continue
	} // let chain continue
	// good place for authentication

}
