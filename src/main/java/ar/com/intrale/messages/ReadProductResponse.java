package ar.com.intrale.messages;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.intrale.Response;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class ReadProductResponse extends Response {
	
	
	private Collection<ProductMessage> products = new ArrayList<ProductMessage>();

	public Collection<ProductMessage> getProducts() {
		return products;
	}

	public void setProducts(Collection<ProductMessage> products) {
		this.products = products;
	}
	
	public void add(ProductMessage product) {
		products.add(product);
	}
	
	
}
