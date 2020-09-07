package com.aperture.sadrpc.serialize;

public interface Serialization {
    <T> byte[] serialize(T obj);
    <T> T deSerialize(byte[] data, Class<T> clz);
}
