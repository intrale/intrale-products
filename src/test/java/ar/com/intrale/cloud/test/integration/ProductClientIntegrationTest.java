package ar.com.intrale.cloud.test.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import ar.com.intrale.cloud.services.ProductClient;
import io.micronaut.test.annotation.MicronautTest;

@MicronautTest(rebuildContext = true )
public class ProductClientIntegrationTest /*extends ar.com.intrale.cloud.Test*/ {
	
	@Inject
	private ProductClient productClient;

    
    @Test
    public void test() throws Exception {
        
    	System.out.println("Iniciando prueba");
    	String productId = productClient.save(ar.com.intrale.cloud.Test.DUMMY_VALUE, ar.com.intrale.cloud.Test.DUMMY_VALUE);
    	System.out.println("Prueba ejecutada");
    	assertNotNull(productId);
    	
    }
}
