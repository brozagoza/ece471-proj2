import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
