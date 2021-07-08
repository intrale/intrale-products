package ar.com.intrale.cloud.messages;

import ar.com.intrale.cloud.Request;

public class GetProductImageRequest extends Request {

	private String id;
	private String ext;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	
}
