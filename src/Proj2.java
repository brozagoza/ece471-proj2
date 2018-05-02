import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Proj2 {
	
	public static void main(String[] args) throws Exception {
		
		// SecretKe must be 8 chars long!!!
		
		server Server = new server();
		Server.run();
		
		
//		encrypt("Public Key", "SecretKe", "this is the  unecrypted message here");
//		
//		decrypt("Public Key", "SecretKe", "this is the ENCRYPTED message here");

	}

}
