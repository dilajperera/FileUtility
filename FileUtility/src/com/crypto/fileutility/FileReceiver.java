package com.crypto.fileutility;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * This class represents the receiver. The public key will be exposed to
 * the others while private key will be secret.
 * RSA getKeyGenerator is used to generate the key pairs.
 * RSA/ECB/PKCS1Padding trasformation is used for the ciphers.
 * @author Dilaj
 *
 */
public class FileReceiver {
	
	private Key publicKey;
	private Key privateKey;
	private byte[] fileData;
	
	
	public FileReceiver() {		
		generateKeys();
	}
	
	/**
	 * generates the private/public key pairs
	 */
	private void generateKeys(){
	    KeyPair key = FileUtilityConfigurator.getKeyGenerator().generateKeyPair();
	    publicKey = key.getPublic();
	    privateKey = key.getPrivate();
	    System.out.println( "\nFileReceiver : RSA keys -> Private : "+key.getPrivate() );
	    System.out.println( "\nFileReceiver : RSA keys -> Public : "+key.getPublic() );
	}
	
	/**
	 * decrypt the file 
	 * @param fCiphers list of fCiphers
	 * fcipher have cipher text and digest
	 * RSA-private key is used to decrypt the cipher text
	 * digest is used to check the integrity of cipher text related plain text
	 */
	public void decryptFile(List<FCipher> fCiphers){
		List<byte[]> bFile = new ArrayList();
		System.out.println( "\nFileReceiver : Start decryption" );
		for(FCipher fCipher : fCiphers){
			byte[] plainText = decryptFile(fCipher.getCipherText());
			checkIntegrity(fCipher.getDigest(),plainText); // check the integrity
			bFile.add(plainText);
		}
		System.out.println( "FileReceiver : Finish decryption " );
		prepareFileData(bFile);  //decrpted list of plain texts are used to generate file as a single byte array
		
		try {
			System.out.println("FileReceiver : Result : " + new String(fileData, "UTF8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * check whether integrity is compromised or not
	 * @param receivedDigest cipher text related digest
	 * @param plainText plain text related to cipher
	 * @return
	 */
	private boolean checkIntegrity(byte[] receivedDigest, byte[] plainText){
		byte[] originalDigest = DigestGenarator.getDigest(plainText);
		boolean integrityCheckstate = Arrays.equals(receivedDigest, originalDigest); //if two digest are not equal -> integrity has compromised 
		if(!integrityCheckstate){
			System.out.println(FileUtilityConfigurator.MSG_INTEGRITY_COMPROMISED);
			//System.exit(0);
		}
		return integrityCheckstate;
	}
	
	/**
	 * decrypt the single cipher
	 * @param cipherText
	 * @return
	 */
	private byte[] decryptFile(byte[] cipherText){
		Cipher cipher = FileUtilityConfigurator.getCipher();
		// decrypt the ciphertext using the private key
	    try {
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		}
	    
	    byte[] newPlainText = null;
		try {
			newPlainText = cipher.doFinal(cipherText);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	    return newPlainText;
	}
	
	/**
	 * prepare the file data (plain text) 
	 * @param bFile - list of plain texts related to the original file
	 */
	private void prepareFileData(List<byte[]> bFile){
		int bufferLength = 0;
		for(byte[] plainText : bFile){
			bufferLength += plainText.length;
		}
		
		for(byte[] plainText : bFile){
			bufferLength += plainText.length;
		}
		fileData = new byte[bufferLength];
		
		int start = 0;
		int end = 0;
		for (int j = 0; j < bFile.size(); j++) {
			byte[] _bData = bFile.get(j);
			end += _bData.length;
			int temp = 0;
			for (int i = start; i < end; i++) {
				fileData[i] = _bData[temp++];
			}
			start = end;
		}
	}
	
	/**
	 * @return public key 
	 */
	public Key getPublicKey() {
		return publicKey;
	}
	
}
