package ar.com.intrale.services;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.intrale.cloud.FunctionBuilder;
import ar.com.intrale.cloud.FunctionConst;
import ar.com.intrale.cloud.Response;
import ar.com.intrale.cloud.exceptions.ClientResponseException;
import ar.com.intrale.messages.DeleteProductRequest;
import ar.com.intrale.messages.ProductMessage;
import ar.com.intrale.messages.ReadProductRequest;
import ar.com.intrale.messages.ReadProductResponse;
import ar.com.intrale.messages.SaveProductRequest;
import ar.com.intrale.messages.SaveProductResponse;
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
	
	public ProductMessage read(String requestId, String productId) {
		
		
		ReadProductRequest readProductRequest = new ReadProductRequest();
		readProductRequest.setRequestId(requestId);
		readProductRequest.setProductId(productId);
		
		HttpRequest<ReadProductRequest> request = HttpRequest.POST("/dev/products", readProductRequest)
				.header(ACCEPT, "application/json")
				.header(USER_AGENT, "Micronaut HTTP Client")
				.header(FunctionBuilder.HEADER_FUNCTION, FunctionConst.READ);

		
		HttpResponse<ReadProductResponse> readProductResponse = httpClient.toBlocking().exchange(request, ReadProductResponse.class);
		
		return readProductResponse.body().getProducts().iterator().next();
	}
	

	
	public String save(String requestId, String productId) throws ClientResponseException {
		System.out.println("save 1");
	
		SaveProductRequest saveProductRequest = new SaveProductRequest();
		saveProductRequest.setRequestId(requestId);
		saveProductRequest.setProductId(productId);
		
		System.out.println("save 2");
		
		HttpRequest<SaveProductRequest> request = HttpRequest.POST("/dev/products", saveProductRequest)
				.header(ACCEPT, "application/json")
				.header(USER_AGENT, "Micronaut HTTP Client")
				.header(FunctionBuilder.HEADER_FUNCTION, FunctionConst.SAVE);

		System.out.println("save 3");
		
		try {
			HttpResponse<SaveProductResponse> response = httpClient.toBlocking().exchange(request, SaveProductResponse.class);
			System.out.println("save 4");
			return response.body().getProductId();
		} catch (HttpClientResponseException e) {
			LOGGER.error("Ocurrio un error=> statusCode:" + e.getStatus().toString() + ", body:" + e.getResponse().body());
			throw new ClientResponseException("nada");
		}
		
	}
	
	public void delete(String requestId, String productId) {
		
		DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
		deleteProductRequest.setRequestId(requestId);
		deleteProductRequest.setProductId(productId);
		
		HttpRequest<DeleteProductRequest> request = HttpRequest.POST("/dev/products", deleteProductRequest)
				.header(ACCEPT, "application/json")
				.header(USER_AGENT, "Micronaut HTTP Client")
				.header(FunctionBuilder.HEADER_FUNCTION, FunctionConst.DELETE);

		
		HttpResponse<Response> saveProductResponse = httpClient.toBlocking().exchange(request, Response.class);
		
	}
	
	
}
