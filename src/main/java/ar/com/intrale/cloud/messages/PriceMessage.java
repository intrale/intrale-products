package ar.com.intrale.cloud.messages;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class PriceMessage {
	
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
