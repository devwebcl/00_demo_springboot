package com.example.demo.service.third.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthRequestDto {

	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;

}
