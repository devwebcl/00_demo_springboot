package cl.devweb.poc.service.third.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {

	@JsonProperty("status")
	private Status status;
	@JsonProperty("token")
	private String token;

	@Override
	public String toString() {
		return "AuthResponseDto [status=" + status + ", token=" + token + "]";
	}

}
