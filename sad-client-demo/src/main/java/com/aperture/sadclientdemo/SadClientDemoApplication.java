package com.aperture.sadclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SadClientDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SadClientDemoApplication.class, args);
        HelloOtherSadService service = ctx.getBean(HelloOtherSadService.class);
        service.hello();
    }
}
