package com.ibm.msreskill.convertcurrency.service;

import org.springframework.stereotype.Component;

import com.ibm.msreskill.convertcurrency.model.CurrencyConversionFactor;

@Component
public class MCCFServiceFallback implements MCCFFeignClient {

	@Override
	public CurrencyConversionFactor convertCurrencyfeign(String country) {
		// TODO Auto-generated method stub
		return new CurrencyConversionFactor("123","ind",30.0);
	}

}
