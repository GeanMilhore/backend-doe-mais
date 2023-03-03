package com.ownproject.doemais.utils.data;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateUtil {

    public static LocalDateTime dataDeHoje(){
        return LocalDateTime.now();
    }
}
