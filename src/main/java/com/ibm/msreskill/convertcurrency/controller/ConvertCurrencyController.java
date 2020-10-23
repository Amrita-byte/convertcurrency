package com.ibm.msreskill.convertcurrency.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.msreskill.convertcurrency.model.CurrencyConversionFactor;
import com.ibm.msreskill.convertcurrency.service.ConvertCurrencyService;

@RestController
public class ConvertCurrencyController {
	
	@Autowired
	ConvertCurrencyService convertCurrencyService ;
	
public ConvertCurrencyController() {
	// TODO Auto-generated constructor stub
}

@GetMapping("/convertCurrencyFeign/{country}/{amount}")
public double convertCurrencyFeign(@PathVariable String country,@PathVariable double amount) {
	return convertCurrencyService.convertCurrencyFeign(country, amount);
}

@GetMapping("/convertCurrencyLBwRestTemp/{country}/{amount}")
public double convertCurrencyLBwRestTemp(@PathVariable String country,@PathVariable double amount) {
	return convertCurrencyService.convertCurrencyLBwRestTemp(country, amount);
}

@GetMapping("/convertCurrencyLB/{country}/{amount}")
public double convertCurrencyLB(@PathVariable String country,@PathVariable double amount) {
	return convertCurrencyService.convertCurrencyLB(country, amount);
}

@GetMapping("/convertCurrencyDiscovery/{country}/{amount}")
public double convertCurrencyDiscovery(@PathVariable String country,@PathVariable double amount) {
	return convertCurrencyService.convertCurrencyDiscovery(country, amount);
}



@GetMapping("/convertCurrency/{country}/{amount}")
public double convertCurrency(@PathVariable String country,@PathVariable double amount) {
	return convertCurrencyService.convertCurrency(country, amount);
}

@GetMapping("/convertCurrency/check")
public String convertCurrencyCheck() {
	return "I am working";
}

@GetMapping("/addConversionFactorDiscovery/{country}/{ccfactor}")
public String addConversionFactorDiscovery(@PathVariable String country,@PathVariable double ccfactor) {
	return convertCurrencyService.addConversionFactorDiscovery(country, ccfactor);
}

@GetMapping("/getConversionFactor/{country}")
public CurrencyConversionFactor getConversionFactorObj(@PathVariable String country) {
	return convertCurrencyService.getConversionFactorObj(country);
}

@GetMapping("/getConversionFactorLB/{country}")
public CurrencyConversionFactor getConversionFactorLB(@PathVariable String country) {
	return convertCurrencyService.getConversionFactorLB(country);
}

@GetMapping("/getConversionFactorList")
public CurrencyConversionFactor[] getConversionFactorList() {
	return convertCurrencyService.getConversionFactorList();
}


}
