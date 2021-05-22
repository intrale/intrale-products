package ar.com.intrale.cloud.functions;

import java.util.Iterator;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import ar.com.intrale.cloud.Function;
import ar.com.intrale.cloud.FunctionException;
import ar.com.intrale.cloud.messages.Product;
import ar.com.intrale.cloud.messages.ReadProductRequest;
import ar.com.intrale.cloud.messages.ReadProductResponse;

@Singleton
@Named(Function.READ)
public class ReadProductFunction extends Function<ReadProductRequest, ReadProductResponse, AmazonDynamoDB> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadProductFunction.class);

	public static final String TABLE_NAME 		= "product";
	
    public static final String BUSINESS_NAME 	= "businessName";
    public static final String PRODUCT_ID 		= "productId";
    public static final String PRODUCT_NAME 	= "productName";
    public static final String DESCRIPTION 		= "description";
    public static final String DETAILS 			= "details";
    public static final String STOCK 			= "stock";
    public static final String PRICE 			= "price";
    
	
	@Override
	public ReadProductResponse execute(ReadProductRequest request) throws FunctionException {
		DynamoDB dynamoDB = new DynamoDB(provider);
		Table table = dynamoDB.getTable(TABLE_NAME);
		
		ReadProductResponse response = new ReadProductResponse();
		
		QuerySpec querySpec = new QuerySpec()
				.withKeyConditionExpression(PRODUCT_ID + " = " + TWO_POINTS + PRODUCT_ID)
				.withValueMap(new ValueMap()	
						.withString(TWO_POINTS + PRODUCT_ID, request.getProductId()));
		
		ItemCollection<QueryOutcome> items = table.query(querySpec);
		
		
		Iterator<Item> iterator = items.iterator();
		
		while (iterator.hasNext()) {
			Item item = (Item) iterator.next();
			Product product = new Product();
			product.setProductId(item.getString(PRODUCT_ID));
			product.setDescription(item.getString(DESCRIPTION));
			product.setDetails(item.getString(DETAILS));
			product.setPrice(Double.valueOf(item.getString(PRICE)));
			product.setProductName(item.getString(PRODUCT_NAME));
			product.setStock(Long.valueOf(item.getString(STOCK)));
			
			response.add(product);
		}
	   
       return response;
	}

}
