package com.aperture.sadrpc.serialize;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class JsonSerialization implements Serialization {

    private Gson gson = new Gson();

    public <T> byte[] serialize(T obj) {
        return gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
    }


    public <T> T deSerialize(byte[] data, Class<T> clz) {
        return gson.fromJson(new String(data, StandardCharsets.UTF_8), clz);
    }
}
