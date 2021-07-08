package ar.com.intrale.cloud.messages;

import javax.validation.constraints.NotBlank;

import ar.com.intrale.cloud.Request;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class SaveProductRequest extends Request {
    
	private String productId;
	
	@NonNull
    @NotBlank
	private String title;
	
    @NonNull
    @NotBlank
	private String description;
    
    @NonNull
	private Long stock;
    
    @NonNull
	private PriceMessage price;
	
	private String base64Image;
    
	@NonNull
    @NotBlank
	private String category;

    public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
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
