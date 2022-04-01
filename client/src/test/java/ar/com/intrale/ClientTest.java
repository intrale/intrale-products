package ar.com.intrale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.intrale.exceptions.ClientResponseException;
import ar.com.intrale.messages.DeleteProductRequest;
import ar.com.intrale.messages.PriceMessage;
import ar.com.intrale.messages.ProductMessage;
import ar.com.intrale.messages.ReadProductRequest;
import ar.com.intrale.messages.ReadProductResponse;
import ar.com.intrale.messages.SaveProductRequest;
import ar.com.intrale.messages.SaveProductResponse;
import ar.com.intrale.client.ProductClient;
import io.micronaut.test.annotation.MicronautTest;

@MicronautTest(rebuildContext = true )
public class ClientTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientTest.class);
	
	@Inject
	private ProductClient productClient;

    
    @Test
    public void test() throws Exception {
        
    	SaveProductRequest saveProductRequest = new SaveProductRequest();
    	saveProductRequest.setRequestId(ar.com.intrale.Test.DUMMY_VALUE);
    	saveProductRequest.setName(ar.com.intrale.Test.DUMMY_VALUE);
    	saveProductRequest.setDescription(ar.com.intrale.Test.DUMMY_VALUE);
    	saveProductRequest.setCategory(ar.com.intrale.Test.DUMMY_VALUE);
    	saveProductRequest.setStock(Long.valueOf("1"));
    	
    	PriceMessage priceMessage = new PriceMessage();
    	priceMessage.setCurrencyAcronym("$");
    	priceMessage.setUnitPrice(Double.valueOf("10"));
    	saveProductRequest.setPrice(priceMessage);
    	SaveProductResponse saveProductResponse = productClient.save(ar.com.intrale.Test.DUMMY_VALUE, saveProductRequest);
    	
    	assertNotNull(saveProductResponse);

    	ReadProductRequest readProductRequest = new ReadProductRequest();
    	readProductRequest.setRequestId(ar.com.intrale.Test.DUMMY_VALUE);
    	readProductRequest.setProductId(saveProductResponse.getProductId());
    	ReadProductResponse readProductResponse= productClient.read(ar.com.intrale.Test.DUMMY_VALUE, readProductRequest);
		assertNotNull(readProductResponse);
		LOGGER.info("readProductResponse.getProducts().size():" + readProductResponse.getProducts().size());
    	assertTrue(readProductResponse.getProducts().size() == 1);
    	
    	ProductMessage productMessage = readProductResponse.getProducts().iterator().next();
    	
    	assertNotNull(productMessage.getCategory());
    	assertNotNull(productMessage.getDescription());
    	assertNotNull(productMessage.getId());
    	assertNotNull(productMessage.getName());
    	assertNotNull(productMessage.getPrice());
    	assertNotNull(productMessage.getStock());
    	
    	readProductResponse.add(productMessage);
    	
		DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
		deleteProductRequest.setRequestId(ar.com.intrale.Test.DUMMY_VALUE);
		deleteProductRequest.setProductId(saveProductResponse.getProductId());
    	
		productClient.delete(ar.com.intrale.Test.DUMMY_VALUE, deleteProductRequest);
		
		readProductResponse = productClient.read(ar.com.intrale.Test.DUMMY_VALUE, readProductRequest);
		assertNotNull(readProductResponse);
		LOGGER.info("readProductResponse.getProducts().size():" + readProductResponse.getProducts().size());
		assertTrue(readProductResponse.getProducts().size() == 0);
		
		readProductResponse = productClient.read(ar.com.intrale.Test.DUMMY_VALUE, readProductRequest);
		readProductRequest.setCategory(ar.com.intrale.Test.DUMMY_VALUE);
		readProductRequest.setCurrencyAcronym(ar.com.intrale.Test.DUMMY_VALUE);
		readProductRequest.setDescription(ar.com.intrale.Test.DUMMY_VALUE);
		readProductRequest.setName(ar.com.intrale.Test.DUMMY_VALUE);
		readProductRequest.setFromPrice(Double.valueOf("1"));
		readProductRequest.setFromStock(Long.valueOf("1"));
		readProductRequest.setToPrice(Double.valueOf("1"));
		readProductRequest.setToStock(Long.valueOf("1"));
		
		assertNotNull(readProductResponse);
		LOGGER.info("readProductResponse.getProducts().size():" + readProductResponse.getProducts().size());
		assertTrue(readProductResponse.getProducts().size() == 0);
		
		try {
			productClient.save(ar.com.intrale.Test.DUMMY_VALUE, new SaveProductRequest());
			assertTrue(Boolean.FALSE);
		} catch(ClientResponseException e) {
			assertTrue(Boolean.TRUE);
		}
		
		try {
			productClient.read(ar.com.intrale.Test.DUMMY_VALUE, new ReadProductRequest());
			assertTrue(Boolean.FALSE);
		} catch(ClientResponseException e) {
			assertTrue(Boolean.TRUE);
		}
		
		try {
			productClient.delete(ar.com.intrale.Test.DUMMY_VALUE, new DeleteProductRequest());
			assertTrue(Boolean.FALSE);
		} catch(ClientResponseException e) {
			assertTrue(Boolean.TRUE);
		}

		try {
			SaveProductRequest request = new SaveProductRequest();
			request.setRequestId("!#$%&/()S=*¬");
			productClient.save(ar.com.intrale.Test.DUMMY_VALUE, request);
			assertTrue(Boolean.FALSE);
		} catch(ClientResponseException e) {
			assertTrue(Boolean.TRUE);
		}
    
    }
}
