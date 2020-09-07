package com.aperture.sadserverdemo;

import com.aperture.sadrpc.annotation.SadProvider;
import org.springframework.stereotype.Service;

@Service
@SadProvider
public class HelloSadServiceImpl implements HelloSadService {
    @Override
    public String hello() {
        return "sad guy";
    }
}
