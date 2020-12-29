package com.example.demo.controller;


//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DemoService;
import com.example.demo.service.third.auth.RestApache;
import com.example.demo.service.third.auth.RestJdkPoc;
import com.example.demo.service.third.auth.dto.AuthRequestDto;
import com.example.demo.service.third.auth.dto.AuthResponseDto;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONArray;

//import io.micrometer.core.annotation.Timed;



@RestController
//@Api(value = "ejemplo de controlador")
@RequestMapping("/example")
@Validated
@Slf4j
@CrossOrigin(origins = "*") //TODO: @CrossOrigin("trustedwebsite.com")
public class DemoController {


	  @Autowired
	  private DemoService demoService;

	  @Autowired
	  private RestJdkPoc restJdkPoc;

	  @Autowired
	  private RestApache restApache;



	  @GetMapping(value = "/healthcheck")
	  public ResponseEntity<String> healthCheck() {
		  log.debug("Healthcheck");
	      return new ResponseEntity<>("{ \"respuesta\" : \"Estoy vivo !!!\"}", HttpStatus.OK);
	  }


	  //@Timed
	  //@Transactional(timeout = 5)
	  @GetMapping(value = "/test")
	  public ResponseEntity<String> test() {

		  System.out.println("http.keepAlive-->" + System.getProperty("http.keepAlive"));

		  try {
			  if(true)
			    Thread.sleep(4000);
		  } catch(Exception e) {
			  System.out.println(e);
		  }

		  //Thread.currentThread().interrupt();

		  log.debug("test");
	      return new ResponseEntity<>("{ \"respuesta\" : \"test !!!\"}", HttpStatus.OK);
	  }


	  @GetMapping(value = "/vars")
	  public ResponseEntity<String> vars() {

		  System.setProperty("http.keepAlive", "false");

			Properties p = System.getProperties();
			Enumeration keys = p.keys();
			while (keys.hasMoreElements()) {
			    String key = (String)keys.nextElement();
			    String value = (String)p.get(key);
			    System.out.println(key + ": " + value);
			}


		  //Thread.currentThread().interrupt();

		  log.debug("test");
	      return new ResponseEntity<>("{ \"respuesta\" : \"vars !!!\"}", HttpStatus.OK);
	  }




	  // TODO: swagger !
	  // http://127.0.0.1:8080/example/hola?mundo=1
	  @GetMapping(value="/hola")
	  public @ResponseBody String getCuentasVinculadas(@RequestParam("mundo") long mundo) {

			try {

				log.debug("mundo = {}", mundo);

				Calendar calendar = Calendar.getInstance();
				System.out.println("> " + calendar.getTimeZone() + "\n");
				System.out.println("today is " + new Date() );

				 MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
				 long memoria = memoryBean.getHeapMemoryUsage().getMax();

				 System.out.println("xmx = " + memoria);


				String resultado = demoService.getMundo(mundo);

				log.debug("resultado: {}", resultado);

				return resultado;

			} catch (Exception e) {
				log.error("searchCustomer Error: {}", e); // ExceptionUtils.getStackTrace(e)
				return "error = " + e;
			}
	  }



	  // http://127.0.0.1:8080/example/jdk


	  //JDK client
	  @GetMapping(value="/jdk")
	  public @ResponseBody String getRestJdkPoc() {

		System.out.println("jdk = {}");

		//workaround hediondo:
		//System.setProperty("http.keepAlive", "false");
		System.out.println("http.keepAlive-->" + System.getProperty("http.keepAlive"));


		for(int i=0; i<20; i++) {

			AuthRequestDto request = AuthRequestDto.builder().build();

			// sleep !
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}

			AuthResponseDto response = restJdkPoc.execute(request);
			System.out.println("jdk response=" + response);
	    }


		System.out.println("http.keepAlive-->" + System.getProperty("http.keepAlive"));
		System.out.println("jdk end.");

		return "ok.";
	  }


	  //Apache Client
	  @GetMapping(value="/apache")
	  public @ResponseBody String getRestApachePoc() {

		System.out.println("apache = {}");

		for(int i=0; i<20; i++) {

			AuthRequestDto request = AuthRequestDto.builder().build();

			// sleep !
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}

			AuthResponseDto response = restApache.execute(request);
			System.out.println("apache response=" + response);
	    }



		System.out.println("apache end.");

		return "ok.";
	  }



		// json 4kb response
		@GetMapping(value = "/fourkb", produces = "application/json")
		public @ResponseBody String getJson4kb() {

			System.out.println("fourkb = {}");

			JSONArray personas = new JSONArray();

			for (int i = 0; i < 100; i++) { // 50~=4kb , 100~=8kb

				JSONObject persona = new JSONObject();

				persona.put("name", "juan");
				persona.put("lastname", "perez");
				persona.put("id", UUID.randomUUID());

				personas.put(persona);
			}

			String json = new Gson().toJson(personas);

			System.out.println("fourkb end.");

			return json;
		}



}


