package com.in28minutes.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {
	
	// get info from api
	private Logger logger = 
				LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	//@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
	// if a microservice fails, arguments: configuration (name, fallbackmethod
	// ** ONLY momentarily down
	
	//@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
	// when there are too many requests on an api
	// circuit breaker will skip the route code returns the hardcoded response
	
	//@RateLimiter(name="default") rate of calls 100 per second 
	//10s => 10000 calls to the sample api

	@Bulkhead(name="sample-api")
	// concurrent calls
	public String sampleApi() {
		// this logger shows message as response
		// will be retried 3 times before returning errors
		logger.info("Sample api call received");
//		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", 
//					String.class);
//		return forEntity.getBody();
		return "sample-api";
	}
	
	// this is the method for @Retry annotation
	// this is the response from the api if it fails
	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}
}
