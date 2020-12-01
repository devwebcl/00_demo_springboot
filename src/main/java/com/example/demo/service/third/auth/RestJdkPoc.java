package com.example.demo.service.third.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.service.third.auth.dto.AuthRequestDto;
import com.example.demo.service.third.auth.dto.AuthResponseDto;




/*
 * PoC de cliente JDK http
 */

@Service
//@Scope("request") // cero
public class RestJdkPoc {

	public final static int connectTimeout = 3000;
	public final static int readTimeout = 3000;

    @Value("${rest.web-services.third-authorizer.base-url}")
    private String url;

    @Value("${rest.web-services.third-authorizer.username}")
    private String username;

    @Value("${rest.web-services.third-authorizer.password}")
    private String password;



    public RestJdkPoc() {
        //log.debug("RestJdkPoc..................");

    }

	private RestTemplate restTemplateInterceptorTimeouts(ClientHttpRequestInterceptor interceptor, int connectTimeout,
			int readTimeout) {

		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		simpleClientHttpRequestFactory.setConnectTimeout(connectTimeout);
		simpleClientHttpRequestFactory.setReadTimeout(readTimeout);

		RestTemplate restTemplate = new RestTemplate(
				new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory)
				//simpleClientHttpRequestFactory
				);

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		if (interceptor != null) {
			interceptors.add(interceptor);
		}
		// if (logRestTemplateRequestsEnabled) {
		// interceptors.add(restTemplateLoggerInterceptor());
		// }

		restTemplate.setRequestFactory(
				new InterceptingClientHttpRequestFactory(restTemplate.getRequestFactory(), interceptors));

		return restTemplate;
	}


	public AuthResponseDto execute(AuthRequestDto request) {

		request.setUsername(username);
		request.setPassword(password);

		// RestTemplate restTemplate = (RestTemplate)
		// applicationContext.getBean("restTemplateTimeouts", connectTimeout,
		// readTimeout);

		RestTemplate restTemplate = restTemplateInterceptorTimeouts(null, connectTimeout, readTimeout);

		return restTemplate.postForObject(url, request, AuthResponseDto.class);
	}

}

