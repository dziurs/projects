package app.security;

import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypter {

    private Crypter() {
    }
    private static MessageDigest messageDigest;

    public static String crypt(String s) throws BuildingSalesAppException {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest = messageDigest.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : digest) {
                String string = Integer.toHexString(0xff & b);
                if (string.length() == 1) builder.append('0');
                builder.append(string);
            }
            return builder.toString();
        }catch (NoSuchAlgorithmException ex){
            throw new GeneralApplicationException(GeneralApplicationException.CRYPTER, ex);
        }
    }
}
