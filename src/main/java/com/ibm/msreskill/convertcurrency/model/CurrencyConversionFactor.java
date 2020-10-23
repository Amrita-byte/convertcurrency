package com.ibm.msreskill.convertcurrency.model;



public class CurrencyConversionFactor {
	
	
	String id;	
	String countryCode;
	double conversionFactor;
	
	public CurrencyConversionFactor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CurrencyConversionFactor(String id,String countrycode,double conversionFactor) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id ;
		this.countryCode = countrycode ;
		this.conversionFactor = conversionFactor ;		
	}	
	  public String getCountryCode() { 
		  return countryCode; 
		  }
	  
	  public void setCountryCode(String countryCode) { 
		  this.countryCode = countryCode;
		  }

	public double getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
