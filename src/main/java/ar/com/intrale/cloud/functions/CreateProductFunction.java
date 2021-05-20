package ar.com.intrale.cloud.functions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import ar.com.intrale.cloud.Function;
import ar.com.intrale.cloud.FunctionException;
import ar.com.intrale.cloud.messages.ProductRequest;
import ar.com.intrale.cloud.messages.ProductResponse;

@Singleton
@Named(Function.CREATE)
public class CreateProductFunction extends Function<ProductRequest, ProductResponse, AmazonDynamoDB> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductFunction.class);

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
		
		LOGGER.info("Inicializando Create");
		
		Map<String, AttributeValue> attributesValues = new HashMap<String, AttributeValue>(); 
		
		attributesValues.put(BUSINESS_NAME, new AttributeValue(request.getBusinessName()));
		attributesValues.put(PRODUCT_ID, new AttributeValue(request.getProductId().toString()));
		attributesValues.put(PRODUCT_NAME, new AttributeValue(request.getProductName()));
		attributesValues.put(DESCRIPTION, new AttributeValue(request.getDescription()));
		attributesValues.put(DETAILS, new AttributeValue(request.getDetails()));
		attributesValues.put(STOCK, new AttributeValue(request.getStock().toString()));
		attributesValues.put(PRICE, new AttributeValue(request.getPrice().toString()));
		
		LOGGER.info("Cargando data");
		
		provider.putItem(TABLE_NAME, attributesValues);
		

	   LOGGER.info("Finalizando Create");
	   
	   
	   
       return response;
	}

}
