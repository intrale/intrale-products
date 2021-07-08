package ar.com.intrale.cloud.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;

import ar.com.intrale.cloud.FunctionBuilder;
import ar.com.intrale.cloud.Runner;
import ar.com.intrale.cloud.functions.SaveProductImageFunction;
import ar.com.intrale.cloud.messages.GetProductImageRequest;
import ar.com.intrale.cloud.messages.SaveProductImageRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MicronautTest;

@MicronautTest
public class SaveProductImageFunctionTest extends ar.com.intrale.cloud.Test {

	@Override
	public void beforeEach() {
		applicationContext.registerSingleton(AmazonS3.class, Mockito.mock(AmazonS3.class));
	}

	@Override
	public void afterEach() {
		AmazonS3 provider = applicationContext.getBean(AmazonS3.class);
    	Mockito.reset(provider);

	}
	
    @Test
    public void testRunner() {
    	Runner runner = new Runner();
    	runner.main(null);
    	assertTrue(Boolean.TRUE);
    }
    
    @Test
    public void testProviderException() throws JsonProcessingException  {
    	AmazonS3 provider = applicationContext.getBean(AmazonS3.class);
    	Mockito.when(provider.putObject(any(), any(), any(File.class))).thenThrow(new AmazonServiceException("test"));
    	
    	APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(FunctionBuilder.HEADER_FUNCTION, SaveProductImageFunction.FUNCTION_NAME);
        headers.put(FunctionBuilder.HEADER_BUSINESS_NAME, DUMMY_VALUE);
        requestEvent.setHeaders(headers);
        SaveProductImageRequest request = new SaveProductImageRequest();
        request.setRequestId(DUMMY_VALUE);
        request.setId(DUMMY_VALUE);
        request.setBase64Image(DUMMY_VALUE);
        requestEvent.setBody(mapper.writeValueAsString(request));
        
        APIGatewayProxyResponseEvent responseEvent = (APIGatewayProxyResponseEvent) lambda.execute(requestEvent);

        assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.getCode(), responseEvent.getStatusCode());
    }
    
    

}
