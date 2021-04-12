package com.in28minutes.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private CurrencyExchangeRepository repository;
	// access db for object
	
	@Autowired 
	private Environment environment;
	// property from currency exchange for different instances
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to) {
		
		logger.info("retrieveExchangeValue called with {} to {}", from, to);
		
		CurrencyExchange currencyExchange 
					= repository.findByFromAndTo(from, to);
		// access db to model with jpa
		
		if(currencyExchange ==null) { // control error if not found
			throw new RuntimeException
				("Unable to Find data for " + from + " to " + to);
		}
		
		String port = environment.getProperty("local.server.port");
		// instead of writing 8000 or other port every time
		// on other instances
		// modified from application.properties
		currencyExchange.setEnvironment(port);
		// from environment property in currency exchange class
		
		return currencyExchange;
		
	}

}
