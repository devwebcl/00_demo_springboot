package cl.devweb.poc.service.third.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Status {

	@JsonProperty("code")
	private String code;
	@JsonProperty("message")
	private String message;

	@Override
	public String toString() {
		return "Status [code=" + code + ", message=" + message + "]";
	}

}
