package ar.com.intrale.cloud.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Price {
	
	private String currencyAcronym;
	private Double unitPrice;
	
	public String getCurrencyAcronym() {
		return currencyAcronym;
	}
	public void setCurrencyAcronym(String currencyAcronym) {
		this.currencyAcronym = currencyAcronym;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
