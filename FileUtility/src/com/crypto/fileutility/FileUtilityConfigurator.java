package com.crypto.fileutility;

import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 * Configuration Class of the file Utility.
 * RSA/ECB/PKCS1Padding cipher is used.
 * RSA algorithm is used for the key generation.
 * @author Dilaj
 *
 */
public class FileUtilityConfigurator {

	private static Cipher cipher;
	private static KeyPairGenerator keyGenerator;
	public static final String MSG_INTEGRITY_COMPROMISED = "Error : The file integrity has been compromised"; 
	public static final String MSG_CONFIDENTIALTY_COMPROMISED = "Error : the file has been encrypted using wrong public key"; 
	
	public static Cipher getCipher(){
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return cipher;	
	}
	
	public static KeyPairGenerator getKeyGenerator() {
		try {
			keyGenerator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyGenerator.initialize(1024);
		return keyGenerator;
	}
	
	public static MessageDigest getMessageDigest() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
