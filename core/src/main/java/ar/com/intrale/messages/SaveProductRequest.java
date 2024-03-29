package ar.com.intrale.messages;

import javax.validation.constraints.NotBlank;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class SaveProductRequest extends RequestRoot {
    
	private String productId;

	@NonNull
    @NotBlank
	private String category;
	
	@NonNull
    @NotBlank
	private String name;
	
    @NonNull
    @NotBlank
	private String description;
    
    @NonNull
	private PriceMessage price;
    
    @NonNull
	private Long stock;
    
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
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public PriceMessage getPrice() {
		return price;
	}
	public void setPrice(PriceMessage price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
