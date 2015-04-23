package com.crypto.fileutility;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		System.out.print("******\nThe file should be placed in the same directory which contains the jar file******");
		System.out.print("******\n e.g.: a sample file has added : testFile.txt ******");
		System.out.print("\nEnter the file  name: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		try {
			input = br.readLine();
		} catch (IOException ioe) {
			System.out.println("\nIO error trying to read file name!");
			System.exit(1);
		}

		if (input != null) {
			File file = new File(input);
			FileSender sender = new FileSender(file);
			FileReceiver receiver = new FileReceiver();

			List<FCipher> fCiphers = sender
					.enctyptFile(receiver.getPublicKey());
			receiver.decryptFile(fCiphers);

			System.out.print("\nEnter any key to exit.. ");
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		
	}

}
