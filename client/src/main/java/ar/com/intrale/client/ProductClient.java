package ar.com.intrale.client;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.intrale.FunctionBuilder;
import ar.com.intrale.FunctionConst;
import ar.com.intrale.messages.FunctionExceptionResponse;
import ar.com.intrale.exceptions.ClientResponseException;
import ar.com.intrale.messages.DeleteProductRequest;
import ar.com.intrale.messages.ReadProductRequest;
import ar.com.intrale.messages.ReadProductResponse;
import ar.com.intrale.messages.SaveProductRequest;
import ar.com.intrale.messages.SaveProductResponse;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;

@Singleton
public class ProductClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductClient.class);
	
	@Client("https://mgnr0htbvd.execute-api.us-east-2.amazonaws.com")
	@Inject
	private HttpClient httpClient;
	
   	@Inject
   	protected ObjectMapper mapper;
	
	public ReadProductResponse read(String businessname, ReadProductRequest readProductRequest) throws ClientResponseException, JsonMappingException, JsonProcessingException {
		
		HttpRequest<ReadProductRequest> request = HttpRequest.POST("/dev/products", readProductRequest)
				.header(ACCEPT, "application/json")
				.header(USER_AGENT, "Micronaut HTTP Client")
				.header(FunctionBuilder.HEADER_FUNCTION, FunctionConst.READ)
				.header(FunctionBuilder.HEADER_BUSINESS_NAME, businessname);

		try {
			HttpResponse<String> response = httpClient.toBlocking().exchange(request, String.class);
			ReadProductResponse readProductResponse = mapper.readValue(response.body(), ReadProductResponse.class);
			return readProductResponse;
		} catch (HttpClientResponseException e) {
			LOGGER.error("statusCode:" + e.getStatus().getCode());
			LOGGER.error("reason:" + e.getStatus().getReason());
			LOGGER.error("message:" + e.getMessage());
			LOGGER.error("response:" + e.getResponse());
			LOGGER.error("body:" + e.getResponse().getBody());
			throw new ClientResponseException(e.getResponse().getBody(String.class).get());
		} finally {
			httpClient.refresh();
		}
	}
	

	
	public SaveProductResponse save(String businessname, SaveProductRequest saveProductRequest) throws ClientResponseException, JsonMappingException, JsonProcessingException {
		
		HttpRequest<SaveProductRequest> request = HttpRequest.POST("/dev/products", saveProductRequest)
				.header(ACCEPT, "application/json")
				.header(USER_AGENT, "Micronaut HTTP Client")
				.header(FunctionBuilder.HEADER_FUNCTION, FunctionConst.SAVE)
				.header(FunctionBuilder.HEADER_BUSINESS_NAME, businessname);
		
		
		try {
			HttpResponse<String> response = httpClient.toBlocking().exchange(request, String.class);
			SaveProductResponse saveProductResponse = mapper.readValue(response.body(), SaveProductResponse.class);
			return saveProductResponse;
		} catch (HttpClientResponseException e) {
			LOGGER.error("statusCode:" + e.getStatus().getCode());
			LOGGER.error("reason:" + e.getStatus().getReason());
			LOGGER.error("message:" + e.getMessage());
			LOGGER.error("response:" + e.getResponse());
			LOGGER.error("body:" + e.getResponse().getBody());
			throw new ClientResponseException(e.getResponse().getBody(String.class).get());
		} finally {
			httpClient.refresh();
		}
		
	}
	
	public void delete(String businessname, DeleteProductRequest deleteProductRequest) throws ClientResponseException {
		
		HttpRequest<DeleteProductRequest> request = HttpRequest.POST("/dev/products", deleteProductRequest)
				.header(ACCEPT, "application/json")
				.header(USER_AGENT, "Micronaut HTTP Client")
				.header(FunctionBuilder.HEADER_FUNCTION, FunctionConst.DELETE)
				.header(FunctionBuilder.HEADER_BUSINESS_NAME, businessname);

		try {
			httpClient.toBlocking().exchange(request, FunctionExceptionResponse.class);
		} catch (HttpClientResponseException e) {
			LOGGER.error("statusCode:" + e.getStatus().getCode());
			LOGGER.error("reason:" + e.getStatus().getReason());
			LOGGER.error("message:" + e.getMessage());
			LOGGER.error("response:" + e.getResponse());
			LOGGER.error("body:" + e.getResponse().getBody());
			throw new ClientResponseException(StringUtils.EMPTY_STRING);
		} finally {
			httpClient.refresh();
		}
	}
	
	
}
