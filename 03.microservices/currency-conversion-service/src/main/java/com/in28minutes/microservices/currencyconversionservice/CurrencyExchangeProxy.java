package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// do not hard code port to be able to talk to different instances
// load balance
//@FeignClient(name="currency-exchange", url="localhost:8000")
// call the exchange microservice
// name of the service we want to call
// annotation to do load balancing automatically between instances
@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to);
	
}

// eureka returns the instances