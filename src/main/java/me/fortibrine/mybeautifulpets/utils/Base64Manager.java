package me.fortibrine.mybeautifulpets.utils;

import java.util.Base64;

public class Base64Manager {

    private Base64.Encoder encoder = Base64.getEncoder();
    private Base64.Decoder decoder = Base64.getDecoder();

    public String encode(String src) {
        return encoder.encodeToString(src.getBytes());
    }

    public String decode(String src) {
        return new String(decoder.decode(src));
    }

}
