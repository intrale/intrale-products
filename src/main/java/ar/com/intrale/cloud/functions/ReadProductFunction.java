package ar.com.intrale.cloud.functions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import ar.com.intrale.cloud.Function;
import ar.com.intrale.cloud.FunctionException;
import ar.com.intrale.cloud.messages.ProductEntity;
import ar.com.intrale.cloud.messages.ProductRequest;
import ar.com.intrale.cloud.messages.ProductResponse;

@Singleton
@Named(Function.READ)
public class ReadProductFunction extends Function<ProductRequest, ProductResponse, AmazonDynamoDB> {

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
		DynamoDB dynamoDB = new DynamoDB(provider);
		Table table = dynamoDB.getTable(TABLE_NAME);
		
		ProductResponse response = new ProductResponse();
		
		QuerySpec querySpec = new QuerySpec()
				.withKeyConditionExpression(PRODUCT_ID + " = " + TWO_POINTS + PRODUCT_ID)
				.withValueMap(new ValueMap()	
						.withString(TWO_POINTS + PRODUCT_ID, request.getProductId().toString()));
		
		ItemCollection<QueryOutcome> items = table.query(querySpec);
		
		
		Iterator<Item> iterator = items.iterator();
		
		while (iterator.hasNext()) {
			Item item = (Item) iterator.next();
			ProductEntity product = new ProductEntity();
			product.setProductId(Long.valueOf(item.getString(PRODUCT_ID)));
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
