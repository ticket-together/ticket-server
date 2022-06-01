package com.tickettogether.global.config.security.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import org.apache.commons.codec.binary.Base64;

public class AES128 {
    private static final int BLOCK_SIZE = 16;
    private static final int MINIMUM_ENCRYPT_KEY_SIZE = 16;
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private final byte[] encryptKeyBytes;
    private final Key keySpec;

    public AES128(String encryptKey) {
        if(encryptKey == null || encryptKey.length() < MINIMUM_ENCRYPT_KEY_SIZE) {
            throw new SecurityException();
        }
        encryptKeyBytes = encryptKey.substring(0, MINIMUM_ENCRYPT_KEY_SIZE).getBytes(StandardCharsets.UTF_8);
        this.keySpec = new SecretKeySpec(encryptKeyBytes, "AES");
    }

    public String encrypt(String message) throws Exception {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes = createRandomIv();

        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));

        byte[] inputBytes = new byte[BLOCK_SIZE + messageBytes.length];
        System.arraycopy(ivBytes, 0, inputBytes, 0, BLOCK_SIZE);
        System.arraycopy(messageBytes, 0, inputBytes, BLOCK_SIZE, messageBytes.length);
        return Base64.encodeBase64String(cipher.doFinal(inputBytes));
    }

    private byte[] createRandomIv(){
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[BLOCK_SIZE];
        random.nextBytes(ivBytes);
        return ivBytes;
    }
}