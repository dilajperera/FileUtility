package com.crypto.fileutility;


import java.security.MessageDigest;
/**
 * This class is used to generate the digest for a plain text
 * and check the integrity of a plain text
 * SHA-256 algorithm is used, since large file sizes are used
 * @author Dilaj
 *
 */
public class DigestGenarator {

	private static MessageDigest messageDigest;
	private static byte [] digest;

	private static void createDigest(byte[]  message) {
		messageDigest = FileUtilityConfigurator.getMessageDigest();
		messageDigest.update(message);
	    digest = new byte[messageDigest.getDigestLength()];
	    digest = messageDigest.digest(); 
	}

	public static byte[] getDigest(byte[]  message) {
		createDigest(message);
		return digest;
	}
}
