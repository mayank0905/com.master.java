package com.hsbc.qe.common.utils;
import java.math.BigInteger;
import java.security.SecureRandom;

public class GeneralUtils {

    public static String generateRandomHexToken(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16); //hex encoding
    }
}
