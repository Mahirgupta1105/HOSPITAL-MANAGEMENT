package com.hospital.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordUtil {
    private static final Logger LOGGER = Logger.getLogger(PasswordUtil.class.getName());
    private static final int SALT_LENGTH = 32; // 256 bits
    private static final int ITERATIONS = 100000;
    private static final int KEY_LENGTH = 512; // 512 bits
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    
    /**
     * Hashes a password with a randomly generated salt
     * @param password the password to hash
     * @return a string containing the iterations, salt, and hashed password, separated by ':'
     */
    public static String hashPassword(String password) {
        try {
            // Generate a random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            
            // Hash the password
            byte[] hash = hashPassword(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            
            // Encode salt and hash to Base64 for storage
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);
            
            // Format: iterations:salt:hash
            return String.format("%d:%s:%s", ITERATIONS, saltBase64, hashBase64);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error hashing password", e);
            throw new RuntimeException("Failed to hash password", e);
        }
    }
    
    /**
     * Verifies a password against a stored hash
     * @param password the password to verify
     * @param storedHash the stored hash to verify against
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Split the stored hash into its components
            String[] parts = storedHash.split(":");
            if (parts.length != 3) {
                return false; // Invalid format
            }
            
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[2]);
            
            // Hash the provided password with the same parameters
            byte[] actualHash = hashPassword(password.toCharArray(), salt, iterations, expectedHash.length * 8);
            
            // Compare the hashes in constant time to prevent timing attacks
            return constantTimeEquals(actualHash, expectedHash);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error verifying password", e);
            return false;
        }
    }
    
    private static byte[] hashPassword(final char[] password, final byte[] salt, 
                                     final int iterations, final int keyLength) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    /**
     * Compares two byte arrays in constant time to prevent timing attacks
     */
    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
