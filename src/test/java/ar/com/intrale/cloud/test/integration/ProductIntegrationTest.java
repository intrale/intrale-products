package ar.com.intrale.cloud.test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import ar.com.intrale.cloud.Function;
import ar.com.intrale.cloud.IntraleFactory;
import ar.com.intrale.cloud.messages.CreateProductRequest;
import ar.com.intrale.cloud.messages.CreateProductResponse;
import ar.com.intrale.cloud.messages.DeleteProductRequest;
import ar.com.intrale.cloud.messages.DeleteProductResponse;
import ar.com.intrale.cloud.messages.Product;
import ar.com.intrale.cloud.messages.ReadProductRequest;
import ar.com.intrale.cloud.messages.ReadProductResponse;
import ar.com.intrale.cloud.messages.UpdateProductRequest;
import ar.com.intrale.cloud.messages.UpdateProductResponse;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.annotation.MicronautTest;


@MicronautTest(rebuildContext = true)
@Property(name = IntraleFactory.FACTORY, value = "true")
@Property(name = IntraleFactory.PROVIDER, value = "true")
public class ProductIntegrationTest extends ar.com.intrale.cloud.Test {

	@Override
    public void beforeEach() {
    }
	
	@Override
	public void afterEach() {
	}
    
    @Test
    public void test() throws Exception {
    	CreateProductRequest request = new CreateProductRequest();
    	request.setBusinessName(DUMMY_VALUE);
    	request.setDescription(DUMMY_VALUE);
    	request.setDetails(DUMMY_VALUE);
    	request.setPrice(Double.valueOf(1));
    	request.setProductName(DUMMY_VALUE);
    	request.setRequestId(DUMMY_VALUE);
    	request.setStock(Long.valueOf(1));
    	
        APIGatewayProxyResponseEvent responseEvent = lambda.execute(makeRequestEvent(request, Function.CREATE));
        CreateProductResponse createProductResponse  = mapper.readValue(responseEvent.getBody(), CreateProductResponse.class);
        
        assertEquals(200, createProductResponse.getStatusCode());

        ReadProductRequest readProductRequest = new ReadProductRequest();
        readProductRequest.setRequestId(DUMMY_VALUE);
        readProductRequest.setBusinessName(DUMMY_VALUE);
        readProductRequest.setProductId(createProductResponse.getProductId());
        responseEvent = lambda.execute(makeRequestEvent(readProductRequest, Function.READ));
        ReadProductResponse readProductResponse  = mapper.readValue(responseEvent.getBody(), ReadProductResponse.class);

        assertEquals(createProductResponse.getProductId(), readProductResponse.getProducts().iterator().next().getProductId());
        
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setRequestId(DUMMY_VALUE);
        updateProductRequest.setBusinessName(DUMMY_VALUE);
        updateProductRequest.setProductId(createProductResponse.getProductId());
        updateProductRequest.setDescription(CHANGED_VALUE);
                
        responseEvent = lambda.execute(makeRequestEvent(updateProductRequest, Function.UPDATE));
        UpdateProductResponse updateProductResponse  = mapper.readValue(responseEvent.getBody(), UpdateProductResponse.class);        
        
        assertEquals(200, updateProductResponse.getStatusCode());
        
        responseEvent = lambda.execute(makeRequestEvent(readProductRequest, Function.READ));
        readProductResponse  = mapper.readValue(responseEvent.getBody(), ReadProductResponse.class);

        Product productEntity = readProductResponse.getProducts().iterator().next();
        assertEquals(createProductResponse.getProductId(), productEntity.getProductId());
        assertEquals(CHANGED_VALUE, productEntity.getDescription());
    
        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setRequestId(DUMMY_VALUE);
        deleteProductRequest.setBusinessName(DUMMY_VALUE);
        deleteProductRequest.setProductId(createProductResponse.getProductId());
        responseEvent = lambda.execute(makeRequestEvent(deleteProductRequest, Function.DELETE));
        DeleteProductResponse deleteProductResponse  = mapper.readValue(responseEvent.getBody(), DeleteProductResponse.class); 
        
        assertEquals(200, createProductResponse.getStatusCode());

        responseEvent = lambda.execute(makeRequestEvent(readProductRequest, Function.READ));
        readProductResponse  = mapper.readValue(responseEvent.getBody(), ReadProductResponse.class);
        
        assertEquals(0, readProductResponse.getProducts().size());
    
    }
}
