package cenk.ozan.rest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import cenk.ozan.backend.exception.OzException;
import lombok.Data;

@Data
public class OzErrorResponse {
	@JsonProperty("ERROR CODE")
	private final int errorCode;
	@JsonProperty("ERROR MESSAGE")
	private final String msg;
	
	public OzErrorResponse(OzException e) {
		errorCode = e.errorCode();
		msg = e.getMessage();
	}

	public OzErrorResponse(int code, String msg) {
		errorCode = code;
		this.msg = msg;
	}

}
