package ar.com.intrale.cloud.test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import ar.com.intrale.cloud.FunctionConst;
import ar.com.intrale.cloud.IntraleFactory;
import ar.com.intrale.cloud.messages.DeleteProductRequest;
import ar.com.intrale.cloud.messages.PriceMessage;
import ar.com.intrale.cloud.messages.ReadProductRequest;
import ar.com.intrale.cloud.messages.ReadProductResponse;
import ar.com.intrale.cloud.messages.SaveProductRequest;
import ar.com.intrale.cloud.messages.SaveProductResponse;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.annotation.MicronautTest;


@MicronautTest(rebuildContext = true )
@Property(name = IntraleFactory.FACTORY, value = "true")
@Property(name = IntraleFactory.PROVIDER, value = "true")
public class ProductIntegrationTest extends ar.com.intrale.cloud.Test {

	public static String IMAGE = "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";
	
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
