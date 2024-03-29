package ar.com.intrale.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import ar.com.intrale.BaseFunction;
import ar.com.intrale.FunctionConst;
import ar.com.intrale.FunctionResponseToBase64HttpResponseBuilder;
import ar.com.intrale.exceptions.FunctionException;
import ar.com.intrale.mappers.ProductMapper;
import ar.com.intrale.messages.ReadProductRequest;
import ar.com.intrale.messages.ReadProductResponse;
import ar.com.intrale.messages.builders.StringToReadProductRequestBuilder;
import ar.com.intrale.models.Product;
import io.micronaut.core.util.StringUtils;

@Singleton
@Named(FunctionConst.READ)
public class ReadProductFunction extends BaseFunction<ReadProductRequest, ReadProductResponse, AmazonDynamoDB, StringToReadProductRequestBuilder, FunctionResponseToBase64HttpResponseBuilder> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadProductFunction.class);
	
	public static final String TWO_POINTS = ":";
	public static final String ATT = "ATT";
	public static final String EQUAL = ATT + " = " + TWO_POINTS + ATT;
	
	public ReadProductResponse execute(ReadProductRequest request)	throws FunctionException {
		ReadProductResponse response = new ReadProductResponse();
		
		DynamoDBMapper mapper = new DynamoDBMapper(provider);
		DynamoDBScanExpression dbScanExpression = new DynamoDBScanExpression();
		
		// dynamoDB filters
		addEqualFilter(dbScanExpression, FunctionConst.BUSINESS_NAME, request.getBusinessName());
		
		PaginatedList<Product> list = mapper.scan(Product.class, dbScanExpression);
		
		if (!list.isEmpty()) {
			list.forEach(new Consumer<Product>() {
				@Override
				public void accept(Product product) {
					// filter list with request criteria
					Boolean needsToBeFiltered = isNeedsToBeFiltered(request, product);
					
					if (!needsToBeFiltered) {
						response.add(ProductMapper.INSTANCE.productToProductMessage(product));
					}
				}

			});
		}
		
       return response;
	}

	private void addEqualFilter(DynamoDBScanExpression dbScanExpression, String property, String value) {
		if (StringUtils.isNotEmpty(value)) {
			Map<String, AttributeValue> values = new HashMap<String, AttributeValue>();
			values.put(TWO_POINTS + property, new AttributeValue().withS(value));
			dbScanExpression.withFilterExpression(EQUAL.replaceAll(ATT, property)).withExpressionAttributeValues(values);
		}
	}

	/**
	 * Retorna verdadero si necesita ser filtrado y NO retornado junto con el response
	 * @param request
	 * @param product
	 * @return
	 */
	protected Boolean isNeedsToBeFiltered(ReadProductRequest request, Product product) {
		Boolean productIdFilter = (!StringUtils.isEmpty(request.getProductId()) && !product.getId().equals(request.getProductId()));
		Boolean nameFilter = (!StringUtils.isEmpty(request.getName()) && !product.getName().equals(request.getName()));
		Boolean descriptionFilter = (!StringUtils.isEmpty(request.getDescription()) && !product.getDescription().equals(request.getDescription()));

		/*Boolean fromStockFilter = (!(request.getFromStock()==null) && !(product.getStock() >= request.getFromStock()));
		Boolean toStockFilter = (!(request.getToStock()==null) && !(product.getStock() <= request.getToStock()));

		Boolean currencyAcronymFilter = (!StringUtils.isEmpty(request.getCurrencyAcronym()) && !product.getPrice().getCurrencyAcronym().equals(request.getCurrencyAcronym()));
		
		Boolean fromPriceFilter = (!(request.getFromPrice()==null) && !(product.getPrice().getUnitPrice() >= request.getFromPrice()));
		Boolean toPriceFilter = (!(request.getToPrice()==null) && !(product.getPrice().getUnitPrice() <= request.getToPrice()));
		*/
		Boolean needsToBeFiltered = (productIdFilter || nameFilter || descriptionFilter /*|| fromStockFilter || toStockFilter || currencyAcronymFilter || fromPriceFilter || toPriceFilter*/);
		return needsToBeFiltered;
	}

}
