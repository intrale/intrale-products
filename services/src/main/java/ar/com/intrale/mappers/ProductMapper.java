package ar.com.intrale.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ar.com.intrale.messages.ProductMessage;
import ar.com.intrale.messages.SaveProductRequest;
import ar.com.intrale.models.Product;

@Mapper
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	@Mappings({
	      @Mapping(target="id", source="productId"),
	    })
	Product saveProductRequestToProduct(SaveProductRequest request);
	ProductMessage productToProductMessage(Product product);
	
}
