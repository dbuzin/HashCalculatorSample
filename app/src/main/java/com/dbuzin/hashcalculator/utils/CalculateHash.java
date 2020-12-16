package com.dbuzin.hashcalculator.utils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalculateHash {
    public static Serializable encode(String raw, String algorithm){
        try{
        MessageDigest mDigest = MessageDigest.getInstance(algorithm);
        byte[] hashInBytes = mDigest.digest(raw.getBytes(StandardCharsets.UTF_8));
        StringBuilder mBuilder = new StringBuilder();
        for (byte b : hashInBytes) {
            mBuilder.append(String.format("%02x", b));
        }
        raw = mBuilder.toString();
        }
        catch (NoSuchAlgorithmException exception){
            return exception;
        }
        return raw;
    }
}
