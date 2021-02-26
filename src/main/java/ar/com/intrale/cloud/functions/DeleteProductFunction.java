package ar.com.intrale.cloud.functions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import ar.com.intrale.cloud.Function;
import ar.com.intrale.cloud.FunctionException;
import ar.com.intrale.cloud.messages.ProductRequest;
import ar.com.intrale.cloud.messages.ProductResponse;

@Singleton
@Named(Function.DELETE)
public class DeleteProductFunction extends Function<ProductRequest, ProductResponse, AmazonDynamoDB> {

	public static final String TABLE_NAME 		= "product";
	
    public static final String BUSINESS_NAME 	= "businessName";
    public static final String PRODUCT_ID 		= "productId";
    public static final String PRODUCT_NAME 	= "productName";
    public static final String DESCRIPTION 		= "description";
    public static final String DETAILS 			= "details";
    public static final String STOCK 			= "stock";
    public static final String PRICE 			= "price";
    
    public static final String NUMERAL = "#";
    public static final String TWO_POINTS = ":";
	
	
	@Override
	public ProductResponse execute(ProductRequest request) throws FunctionException {
		
		Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put(PRODUCT_ID, new AttributeValue(request.getProductId().toString()));
		
		provider.deleteItem(TABLE_NAME, key);
	   
       return new ProductResponse();
	}

}
