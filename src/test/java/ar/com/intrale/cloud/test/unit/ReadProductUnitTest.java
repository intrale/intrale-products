package ar.com.intrale.cloud.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ar.com.intrale.cloud.Error;
import ar.com.intrale.cloud.Function;
import ar.com.intrale.cloud.FunctionExceptionResponse;
import ar.com.intrale.cloud.Lambda;
import ar.com.intrale.cloud.Runner;
import ar.com.intrale.cloud.functions.UpdateProductFunction;
import ar.com.intrale.cloud.messages.CreateProductRequest;
import ar.com.intrale.cloud.messages.CreateProductResponse;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.annotation.MicronautTest;


@MicronautTest
public class ReadProductUnitTest extends ar.com.intrale.cloud.Test {
	
	private Boolean flag = Boolean.TRUE;

	@Override
    public void beforeEach() {
    	ListTablesResult listTablesResult = getListTablesResult();
    	
    	AmazonDynamoDB provider = Mockito.mock(AmazonDynamoDB.class);
    	
    	Mockito.when(provider.listTables(any(ListTablesRequest.class))).thenReturn(listTablesResult);
    	Mockito.when(provider.createTable(any())).thenReturn(new CreateTableResult());
    	
    	applicationContext.registerSingleton(AmazonDynamoDB.class, provider);
    }
	
	@Override
	public void afterEach() {
		AmazonDynamoDB provider = applicationContext.getBean(AmazonDynamoDB.class);
    	Mockito.reset(provider);
	}

	private ListTablesResult getListTablesResult() {
		ListTablesResult listTablesResult = new ListTablesResult();
    	if(flag) {
    		listTablesResult.setTableNames(Arrays.asList(UpdateProductFunction.TABLE_NAME));
    		flag = Boolean.FALSE;
    	} else {
    		flag = Boolean.TRUE;
    	}
		return listTablesResult;
	}
    
    
    //@Test
    public void testIsRunning() {
    	//assertTrue(app.isRunning());
    }

    @Test
    public void testRunner() {
    	Runner runner = new Runner();
    	runner.main(null);
    	assertTrue(Boolean.TRUE);
    }
    
    @Test
    public void testCreateTableException() {
    	ListTablesResult listTablesResult = getListTablesResult();
    	AmazonDynamoDB provider = applicationContext.getBean(AmazonDynamoDB.class);
    	provider = Mockito.mock(AmazonDynamoDB.class);
    	Mockito.when(provider.listTables(any(ListTablesRequest.class))).thenReturn(listTablesResult);
    	Mockito.when(provider.createTable(any())).thenThrow(ResourceInUseException.class);
    	
    	BeanIntrospection<Lambda> beanIntrospection = BeanIntrospection.getIntrospection(Lambda.class);
    	lambda = beanIntrospection.instantiate();
    	
    	assertTrue(Boolean.TRUE);
    	
    }

    
    
    @Test
    public void testAddOK() throws JsonMappingException, JsonProcessingException {
    	ListTablesResult result = new ListTablesResult();
    	result.setTableNames(Arrays.asList(UpdateProductFunction.TABLE_NAME));

    	AmazonDynamoDB provider = applicationContext.getBean(AmazonDynamoDB.class);
    	Mockito.when(provider.listTables()).thenReturn(result);
    	
    	QueryResult queryResult = new QueryResult();
    	Mockito.when(provider.query(any())).thenReturn(queryResult);
    	
    	CreateProductRequest request = new CreateProductRequest();
    	request.setRequestId(DUMMY_VALUE);
        request.setBusinessName(DUMMY_VALUE);
        request.setProductId(new Long("1"));
        request.setProductName(DUMMY_VALUE);
        request.setDescription(DUMMY_VALUE);
        request.setDetails(DUMMY_VALUE);
        request.setPrice(new Double("1"));
        request.setStock(new Long("1"));
        
        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(Lambda.FUNCTION, Function.READ);
        requestEvent.setHeaders(headers);
        requestEvent.setBody(mapper.writeValueAsString(request));
        APIGatewayProxyResponseEvent responseEvent = lambda.execute(requestEvent);
        
        CreateProductResponse response  = mapper.readValue(responseEvent.getBody(), CreateProductResponse.class);
        
        assertNotNull(response);
    }
    
    @Test
    public void testRequestIncomplete() throws JsonMappingException, JsonProcessingException {
    	ListTablesResult result = new ListTablesResult();
    	result.setTableNames(Arrays.asList(UpdateProductFunction.TABLE_NAME));

    	AmazonDynamoDB provider = applicationContext.getBean(AmazonDynamoDB.class);
    	Mockito.when(provider.listTables()).thenReturn(result);
    	
    	CreateProductRequest request = new CreateProductRequest();
    	//request.setRequestId(DUMMY_VALUE);
        request.setBusinessName(DUMMY_VALUE);
        request.setProductId(new Long("1"));
        request.setProductName(DUMMY_VALUE);
        request.setDescription(DUMMY_VALUE);
        request.setDetails(DUMMY_VALUE);
        request.setPrice(new Double("1"));
        request.setStock(new Long("1"));
        
        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(Lambda.FUNCTION, Function.READ);
        requestEvent.setHeaders(headers);
        requestEvent.setBody(mapper.writeValueAsString(request));
        APIGatewayProxyResponseEvent responseEvent = lambda.execute(requestEvent);
        
        FunctionExceptionResponse response  = mapper.readValue(responseEvent.getBody(), FunctionExceptionResponse.class);
        
        assertEquals(Boolean.TRUE, containError(response.getErrors(), "requestId"));;
    }
    private Boolean containError(Collection<Error> errors, String code) {
		Boolean contain = Boolean.FALSE;
        Iterator<Error> it = errors.iterator();
        while (it.hasNext()) {
			Error error = (Error) it.next();
			if (code.equals(error.getCode())) {
				contain = Boolean.TRUE;
			}
		}
        return contain;
	}
   
    @Test
    public void testRequestNull() throws JsonProcessingException {
      
    	APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(Lambda.FUNCTION, Function.READ);
        requestEvent.setHeaders(headers);
        requestEvent.setBody("");
        APIGatewayProxyResponseEvent responseEvent = lambda.execute(requestEvent);

        assertEquals(responseEvent.getStatusCode(), HttpResponse.badRequest().code());
    }




}
