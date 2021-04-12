package com.in28minutes.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	// beans and routes
	// return a router of the gateway
	// customize routes
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		// lambda p -> p.path
		// switches based on route functional programming
		// can match or switch in headers 
		// filters do something once request has been matched
		return builder.routes()
				.route(p -> p
						.path("/get") // reroute the urls
						.filters(f -> f
								.addRequestHeader("MyHeader", "MyURI") // modify request and response with lambda
								.addRequestParameter("Param", "MyValue"))
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**") // regex text should redirect to 
						.uri("lb://currency-exchange")) // reroute the urls
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath(
								"/currency-conversion-new/(?<segment>.*)", 
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				.build();
	}

}
