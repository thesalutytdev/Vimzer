package org.thesalutyt.vimzer.api.features.lock;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {
    private static final String SHA_256 = "SHA-256";

    public static String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            byte[] encodedPassword = digest.digest(password.getBytes());
            return bytesToHex(encodedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public static boolean verifyPassword(String encodedPassword, String password) {
        return encodePassword(password).equals(encodedPassword);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
