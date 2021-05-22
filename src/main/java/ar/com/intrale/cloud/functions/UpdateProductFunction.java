package ar.com.intrale.cloud.functions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;

import ar.com.intrale.cloud.Function;
import ar.com.intrale.cloud.FunctionException;
import ar.com.intrale.cloud.messages.UpdateProductRequest;
import ar.com.intrale.cloud.messages.UpdateProductResponse;
import io.micronaut.core.util.StringUtils;

@Singleton
@Named(Function.UPDATE)
public class UpdateProductFunction extends Function<UpdateProductRequest, UpdateProductResponse, AmazonDynamoDB> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateProductFunction.class);

	public static final String TABLE_NAME 		= "product";
	
    public static final String BUSINESS_NAME 	= "businessName";
    public static final String PRODUCT_ID 		= "productId";
    public static final String PRODUCT_NAME 	= "productName";
    public static final String DESCRIPTION 		= "description";
    public static final String DETAILS 			= "details";
    public static final String STOCK 			= "stock";
    public static final String PRICE 			= "price";
	
	
	@Override
	public UpdateProductResponse execute(UpdateProductRequest request) throws FunctionException {
		UpdateProductResponse response = new UpdateProductResponse();
		
		Map<String, AttributeValue> keysValues = new HashMap<String, AttributeValue>(); 
		keysValues.put(PRODUCT_ID, new AttributeValue(request.getProductId()));
		
		Map<String, AttributeValueUpdate> attributesValues = new HashMap<String, AttributeValueUpdate>(); 
		
		putAttribute(attributesValues, BUSINESS_NAME, request.getBusinessName());
		putAttribute(attributesValues, PRODUCT_NAME, request.getProductName());
		putAttribute(attributesValues, DESCRIPTION, request.getDescription());
		putAttribute(attributesValues, DETAILS, request.getDetails());
		putAttribute(attributesValues, STOCK, request.getStock());
		putAttribute(attributesValues, PRICE, request.getPrice());
		
		provider.updateItem(TABLE_NAME, keysValues, attributesValues);
		

	   LOGGER.info("finalizando handler");
	   
	   
	   
       return response;
	}


	private void putAttribute(Map<String, AttributeValueUpdate> attributesValues, String key, Object value) {
		if ((value!=null)&&(!StringUtils.isEmpty(value.toString()))) {
			attributesValues.put(key, new AttributeValueUpdate(new AttributeValue(value.toString()), AttributeAction.PUT));
		}
	}

}
