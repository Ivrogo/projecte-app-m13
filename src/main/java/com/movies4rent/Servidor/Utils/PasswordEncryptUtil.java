package com.movies4rent.Servidor.Utils;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class PasswordEncryptUtil {

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 10000;
    private static final int KEY_SIZE = 256;
    private static final String SECRET_KEY = "mySecretKey"; // Clave secreta utilizada para el cifrado

    public static String encryptPassword(String password) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), getSalt(), ITERATIONS, KEY_SIZE);
            SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ENCRYPTION_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedPasswordBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedPasswordBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    public static String decryptPassword(String encryptedPassword) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), getSalt(), ITERATIONS, KEY_SIZE);
            SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ENCRYPTION_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedPasswordBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedPasswordBytes = cipher.doFinal(encryptedPasswordBytes);
            return new String(decryptedPasswordBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar la contraseña", e);
        }
    }

    private static byte[] getSalt() {
        // Puedes generar y almacenar una sal única para cada usuario en la base de datos
        // y utilizarla en la generación de la clave secreta.
        // En este ejemplo, se utiliza una sal fija para simplificar.
        return "mySalt".getBytes(StandardCharsets.UTF_8);
    }
}

