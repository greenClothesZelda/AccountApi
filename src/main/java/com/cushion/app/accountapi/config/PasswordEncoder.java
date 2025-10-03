package com.cushion.app.accountapi.config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncoder {
    private static final int SALT_LENGTH = 16;

    public String encode(String rawPassword) {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hash = hashWithSalt(rawPassword, salt);
        return saltBase64 + "$" + hash;
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split("\\$");
        if (parts.length != 2) return false;
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String hash = hashWithSalt(rawPassword, salt);
        return hash.equals(parts[1]);
    }

    private String hashWithSalt(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashed = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException("Hashing error", e);
        }
    }
}
