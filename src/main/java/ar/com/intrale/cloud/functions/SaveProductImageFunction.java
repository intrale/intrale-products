package ar.com.intrale.cloud.functions;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;

import ar.com.intrale.cloud.BaseFunction;
import ar.com.intrale.cloud.Error;
import ar.com.intrale.cloud.FunctionResponseToHttpResponseBuilder;
import ar.com.intrale.cloud.Response;
import ar.com.intrale.cloud.exceptions.FunctionException;
import ar.com.intrale.cloud.exceptions.S3BucketException;
import ar.com.intrale.cloud.messages.SaveProductImageRequest;
import ar.com.intrale.cloud.messages.builders.StringToSaveProductImageRequestBuilder;
import io.micronaut.core.util.StringUtils;

@Singleton
@Named(SaveProductImageFunction.FUNCTION_NAME)
public class SaveProductImageFunction extends BaseFunction<SaveProductImageRequest, Response, AmazonS3, StringToSaveProductImageRequestBuilder, FunctionResponseToHttpResponseBuilder> {

	public static final String FUNCTION_NAME = "saveImage";
	private static final Logger LOGGER = LoggerFactory.getLogger(SaveProductImageFunction.class);
	
	@Override
	public Response execute(SaveProductImageRequest request) throws FunctionException {
		
		if (!StringUtils.isEmpty(request.getBase64Image())) {
			try {
				byte[] base64Image = Base64.getDecoder().decode(request.getBase64Image());
				provider.putObject(config.getS3().getBucketName(), request.getId(), Files.write(Paths.get("/tmp/" + request.getId()), base64Image).toFile());
			} catch (Exception e) {
				LOGGER.error(FunctionException.toString(e));
				throw new S3BucketException(new Error("S3_PUT_OBJECT", "S3 Put Object Exception"), mapper);
			}
		}
		
		return new Response();
	}

}
