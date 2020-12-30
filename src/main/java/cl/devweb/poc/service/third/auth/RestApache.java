package cl.devweb.poc.service.third.auth;


import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.devweb.poc.service.third.auth.dto.AuthRequestDto;
import cl.devweb.poc.service.third.auth.dto.AuthResponseDto;



@Service
public class RestApache {

    private final static int maxTotalConnections = 50;
    private final static int maxConnectionsPerRoute = 10;
    private final static int connectRequestTimeout = 3000;
    private final static int connectTimeout = 3000; //2
    private final static int readTimeout = 3000; //7

    @Value("${rest.web-services.third-authorizer.username}")
    private String username;

    @Value("${rest.web-services.third-authorizer.password}")
    private String password;

    @Value("${rest.web-services.third-authorizer.base-url}")
    private String url;



	/*
	 * PoC de cliente Apache http
	 */

	  private RestTemplate getrestTemplateApache() {

	        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
	        connManager.setMaxTotal(maxTotalConnections);
	        connManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);

	        CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();

	        // Asigna valores de timeout de conexión y lectura para servicios REST invocados mediante este RestTemplate
	        // utilizando un HttpComponentsClientHttpRequestFactory
	        HttpComponentsClientHttpRequestFactory httpRequestFactory;
	        httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	        httpRequestFactory.setConnectionRequestTimeout(connectRequestTimeout);
	        httpRequestFactory.setConnectTimeout(connectTimeout);
	        httpRequestFactory.setReadTimeout(readTimeout);
	        httpRequestFactory.setHttpClient(httpClient);

	        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);

	        /*
	        this.setRequestFactory(httpRequestFactory);
	        this.setMessageConverters(MessageConverterUtil.generateMessageConverters());
	        this.setErrorHandler(new DefaultResponseErrorHandler() {
	            @Override
	            protected boolean hasError(HttpStatus statusCode) {
	                return false;
	            }
	        });*/


		    return restTemplate;
		  }


		public AuthResponseDto execute(AuthRequestDto request) {

			request.setUsername(username);
			request.setPassword(password);

			// RestTemplate restTemplate = (RestTemplate)
			// applicationContext.getBean("restTemplateTimeouts", connectTimeout,
			// readTimeout);

			RestTemplate restTemplate = getrestTemplateApache();

			return restTemplate.postForObject(url, request, AuthResponseDto.class);
		}
}

