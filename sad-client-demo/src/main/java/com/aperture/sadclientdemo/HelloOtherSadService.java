package com.aperture.sadclientdemo;

import com.aperture.sadrpc.annotation.SadConsumer;
import com.aperture.sadserverdemo.HelloSadService;
import org.springframework.stereotype.Service;

@Service
public class HelloOtherSadService {

    @SadConsumer
    private HelloSadService service;

    public void hello() {
        System.out.println(service.hello());
    }
}
