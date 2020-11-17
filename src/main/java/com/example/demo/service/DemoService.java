package com.example.demo.service;

import org.springframework.stereotype.Service;

//import kong.unirest.HttpResponse;
import lombok.extern.slf4j.Slf4j;




@Service
@Slf4j
public class DemoService {



    public DemoService() {
        log.debug("Hola DemoService..................");
    }



    public String getMundo(long cifId) {

    	return "{ 'holaMundo' }";
    }


}


