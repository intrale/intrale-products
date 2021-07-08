package ar.com.intrale.cloud.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ar.com.intrale.cloud.messages.ProductMessage;
import ar.com.intrale.cloud.messages.SaveProductRequest;
import ar.com.intrale.cloud.models.Product;

@Mapper
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	@Mappings({
	      @Mapping(target="id", source="productId"),
	    })
	Product saveProductRequestToProduct(SaveProductRequest request);
	ProductMessage productToProductMessage(Product product);
	
}
