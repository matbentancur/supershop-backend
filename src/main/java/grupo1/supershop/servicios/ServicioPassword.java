package grupo1.supershop.servicios;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.mindrot.jbcrypt.BCrypt;

public class ServicioPassword {
    
    private static ServicioPassword instance = null;
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String SECRET_KEY = "emrYoruZKktTVfw5";

    public ServicioPassword() {
    }
    
    public static ServicioPassword getInstance() {
        if (instance == null) {
            instance = new ServicioPassword();
        }
        return instance;
    }
    
    public String hashPassword(String password){
        if(null == password){
            return null;
        }
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public Boolean chequearPasswordHash(String candidato, String hash){
        return BCrypt.checkpw(candidato, hash);
    }
    
    public String getRandomPassword(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
    
    private String encriptarPassword(String password, String key){
	try {
            SecretKeySpec skeyspec = new SecretKeySpec(key.getBytes(),"Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            return new String(Base64.getEncoder().encodeToString(encrypted));
		
	} catch (Exception e) {
            return null;
	}
    }
    
    private String desencriptarPassword(String encryptedPassword, String key){
	try {
            SecretKeySpec skeyspec = new SecretKeySpec(key.getBytes(),"Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decrypted);
		
	} catch (Exception e) {
            return null;
	}
    }
    
    public String encriptarPassword(String password){
        return this.encriptarPassword(password, SECRET_KEY);
    }
    
    public String desencriptarPassword(String encryptedPassword){
        return this.desencriptarPassword(encryptedPassword, SECRET_KEY);
    }
    
}
