package ar.com.intrale.messages;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class SaveProductResponse extends Response {
	
	private String productId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
}
