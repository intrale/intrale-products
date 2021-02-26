package ar.com.intrale.cloud.messages;

import javax.validation.constraints.NotBlank;

import edu.umd.cs.findbugs.annotations.NonNull;

public class ProductEntity {
	private Long productId;
	private String productName;
	private String description;
	private String details;
	private Long stock;
	private Double price;
	
	@NonNull
    @NotBlank
    public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@NonNull
    @NotBlank
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@NonNull
    @NotBlank
	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	@NonNull
    @NotBlank
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
