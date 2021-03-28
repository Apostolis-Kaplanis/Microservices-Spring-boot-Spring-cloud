package com.tolis.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url="localhost:8000")
@FeignClient(name = "currency-exchange") //Now, Feign Client, talks to Eureka server, and pick up the Instances of currency-exchange (and do Load Balancing between them) 
public interface CurrencyExchangeProxy {
	
	/*Notes:
	 * -CurrencyConvertion bean is build to match the structure of the response. (wanted field names are the same)
	 * -Therefore, the written values are automatically mapped in the CurrencyConvertion bean
	 * 
	 * */
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public CurrencyConvertion retrieveExchangeValue(
			@PathVariable String from, @PathVariable String to);
}
