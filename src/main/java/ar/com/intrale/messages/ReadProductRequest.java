package ar.com.intrale.messages;

import ar.com.intrale.cloud.RequestRoot;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class ReadProductRequest extends RequestRoot {
	
	private String productId;
	
	private String category;
	
	private String name;
	private String description;
	
	private Long fromStock;
	private Long toStock;
	
	private String currencyAcronym;
	
	private Double fromPrice;
	private Double toPrice;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getFromStock() {
		return fromStock;
	}
	public void setFromStock(Long fromStock) {
		this.fromStock = fromStock;
	}
	public Long getToStock() {
		return toStock;
	}
	public void setToStock(Long toStock) {
		this.toStock = toStock;
	}
	public String getCurrencyAcronym() {
		return currencyAcronym;
	}
	public void setCurrencyAcronym(String currencyAcronym) {
		this.currencyAcronym = currencyAcronym;
	}
	public Double getFromPrice() {
		return fromPrice;
	}
	public void setFromPrice(Double fromPrice) {
		this.fromPrice = fromPrice;
	}
	public Double getToPrice() {
		return toPrice;
	}
	public void setToPrice(Double toPrice) {
		this.toPrice = toPrice;
	}
	


}
