package ar.com.intrale.cloud.test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import ar.com.intrale.cloud.Function;
import ar.com.intrale.cloud.IntraleFactory;
import ar.com.intrale.cloud.messages.Product;
import ar.com.intrale.cloud.messages.ProductRequest;
import ar.com.intrale.cloud.messages.ProductResponse;
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
    	ProductRequest request = new ProductRequest();
    	request.setProductId(DUMMY_ID);
    	request.setBusinessName(DUMMY_VALUE);
    	request.setDescription(DUMMY_VALUE);
    	request.setDetails(DUMMY_VALUE);
    	request.setPrice(Double.valueOf(1));
    	request.setProductName(DUMMY_VALUE);
    	request.setRequestId(DUMMY_VALUE);
    	request.setStock(Long.valueOf(1));
    	
        APIGatewayProxyResponseEvent responseEvent = lambda.execute(makeRequestEvent(request, Function.CREATE));
        ProductResponse response  = mapper.readValue(responseEvent.getBody(), ProductResponse.class);
        
        assertEquals(200, response.getStatusCode());

        responseEvent = lambda.execute(makeRequestEvent(request, Function.READ));
        response  = mapper.readValue(responseEvent.getBody(), ProductResponse.class);

        assertEquals(DUMMY_ID, response.getProducts().iterator().next().getProductId());
        
        request.setDescription(CHANGED_VALUE);
        
        responseEvent = lambda.execute(makeRequestEvent(request, Function.UPDATE));
        response  = mapper.readValue(responseEvent.getBody(), ProductResponse.class);        
        
        assertEquals(200, response.getStatusCode());

        responseEvent = lambda.execute(makeRequestEvent(request, Function.READ));
        response  = mapper.readValue(responseEvent.getBody(), ProductResponse.class);

        Product productEntity = response.getProducts().iterator().next();
        assertEquals(DUMMY_ID, productEntity.getProductId());
        assertEquals(CHANGED_VALUE, productEntity.getDescription());
    
        responseEvent = lambda.execute(makeRequestEvent(request, Function.DELETE));
        response  = mapper.readValue(responseEvent.getBody(), ProductResponse.class); 
        
        assertEquals(200, response.getStatusCode());

        responseEvent = lambda.execute(makeRequestEvent(request, Function.READ));
        response  = mapper.readValue(responseEvent.getBody(), ProductResponse.class);
        
        assertEquals(0, response.getProducts().size());
    
    }
}
