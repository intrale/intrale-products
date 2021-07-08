package ar.com.intrale.cloud.messages;

public class ProductMessage {
	
	private String id;
	private String title;
	private String description;
	private Long stock;
	private PriceMessage price;
	private String pictureUrl;
	private String category;

	public String getPictureUrl() {
		return pictureUrl;
	}

	/*public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}*/

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public PriceMessage getPrice() {
		return price;
	}

	public void setPrice(PriceMessage price) {
		this.price = price;
	}

}
