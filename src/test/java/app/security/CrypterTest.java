package app.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class CrypterTest {
    String s ;
    @BeforeEach
    public void initObjects(){
        s=new String("radek");
    }

    @Test
    void crypt() {
        String password = new String("radek");
        String passwordRepeat = "Radek";
        String  newPassword = "AntekMonika";
        try {
            String crypt = Crypter.crypt(password);
            String crypt1 = Crypter.crypt(passwordRepeat);
            String crypt2 = Crypter.crypt(newPassword);
            String crypt3 = Crypter.crypt(s);
            assertArrayEquals(crypt.getBytes(),crypt3.getBytes());
            assertEquals(s,password);
            assertFalse(s==password);
            assertFalse(crypt.equals(crypt1));
            assertFalse(crypt1.equals(crypt2));
            assertTrue(crypt3.equals(crypt));
            assertTrue(crypt.length()==64);
            assertTrue(crypt1.length()==64);
            assertTrue(crypt2.length()==64);
            assertTrue(crypt3.length()==64);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}