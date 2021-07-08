package ar.com.intrale.cloud.messages;

import ar.com.intrale.cloud.Request;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class ReadProductRequest extends Request {
	
	private String productId;
	private String title;
	private String description;
	
	private Long fromStock;
	private Long toStock;
	
	private String currencyAcronym;
	
	private Double fromPrice;
	private Double toPrice;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
