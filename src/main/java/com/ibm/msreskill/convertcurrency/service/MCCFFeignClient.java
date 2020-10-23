package com.ibm.msreskill.convertcurrency.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibm.msreskill.convertcurrency.model.CurrencyConversionFactor;

import feign.Param;

@FeignClient(name = "mccf", fallback = MCCFServiceFallback.class)
public interface MCCFFeignClient {
	
	@RequestMapping(path = "/getConversionFactorObj/{country}" , method = RequestMethod.GET)	
	public CurrencyConversionFactor convertCurrencyfeign(@RequestParam(value="country") String country);

}
