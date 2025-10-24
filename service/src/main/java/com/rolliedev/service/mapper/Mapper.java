package com.rolliedev.service.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
