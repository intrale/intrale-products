package ar.com.intrale.cloud.functions;

import javax.inject.Named;
import javax.inject.Singleton;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import ar.com.intrale.cloud.BaseFunction;
import ar.com.intrale.cloud.ObjectToSameBuilder;
import ar.com.intrale.cloud.exceptions.FunctionException;
import ar.com.intrale.cloud.messages.GetProductImageRequest;
import ar.com.intrale.cloud.messages.builders.StringToGetProductImageRequestBuilder;

@Singleton
@Named(GetProductImageFunction.FUNCTION_NAME)
public class GetProductImageFunction extends BaseFunction<GetProductImageRequest, S3ObjectInputStream, AmazonS3, StringToGetProductImageRequestBuilder, ObjectToSameBuilder> {

	public static final String FUNCTION_NAME = "getImage";

	@Override
	public S3ObjectInputStream execute(GetProductImageRequest request) throws FunctionException {
		
		return provider.getObject(config.getS3().getBucketName(), request.getId()).getObjectContent();
	}


}
