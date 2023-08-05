package com.bingchong.cipher;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.bingchong.utils.Base64;


public class RSA {

    final static String modulus = "44akle7U4KtfW9VZAgsETDdiz2Igzg+i7a/gJWtJnQ9ahAcOIujcZfT/xH/AAB4tRSdzqPQGP43s2cfpcT5/LkhXQaqZPYaJkv3CUdIKRX3Eir+vvNHk6vtKcgdGh3ZFwk8tU7NdXKCwDYJPAyOpx9QtclrgtxRahoWlITkgf30=";
    final static String exponent = "AQAB";
	
	public static byte[] encryptWithPubKey(byte[] input, PublicKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(input);
    }
    
    public static String encryptString (String plain_text) throws Exception {
    	
    	byte[] modulusBytes = Base64.decode(modulus);
    	byte[] exponentBytes = Base64.decode(exponent);
    	
    	BigInteger modulus = new BigInteger( 1,modulusBytes );                
    	BigInteger exponent = new BigInteger( 1,exponentBytes);

    	RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
    	KeyFactory fact = KeyFactory.getInstance("RSA");
    	PublicKey pubKey = fact.generatePublic(rsaPubKey);   
    	
		byte[] cipherText1 = encryptWithPubKey(plain_text.getBytes("UTF-8"),pubKey);
		String result1 = Base64.encodeBytes(cipherText1);	           
        return result1;
    }
    
    public static String encryptString (Double plain_value) throws Exception {
    	
    	byte[] modulusBytes = Base64.decode(modulus);
    	byte[] exponentBytes = Base64.decode(exponent);
    	
    	BigInteger modulus = new BigInteger( 1,modulusBytes );                
    	BigInteger exponent = new BigInteger( 1,exponentBytes);

    	RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
    	KeyFactory fact = KeyFactory.getInstance("RSA");
    	PublicKey pubKey = fact.generatePublic(rsaPubKey);   
    	
    	String plain_text =  Double.toString(plain_value);
    	
		byte[] cipherText1 = encryptWithPubKey(plain_text.getBytes("UTF-8"),pubKey);
		String result1 = Base64.encodeBytes(cipherText1);	           
        return result1;
    }
}
