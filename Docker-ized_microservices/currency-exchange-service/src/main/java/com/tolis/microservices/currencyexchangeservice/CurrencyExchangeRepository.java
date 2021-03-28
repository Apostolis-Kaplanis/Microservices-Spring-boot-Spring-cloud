package com.tolis.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long>{
	//Note: The JPA will convert findByFromAndTo to a sql query.
	CurrencyExchange findByFromAndTo(String from, String to);
}
