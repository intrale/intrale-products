package ar.com.intrale.functions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import ar.com.intrale.BaseFunction;
import ar.com.intrale.FunctionResponseToBase64HttpResponseBuilder;
import ar.com.intrale.exceptions.FunctionException;
import ar.com.intrale.messages.DeleteProductRequest;
import ar.com.intrale.FunctionConst;
import ar.com.intrale.messages.Response;
import ar.com.intrale.messages.builders.StringToDeleteProductRequestBuilder;

@Singleton
@Named(FunctionConst.DELETE)
public class DeleteProductFunction extends BaseFunction<DeleteProductRequest, Response, AmazonDynamoDB, StringToDeleteProductRequestBuilder, FunctionResponseToBase64HttpResponseBuilder> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteProductFunction.class);
	
	public static final String TABLE_NAME 		= "product";
	
    public static final String PRODUCT_ID 		= "productId";
	
	
	@Override
	public Response execute(DeleteProductRequest request) throws FunctionException {
		
		Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put(PRODUCT_ID, new AttributeValue(request.getProductId().toString()));
		
		provider.deleteItem(TABLE_NAME, key);
	   
       return new Response();
	}
}
