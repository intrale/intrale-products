package ar.com.intrale.cloud.messages;

import ar.com.intrale.cloud.Request;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class DeleteProductRequest extends Request {
	
	private String productId;
	
    public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}