package collabodecision.webservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class is needed, because the Request Data from a standard JS Call is
 * nested in a data property. (this class is just for wrapping the data property!)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestWrapperData<E> {

	private E data;

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}
}
