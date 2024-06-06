package com.ais_r_enhanced.ais.r.enhanced.Utility;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryptor {
    private  SecretKeySpec secretKeySpec;
    private  IvParameterSpec ivSpec;


    public Encryptor() throws Exception {
        String secretKeyString = "0123456789abcdef";
        String ivString = "RandomInitVector";
        // Initialize secret key
        byte[] secretKeyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);
        secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");

        // Initialize IV
        byte[] ivBytes = ivString.getBytes(StandardCharsets.UTF_8);
        ivSpec = new IvParameterSpec(ivBytes);
    }

    public  String encryptText(String plainText) throws Exception {
        // Encryption
        Cipher cipherEncrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
        byte[] encryptedBytes = cipherEncrypt.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public  String decryptText(String encryptedText) throws Exception {
        // Decryption
        Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipherDecrypt.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
