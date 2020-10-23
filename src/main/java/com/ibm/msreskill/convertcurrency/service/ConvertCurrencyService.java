package com.ibm.msreskill.convertcurrency.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibm.msreskill.convertcurrency.model.CurrencyConversionFactor;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
public class ConvertCurrencyService {
	
	
	/*
	 * @Autowired CurrencyConversionFactor ccFactorObj;
	 */
	 
	
	double conversionFactor;
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	@Autowired
	LoadBalancerClient lbClient;
	
	@Autowired
	RestTemplate lbRestTemplate;
	
	@Autowired
	MCCFFeignClient feignClient;
	
	@Bean
	@LoadBalanced
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	public double convertCurrencyFeign(String country, double amount) {			
		
		CurrencyConversionFactor ccFactorObj = feignClient.convertCurrencyfeign(country);
		conversionFactor = ccFactorObj.getConversionFactor();
		return amount*conversionFactor ;	
		
	}
	
	@HystrixCommand(fallbackMethod = "convertCurrencyLBwRestTempFallback")
	public double convertCurrencyLBwRestTemp(String country, double amount) {
		
		String url = "http://mccf/getConversionFactorObj/"+country;
						
		System.out.println("url : "+ url);
		
		
		HttpEntity<CurrencyConversionFactor> requestEntity = new HttpEntity<CurrencyConversionFactor>(
				createCCFactorRequestObj(country));
		HttpEntity<CurrencyConversionFactor> responseEntity = lbRestTemplate.exchange(
				url, HttpMethod.GET, requestEntity, CurrencyConversionFactor.class);
		
		CurrencyConversionFactor ccFactorObj = responseEntity.getBody();
		conversionFactor = ccFactorObj.getConversionFactor();
		return amount*conversionFactor ;	
		
	}
	
	public double convertCurrencyLBwRestTempFallback(String country, double amount) {
		
		conversionFactor = 20.0;		
		return amount*conversionFactor ;	
		
	}
	
	
	public double convertCurrencyLB(String country, double amount) {
		
		ServiceInstance instance = lbClient.choose("mccf");
		
		String host = instance.getHost();
		int port = instance.getPort();
		String url = "http://"+ host + ":" + port + "/getConversionFactorObj/" + country;
		System.out.println("url : "+ url);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<CurrencyConversionFactor> requestEntity = new HttpEntity<CurrencyConversionFactor>(
				createCCFactorRequestObj(country));
		HttpEntity<CurrencyConversionFactor> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, requestEntity, CurrencyConversionFactor.class);
		
		CurrencyConversionFactor ccFactorObj = responseEntity.getBody();
		conversionFactor = ccFactorObj.getConversionFactor();
		return amount*conversionFactor ;
		
		
	}
	
	public double convertCurrencyDiscovery(String country, double amount) {
		
		List<ServiceInstance> instances = discoveryClient.getInstances("mccf");
		ServiceInstance instance = instances.get(0);
		String host = instance.getHost();
		int port = instance.getPort();
		String url = "http://"+ host + ":" + port + "/getConversionFactorObj/" + country;
		System.out.println("url : "+ url);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<CurrencyConversionFactor> requestEntity = new HttpEntity<CurrencyConversionFactor>(
				createCCFactorRequestObj(country));
		HttpEntity<CurrencyConversionFactor> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, requestEntity, CurrencyConversionFactor.class);
		
		CurrencyConversionFactor ccFactorObj = responseEntity.getBody();
		conversionFactor = ccFactorObj.getConversionFactor();
		return amount*conversionFactor ;
		
		
	}
	
	public double convertCurrency(String country, double amount) {
		String url = "http://localhost:9080/getConversionFactorObj/"+country;
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<CurrencyConversionFactor> requestEntity = new HttpEntity<CurrencyConversionFactor>(
				createCCFactorRequestObj(country));
		HttpEntity<CurrencyConversionFactor> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, requestEntity, CurrencyConversionFactor.class);
		CurrencyConversionFactor ccFactorObj = responseEntity.getBody();
		conversionFactor = ccFactorObj.getConversionFactor();
		return amount*conversionFactor ;
		
		
	}
	
	public CurrencyConversionFactor createCCFactorRequestObj(String country) {	
		CurrencyConversionFactor ccFactorObj = new CurrencyConversionFactor();
		ccFactorObj.setCountryCode(country);
		return ccFactorObj;		
	}

	public String addConversionFactorDiscovery(String country, double ccfactor) {
		// TODO Auto-generated method stub
		List<ServiceInstance> instances = discoveryClient.getInstances("mccf");
		ServiceInstance instance = instances.get(0);
		String host = instance.getHost();
		int port = instance.getPort();
		String url = "http://"+ host + ":" + port + "/addConversionFactorDiscovery/" + country + "/"+ ccfactor;
		System.out.println("url : "+ url);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<CurrencyConversionFactor> requestEntity = new HttpEntity<CurrencyConversionFactor>(
				createCCFactorRequestObj(country));
		HttpEntity<CurrencyConversionFactor> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, requestEntity, CurrencyConversionFactor.class);
		CurrencyConversionFactor ccFactorObj = responseEntity.getBody();
		return "success";
	}
	
	public CurrencyConversionFactor getConversionFactorObj(String country) {
		// TODO Auto-generated method stub
		List<ServiceInstance> instances = discoveryClient.getInstances("mccf");
		ServiceInstance instance = instances.get(0);
		String host = instance.getHost();
		int port = instance.getPort();
		String url = "http://"+ host + ":" + port + "/getConversionFactorObj/"+country ;
		System.out.println("url : "+ url);
				
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<CurrencyConversionFactor> requestEntity = new HttpEntity<CurrencyConversionFactor>(
				createCCFactorRequestObj(country));
		HttpEntity<CurrencyConversionFactor> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, requestEntity, CurrencyConversionFactor.class);
		CurrencyConversionFactor ccFactorObj = responseEntity.getBody();
		return ccFactorObj;
	}
	
	public CurrencyConversionFactor getConversionFactorLB(String country) {
		// TODO Auto-generated method stub
		ServiceInstance instance = lbClient.choose("mccf");
		
		String host = instance.getHost();
		int port = instance.getPort();
		String url = "http://"+ host + ":" + port + "/getConversionFactorObj/"+country ;
		System.out.println("url : "+ url);
				
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<CurrencyConversionFactor> requestEntity = new HttpEntity<CurrencyConversionFactor>(
				createCCFactorRequestObj(country));
		HttpEntity<CurrencyConversionFactor> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, requestEntity, CurrencyConversionFactor.class);
		CurrencyConversionFactor ccFactorObj = responseEntity.getBody();
		return ccFactorObj;
	}
	
	public CurrencyConversionFactor[] getConversionFactorList() {
		// TODO Auto-generated method stub
		List<ServiceInstance> instances = discoveryClient.getInstances("mccf");
		ServiceInstance instance = instances.get(0);
		String host = instance.getHost();
		int port = instance.getPort();
		String url = "http://"+ host + ":" + port + "/getConversionFactorList" ;
		System.out.println("url : "+ url);
				
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CurrencyConversionFactor[]> responseEntity =  restTemplate.getForEntity(url, CurrencyConversionFactor[].class);
		
		CurrencyConversionFactor[] CurrencyConversionFactors = responseEntity.getBody();
		return CurrencyConversionFactors;
	}
}
