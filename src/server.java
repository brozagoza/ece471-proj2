import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class server {
	
	public void run() throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		String input = "";
		char action = ' ';
		
		System.out.println();
		initMessage();
		while(scanner.hasNextLine())
		{
			
			input = scanner.nextLine();
			
			
			if(input.length() > 1)
				System.out.println("Please enter 1 appropriate character");
			else if(input.equals("Q"))
				break;
			else if(!(input.equals("C") || input.equals("E") || input.equals("D")))
				System.out.println("Please enter 1 appropriate character");
			else
			{
				if(input.equals("C"))
				{
					System.out.println("Enter the name of the recipient to be:");
					String name = scanner.nextLine();
					createUser(name);
					System.out.println("User Created!!");
				}
				else if(input.equals("E"))
				{
					System.out.println("Enter the name of the recipient:");
					String name = scanner.nextLine();
					String message = "";
					
					System.out.println("Enter the message to encrypt:");
					message = scanner.nextLine();
					String secretKey = getSecretKey(name);
					
					//TODO encrypt the stuffs here
					if (secretKey == null)
						System.out.println("Did not find the user in our records, returning to start");
					else
						encrypt(name, secretKey, message);
				
				}
				else// "D"
				{
					System.out.println("Enter the name of the recipient:");
					String name = scanner.nextLine();
					String message = "";
					String sk = "";
					
					System.out.println("Enter the secret key:");
					sk = scanner.nextLine();
											
					System.out.println("Enter the message to decrypt:");
					message = scanner.nextLine();
					
					//TODO decrypt the stuffs here
					if (!sk.equals(getSecretKey(name))) {
						System.out.println("Did not find the user or secretkey does not match our records, returning to start");
					} else
						decrypt(name, sk, message);
					
				}
			}
			System.out.println();
			initMessage();	
		}
		
	}
	
	public boolean checkUsername(String name)
	{
		//TODO check for the user name
		return true;
	}
	
	public void initMessage()
	{
		String str = "";
		
		str = "Hello welcome to SantaGoza Public Key Distribution";
		System.out.println(str);
		
		str = "Enter 'C' to create a public and secret key pair";
		System.out.println(str);
		
		str = "Enter 'E' to encrypt a message";
		System.out.println(str);
		
		str = "Enter 'D' to decrypt a message";
		System.out.println(str);
		
		str = "Enter 'Q' to quit program";
		System.out.println(str);
	}
	
final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	public static void encrypt(String keyStr, String ivStr, String message)
			throws Exception {
		// Configuration
		byte[] key	= keyStr.getBytes();
		String IV  	= ivStr;
		
		//System.out.println("-- Settings Encrypt -----------");
		//System.out.println("KEY:\t " + bytesToHex(key));
		//System.out.println("IV:\t " + bytesToHex(IV.getBytes()));
		
		// Create new Blowfish cipher
		SecretKeySpec keySpec = new SecretKeySpec(key, "Blowfish"); 
		Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding"); 
		String out = null;
		
		String secret = message;
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, new javax.crypto.spec.IvParameterSpec(IV.getBytes())); 
		byte[] encoding = cipher.doFinal(secret.getBytes());

		System.out.println("-- Encrypted -----------");
		System.out.println("Base64:\t " + DatatypeConverter.printBase64Binary(encoding));
		//System.out.println("HEX:\t " + bytesToHex(encoding));
		System.out.println();
	}
	
	public static void decrypt(String keyStr, String ivStr, String message) 
			throws Exception {
		// Configuration
		byte[] key	= keyStr.getBytes();
		String IV  	= ivStr;
		
		//System.out.println("-- Settings Decrypt -----------");
		//System.out.println("KEY:\t " + bytesToHex(key));
		//System.out.println("IV:\t " + bytesToHex(IV.getBytes()));
		
		// Create new Blowfish cipher
		SecretKeySpec keySpec = new SecretKeySpec(key, "Blowfish"); 
		Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding"); 
		String out = null;
		
		// Decode Base64
		byte[] ciphertext = DatatypeConverter.parseBase64Binary(message);

		// Decrypt 
		cipher.init(Cipher.DECRYPT_MODE, keySpec, new javax.crypto.spec.IvParameterSpec(IV.getBytes()));
		byte[] finalMessage = cipher.doFinal(ciphertext);

		System.out.println("-- Decrypted -----------");
		//System.out.println("HEX:\t " + bytesToHex(finalMessage));
		System.out.println("MESSAGE:\t " + new String(finalMessage));
		System.out.println();
	}
	
	private String getSecretKey(String name) {
		File file = new File("db.txt");
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> secretKeys = new ArrayList<>();
		
		try {
	        Scanner sc = new Scanner(file);

	        while (sc.hasNextLine()) {
		        	String string = sc.nextLine();
		        	String[] parts = string.split(",");
		        names.add(parts[0]);
		        secretKeys.add(parts[1]);
	        }
	        sc.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
		int i = 0;
		
		for (i = 0; i < names.size(); i++)
			if (names.get(i).equals(name))
				break;
		
		if (i >= names.size())
			return null;
		
		return secretKeys.get(i);
	}
	
	private void createUser(String name) {
		String sK = randomAlphaNumeric(8);
		System.out.println("Your secret key: "+sK);
		System.out.println();
		
		BufferedWriter bw = null;

	      try {
	         // APPEND MODE SET HERE
	         bw = new BufferedWriter(new FileWriter("db.txt", true));
	         //bw.newLine();
	         bw.write(name);
			 bw.write(",");
			 bw.write(sK);
			 bw.newLine();
			 bw.flush();
		      } catch (IOException ioe) {
			 ioe.printStackTrace();
		      } finally {                       // always close the file
			 if (bw != null) try {
			    bw.close();
			 } catch (IOException ioe2) {
			    // just ignore it
			 }
		   } // end try/catch/finally
		
	}
	

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public String randomAlphaNumeric(int count) {
	StringBuilder builder = new StringBuilder();
	while (count-- != 0) {
	int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
	builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	}
	return builder.toString();
	}
	
}