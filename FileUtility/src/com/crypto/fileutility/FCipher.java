package com.crypto.fileutility;

/**
 * File sender send the list of FCiphers to the receiver
 * @author Dilaj
 *
 */
public class FCipher {
	
	private byte[] cipherText; // a cipher text related to a plain text
	private byte [] digest; //digest of the plain text
	
	public FCipher(byte[] cipherText,byte [] digest) {
		this.cipherText = cipherText;
		this.digest = digest;
	}

	/**
	 * 
	 * @return cipher text as a byte[]
	 */
	public byte[] getCipherText() {
		return cipherText;
	}
	
	/**
	 * return digest as a byte[]
	 * @return
	 */
	public byte[] getDigest() {
		return digest;
	}
	
}
