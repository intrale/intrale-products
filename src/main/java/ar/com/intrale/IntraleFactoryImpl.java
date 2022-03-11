package ar.com.intrale;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;

@Factory
@Requires(property = IntraleFactory.FACTORY, value = IntraleFactory.TRUE, defaultValue = IntraleFactory.TRUE)
public class IntraleFactoryImpl extends IntraleFactory<AmazonDynamoDB>{

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

}
