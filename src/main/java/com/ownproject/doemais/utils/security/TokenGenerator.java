package com.ownproject.doemais.utils.security;

import java.security.SecureRandom;

public class TokenGenerator {

    private static final int BYTE_LENGTH = 16; // 16 bytes = 128 bits
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateToken() {
        byte[] randomBytes = new byte[BYTE_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return bytesToHex(randomBytes);
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}