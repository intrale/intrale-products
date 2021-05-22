package ar.com.intrale.cloud.messages;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.intrale.cloud.Response;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class ReadProductResponse extends Response {
	
	
	private Collection<Product> products = new ArrayList<Product>();

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	public void add(Product product) {
		products.add(product);
	}
	
	
}
