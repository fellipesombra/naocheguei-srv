package br.com.onmyway.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;


public class CryptoUtil {

    private static byte[] getSalt() throws NoSuchAlgorithmException {
	SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	byte[] salt = new byte[32];
	sr.nextBytes(salt);
	return salt;
    }
    
    public static String generateSalt() throws NoSuchAlgorithmException{
	return byteToString(getSalt());
    }
    
    public static String byteToString(byte[] input) {
        return Base64.encodeBase64String(input);
    }
    
    public static byte[] stringToByte(String input) {
        return Base64.decodeBase64(input);
    }
    
    public static String getSecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
