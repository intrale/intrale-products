package ar.com.intrale.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import ar.com.intrale.FunctionConst;
import ar.com.intrale.IntraleFactory;
import ar.com.intrale.messages.DeleteProductRequest;
import ar.com.intrale.messages.PriceMessage;
import ar.com.intrale.messages.ReadProductRequest;
import ar.com.intrale.messages.ReadProductResponse;
import ar.com.intrale.messages.SaveProductRequest;
import ar.com.intrale.messages.SaveProductResponse;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.annotation.MicronautTest;


@MicronautTest(rebuildContext = true )
@Property(name = IntraleFactory.FACTORY, value = "true")
@Property(name = IntraleFactory.PROVIDER, value = "true")
public class IntegrationTest extends ar.com.intrale.Test {

	@Override
    public void beforeEach() {
    }
	
	@Override
	public void afterEach() {
	}
    
    @Test
    public void test() throws Exception {
    	SaveProductRequest request = new SaveProductRequest();
    	request.setName(DUMMY_VALUE);
    	request.setDescription(DUMMY_VALUE);
    	request.setRequestId(DUMMY_VALUE);
    	request.setCategory(DUMMY_VALUE);
    	request.setStock(Long.valueOf(1));
    	//request.setBase64Image(IMAGE);
    	PriceMessage price = new PriceMessage();
    	price.setCurrencyAcronym(DUMMY_VALUE);
    	price.setUnitPrice(Double.valueOf(1));
    	request.setPrice(price);
    	
        APIGatewayProxyResponseEvent responseEvent = (APIGatewayProxyResponseEvent) lambda.execute(makeRequestEvent(request, FunctionConst.SAVE));
        SaveProductResponse createProductResponse  = readEncodedValue(responseEvent.getBody(), SaveProductResponse.class);
        
        assertEquals(200, createProductResponse.getStatusCode());
        
        ReadProductRequest readProductRequest = new ReadProductRequest();
        readProductRequest.setRequestId(DUMMY_VALUE);
        readProductRequest.setProductId(createProductResponse.getProductId()); 
        readProductRequest.setName(DUMMY_VALUE);
        readProductRequest.setDescription(DUMMY_VALUE);
        readProductRequest.setFromStock(Long.valueOf(1)); 
        readProductRequest.setToStock(Long.valueOf(1)); 
        readProductRequest.setFromPrice(Double.valueOf(1)); 
        readProductRequest.setToPrice(Double.valueOf(1)); 
        readProductRequest.setCurrencyAcronym(DUMMY_VALUE);
        responseEvent = (APIGatewayProxyResponseEvent) lambda.execute(makeRequestEvent(readProductRequest, FunctionConst.READ));
        ReadProductResponse readProductResponse  = readEncodedValue(responseEvent.getBody(), ReadProductResponse.class);
        
        assertEquals(200, readProductResponse.getStatusCode());
        assertTrue(readProductResponse.getProducts().size()==1);
        
        String dummyOtherValue = DUMMY_VALUE + "_OTHER";
        readProductRequest.setRequestId(DUMMY_VALUE);
        readProductRequest.setProductId(dummyOtherValue); 
        readProductRequest.setName(dummyOtherValue);
        readProductRequest.setDescription(dummyOtherValue);
        readProductRequest.setFromStock(Long.valueOf(2)); 
        readProductRequest.setToStock(Long.valueOf(0)); 
        readProductRequest.setFromPrice(Double.valueOf(2)); 
        readProductRequest.setToPrice(Double.valueOf(0)); 
        readProductRequest.setCurrencyAcronym(dummyOtherValue);
        responseEvent = (APIGatewayProxyResponseEvent) lambda.execute(makeRequestEvent(readProductRequest, FunctionConst.READ));
        readProductResponse  = readEncodedValue(responseEvent.getBody(), ReadProductResponse.class);
        
        assertEquals(200, readProductResponse.getStatusCode());
        assertTrue(readProductResponse.getProducts().size()==0);
        
        readProductRequest = new ReadProductRequest();
        readProductRequest.setRequestId(DUMMY_VALUE);
        responseEvent = (APIGatewayProxyResponseEvent) lambda.execute(makeRequestEvent(readProductRequest, FunctionConst.READ));
        readProductResponse  = readEncodedValue(responseEvent.getBody(), ReadProductResponse.class);
        
        assertEquals(200, readProductResponse.getStatusCode());
        assertTrue(readProductResponse.getProducts().size()>0);        
        
    	SaveProductRequest updateRequest = new SaveProductRequest();
    	updateRequest.setProductId(createProductResponse.getProductId());
    	updateRequest.setName(DUMMY_VALUE);
    	updateRequest.setDescription(DUMMY_VALUE);
    	updateRequest.setRequestId(DUMMY_VALUE);
    	updateRequest.setCategory(DUMMY_VALUE);
    	updateRequest.setStock(Long.valueOf(1));
    	price = new PriceMessage();
    	price.setCurrencyAcronym(DUMMY_VALUE);
    	price.setUnitPrice(Double.valueOf(1));
    	updateRequest.setPrice(price);
    	
        responseEvent = (APIGatewayProxyResponseEvent) lambda.execute(makeRequestEvent(updateRequest, FunctionConst.SAVE));
        SaveProductResponse updateProductResponse  = readEncodedValue(responseEvent.getBody(), SaveProductResponse.class);
        
        assertEquals(200, updateProductResponse.getStatusCode());

        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setRequestId(DUMMY_VALUE);
        deleteProductRequest.setProductId(createProductResponse.getProductId());
        
        responseEvent = (APIGatewayProxyResponseEvent) lambda.execute(makeRequestEvent(deleteProductRequest, FunctionConst.DELETE));
        assertEquals(200, responseEvent.getStatusCode());
        
        System.out.println();
        
    }
}
