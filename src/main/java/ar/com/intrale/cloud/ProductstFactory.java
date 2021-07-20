package ar.com.intrale.cloud;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;

@Factory
@Requires(property = IntraleFactory.FACTORY, value = IntraleFactory.TRUE, defaultValue = IntraleFactory.TRUE)
public class ProductstFactory extends IntraleFactory<AmazonDynamoDB>{

	@Bean @Requires(property = IntraleFactory.PROVIDER, value = IntraleFactory.TRUE, defaultValue = IntraleFactory.TRUE)
	@Override
	public AmazonDynamoDB provider() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(config.getDatabase().getAccess(), config.getDatabase().getSecret());
    	
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
          .withCredentials(new AWSStaticCredentialsProvider(credentials))
          .withRegion(config.getAws().getRegion())
          .build();
         
        return amazonDynamoDB;
	}
	
	
	@Bean @Requires(property = "app.instantiate.s3provider", value = IntraleFactory.TRUE, defaultValue = IntraleFactory.TRUE)
	public AmazonS3 s3Provider() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(config.getS3().getAccess(), config.getS3().getSecret());
    	
		AmazonS3 amazonDynamoDB = AmazonS3ClientBuilder.standard()
          .withCredentials(new AWSStaticCredentialsProvider(credentials))
          .withRegion(config.getAws().getRegion())
          .build();
         
        return amazonDynamoDB;
	}
	

}
