package ar.com.intrale.messages;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class DeleteProductRequest extends RequestRoot {
	
	private String productId;
	
    public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
