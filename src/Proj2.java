import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Proj2 {
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
		
		System.out.println("-- Settings Encrypt -----------");
		System.out.println("KEY:\t " + bytesToHex(key));
		System.out.println("IV:\t " + bytesToHex(IV.getBytes()));
		
		// Create new Blowfish cipher
		SecretKeySpec keySpec = new SecretKeySpec(key, "Blowfish"); 
		Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding"); 
		String out = null;
		
		String secret = message;
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, new javax.crypto.spec.IvParameterSpec(IV.getBytes())); 
		byte[] encoding = cipher.doFinal(secret.getBytes());

		System.out.println("-- Encrypted -----------");
		System.out.println("Base64:\t " + DatatypeConverter.printBase64Binary(encoding));
		System.out.println("HEX:\t " + bytesToHex(encoding));
	}
	
	public static void decrypt(String keyStr, String ivStr, String message) 
			throws Exception {
		// Configuration
		byte[] key	= keyStr.getBytes();
		String IV  	= ivStr;
		
		System.out.println("-- Settings Decrypt -----------");
		System.out.println("KEY:\t " + bytesToHex(key));
		System.out.println("IV:\t " + bytesToHex(IV.getBytes()));
		
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
		System.out.println("HEX:\t " + bytesToHex(finalMessage));
		System.out.println("PLAIN:\t " + new String(finalMessage));
		
	}
	
	public static void main(String[] args) throws Exception {
		
		// SecretKe must be 8 chars long!!!
		
		server Server = new server();
		Server.run();
		
		
//		encrypt("Public Key", "SecretKe", "this is the  unecrypted message here");
//		
//		decrypt("Public Key", "SecretKe", "this is the ENCRYPTED message here");

	}

}
