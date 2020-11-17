package com.example.demo.controller;


//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.DemoService;



@RestController
//@Api(value = "ejemplo de controlador")
@RequestMapping("/example")
@Validated
@Slf4j
@CrossOrigin(origins = "*") //TODO: @CrossOrigin("trustedwebsite.com")
public class DemoController {


	  @Autowired
	  private DemoService demoService;



  @GetMapping(value = "/healthcheck")
  public ResponseEntity<String> healthCheck() {
	  log.debug("Healthcheck");
      return new ResponseEntity<>("{ \"respuesta\" : \"Estoy vivo !!!\"}", HttpStatus.OK);
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



}


