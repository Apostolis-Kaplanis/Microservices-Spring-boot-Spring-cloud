package com.tolis.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConvertionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	/**
	 * [LEGACY-like method] This method doesn't use the Feign framework for easy http clients.
	 * Next one uses Feign
	 * */
	@GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConvertion calculateCurrencyConvertion(
			@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		//Call the CurrencyEchange microservice from here:
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		/*Note:
		 * RestTemplate is used to make API calls 
		 * and we want a GET request 
		 * getForEntity params:
		 * -url
		 * -responseType. Our CurrencyConvertion has similar fields with CurrencyExchange (check with url)
		 * -uriVariables
		 */
		ResponseEntity<CurrencyConvertion> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConvertion.class,
				uriVariables);
		CurrencyConvertion currencyConversion = responseEntity.getBody();
		
		return new CurrencyConvertion(
				currencyConversion.getId(), from, to, quantity, 
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment() + " " + "rest template"); //Check that the environment (the port which the response we get back from) is Currency Exchange's mocroservice port (8000)
	}
	
	@GetMapping("currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConvertion calculateCurrencyConvertionFeign(
			@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		CurrencyConvertion currencyConversion = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConvertion(
				currencyConversion.getId(), 
				from, 
				to, 
				quantity, 
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment() + " " + "feign"); //Check that the environment (the port which the response we get back from) is Currency Exchange's microservice port (8000)!
	}
}
