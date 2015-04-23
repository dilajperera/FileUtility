package com.crypto.fileutility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * This Class represent the file sender
 * RSA 117 Bytes Ciphers are used. fCiphers refers to a list of FCipher.
 * The provided file is partitioned to 117 Bytes plain texts. 
 * @author Dilaj
 *
 */
public class FileSender {

	private List<byte[]> bFile; //list of byte[]
	private List<FCipher> fCiphers; 

	public FileSender(File file) {
		this.bFile = readFile(file);
	}
	
	public List<FCipher> enctyptFile(Key receiverPublicKey){
		Cipher cipher = FileUtilityConfigurator.getCipher();
		try {
			cipher.init(Cipher.ENCRYPT_MODE, receiverPublicKey);
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		}
		
		System.out.println( "\nFileSender : Start encryption" );
		
		fCiphers = new ArrayList();
		for (byte[] plainText : bFile) {
			try {
				byte[] cipherText = cipher.doFinal(plainText);
				byte[] digest = DigestGenarator.getDigest(plainText);
				fCiphers.add(new FCipher(cipherText,digest));
				System.out.println("FileSender : Plain Text : " + new String(plainText));
				System.out.println("FileSender : Cipher Text :" + new String(cipherText));
				System.out.println( "\n--------------------------------------------------------------" );
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
		}
		
		
	    System.out.println( "FileSender : Finish encryption.." );  
	    return fCiphers;
	}
	
	/**
	 * read the File
	 * @param file - given file
	 * @return - list of byte[] - plain texts
	 */
	private List<byte[]> readFile(File file){
		
		bFile = new ArrayList<>();
		byte[] _bFile = new byte[(int) file.length()];
		try (FileInputStream fileInputStream = new FileInputStream(file)){
			fileInputStream.read(_bFile);
		}  catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int RSALimit = 117;
		int cipherCount = (int) (_bFile.length/RSALimit);
		
		int start = 0;
		int end = 0;
		for(int blockIndex = 0 ; blockIndex < cipherCount; blockIndex++){
			end = (blockIndex + 1) * RSALimit;		
			bFile.add(Arrays.copyOfRange(_bFile, start, end));
			start = end;		
		}
		
		if(cipherCount * RSALimit < _bFile.length){
			bFile.add(Arrays.copyOfRange(_bFile, end, _bFile.length));
		}
		
		return bFile;
	}

}
