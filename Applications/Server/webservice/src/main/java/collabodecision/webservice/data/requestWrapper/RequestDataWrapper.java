package collabodecision.webservice.data.requestWrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RequestDataWrapper<E> {

	private E data;

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}
}
