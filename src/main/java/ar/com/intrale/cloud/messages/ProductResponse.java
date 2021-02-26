package ar.com.intrale.cloud.messages;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.intrale.cloud.Response;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class ProductResponse extends Response {
	
	
	private Collection<ProductEntity> products = new ArrayList<ProductEntity>();

	public Collection<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(Collection<ProductEntity> products) {
		this.products = products;
	}
	
	public void add(ProductEntity product) {
		products.add(product);
	}
	
	
}
