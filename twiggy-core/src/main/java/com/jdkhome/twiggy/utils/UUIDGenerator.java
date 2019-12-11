package com.jdkhome.twiggy.utils;

import java.util.UUID;

public class UUIDGenerator {

    public static String get() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}