package ar.com.intrale.cloud.functions;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import ar.com.intrale.cloud.BaseFunction;
import ar.com.intrale.cloud.FunctionConst;
import ar.com.intrale.cloud.FunctionResponseToHttpResponseBuilder;
import ar.com.intrale.cloud.exceptions.FunctionException;
import ar.com.intrale.cloud.mappers.ProductMapper;
import ar.com.intrale.cloud.messages.SaveProductImageRequest;
import ar.com.intrale.cloud.messages.SaveProductRequest;
import ar.com.intrale.cloud.messages.SaveProductResponse;
import ar.com.intrale.cloud.messages.builders.StringToSaveProductRequestBuilder;
import ar.com.intrale.cloud.models.Product;

@Singleton
@Named(FunctionConst.SAVE)
public class SaveProductFunction extends BaseFunction<SaveProductRequest, SaveProductResponse, AmazonDynamoDB, StringToSaveProductRequestBuilder, FunctionResponseToHttpResponseBuilder> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SaveProductFunction.class);

	@Inject
	private SaveProductImageFunction productImageFunction;
	
	@Override
	public SaveProductResponse execute(SaveProductRequest request) throws FunctionException {
		SaveProductResponse response = new SaveProductResponse();
		
		LOGGER.info("Inicializando Create");
		
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(provider);
		
		Product product = ProductMapper.INSTANCE.saveProductRequestToProduct(request);
		dynamoDBMapper.save(product);
		
		SaveProductImageRequest imageRequest = new SaveProductImageRequest();
		imageRequest.setRequestId(request.getRequestId());
		imageRequest.setId(product.getId());
		imageRequest.setBase64Image(request.getBase64Image());
		productImageFunction.execute(imageRequest);

		response.setProductId(product.getId());
	   LOGGER.info("Finalizando Create");
	   
       return response;
	}

}