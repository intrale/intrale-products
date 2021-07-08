package ar.com.intrale.cloud.messages;

import ar.com.intrale.cloud.Request;

public class SaveProductImageRequest extends Request{

	private String id;

	private String base64Image;
	
	/*private String ext;

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
}
