package cl.devweb.poc.service;

import org.springframework.stereotype.Service;

import cl.devweb.poc.dto.DemoDto;
//import kong.unirest.HttpResponse;
import lombok.extern.slf4j.Slf4j;




@Service
@Slf4j
public class DemoService {

	private DemoDto demoDto;


    public DemoService() {
        log.debug("Hola DemoService..................");
    }



    public String getMundo(long cifId) {

    	return "{ 'holaMundo' }" + demoDto;
    }


    //ejemplo 2:
    private String name = null;

    public String greet(String greetee) {

      if (greetee != null) {
        this.name = greetee;
      }

      return "Hello " + this.name;  // if greetee is null, you see the previous user's data
    }



}


