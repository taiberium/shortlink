package com.shortlink.core.util;

public class IdConverter {

    private static final String ALLOWED_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
    private static final BaseNEncoder ENCODER = new BaseNEncoder(ALLOWED_STRING);

    public static String encode(long id){
        return ENCODER.encode(id);
    }

    public static long decode(String url){
        return ENCODER.decode(url);
    }
}
