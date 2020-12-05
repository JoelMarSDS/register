package com.cash.register.domain.settings.useful;

public interface InterfaceConverterDTO <B,T>{

    B convertTo(T t);
    T convertBack(B b);
}
