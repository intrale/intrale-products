package ar.com.intrale.cloud.functions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;

import ar.com.intrale.cloud.Function;
import ar.com.intrale.cloud.FunctionException;
import ar.com.intrale.cloud.messages.ProductRequest;
import ar.com.intrale.cloud.messages.ProductResponse;

@Singleton
@Named(Function.UPDATE)
public class UpdateProductFunction extends Function<ProductRequest, ProductResponse, AmazonDynamoDB> {

	public static final String TABLE_NAME 		= "product";
	
    public static final String BUSINESS_NAME 	= "businessName";
    public static final String PRODUCT_ID 		= "productId";
    public static final String PRODUCT_NAME 	= "productName";
    public static final String DESCRIPTION 		= "description";
    public static final String DETAILS 			= "details";
    public static final String STOCK 			= "stock";
    public static final String PRICE 			= "price";
	
	
	@Override
	public ProductResponse execute(ProductRequest request) throws FunctionException {
		ProductResponse response = new ProductResponse();
		
		Map<String, AttributeValue> keysValues = new HashMap<String, AttributeValue>(); 
		keysValues.put(PRODUCT_ID, new AttributeValue(request.getProductId().toString()));
		
		Map<String, AttributeValueUpdate> attributesValues = new HashMap<String, AttributeValueUpdate>(); 
		attributesValues.put(BUSINESS_NAME, new AttributeValueUpdate(new AttributeValue(request.getBusinessName()), AttributeAction.PUT));
		attributesValues.put(PRODUCT_NAME, new AttributeValueUpdate(new AttributeValue(request.getProductName()), AttributeAction.PUT));
		attributesValues.put(DESCRIPTION, new AttributeValueUpdate(new AttributeValue(request.getDescription()), AttributeAction.PUT));
		attributesValues.put(DETAILS, new AttributeValueUpdate(new AttributeValue(request.getDetails()), AttributeAction.PUT));
		attributesValues.put(STOCK, new AttributeValueUpdate(new AttributeValue(request.getStock().toString()), AttributeAction.PUT));
		attributesValues.put(PRICE, new AttributeValueUpdate(new AttributeValue(request.getPrice().toString()), AttributeAction.PUT));
		
		provider.updateItem(TABLE_NAME, keysValues, attributesValues);
		

	   LOGGER.info("finalizando handler");
	   
	   
	   
       return response;
	}

}
