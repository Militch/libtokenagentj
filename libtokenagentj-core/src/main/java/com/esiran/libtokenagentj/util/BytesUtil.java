package com.esiran.libtokenagentj.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class BytesUtil {
    private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.UTF_8);
    public static byte[] hex2bytes(String hex){
        if (hex == null){ return null; }
        if (hex.length() >= 2){
            String prefix = hex.substring(0,2);
            if (prefix.equals("0x")){
                hex = hex.substring(2);
            }
        }
        if (hex.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            int firstDigit = Character.digit(hex.charAt(i), 16);
            int secondDigit = Character.digit(hex.charAt(i+1), 16);
            bytes[i / 2] = (byte) ((firstDigit << 4) + secondDigit);
        }
        return bytes;
    }
    public static String bytes2hex(byte[] bytes){
        return bytes2hex(bytes, false);
    }
    public static String bytes2hex(byte[] bytes, boolean prefix){
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        String hex = new String(hexChars, StandardCharsets.UTF_8);
        if (prefix){
            hex = "0x" + hex;
        }
        return hex;
    }
    public static byte[] random(int len){
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.generateSeed(len);
    }
}
