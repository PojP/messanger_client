package cl.client.cipher.DES;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DES
{
/*
    private static SecretKey genKey(String password)
    {
        System.out.println(password);
        byte[] decodedKey = Base64.getDecoder().decode(password);
        SecretKey key = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
        System.out.println(key);
        return key;
    }
    public static String Cipher(String data, String password)
    {

        //setting encryption mode
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, genKey(password));
            byte[] encryptedMessage = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedMessage);
        } catch (IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static String Decipher(String data, String password)
    {
        //setting decryption mode
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, genKey(password));
            byte[] clearMessage = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(clearMessage);
        } catch (IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException e){
            //e.printStackTrace();
            return null;
        }
    }



*/
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    private static final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
    private static final String IV = "Af3fp==-gvs'/.sg=]'g";
    private static final String SALT = "12kld??,m-|ggdsg'$#d";
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    // return a base64 encoded AES encrypted text
    public static String Cipher(String pText, String password) throws Exception {

        // 16 bytes salt
        byte[] salt = SALT.getBytes(StandardCharsets.UTF_8);

        // GCM recommended 12 bytes iv?
        byte[] iv = IV.getBytes(StandardCharsets.UTF_8);

        // secret key from password
        SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        // ASE-GCM needs GCMParameterSpec
        cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] cipherText = cipher.doFinal(pText.getBytes(StandardCharsets.UTF_8));

        // prefix IV and Salt to cipher text
        byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                .put(iv)
                .put(salt)
                .put(cipherText)
                .array();

        // string representation, base64, send this string to other for decryption.
        return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);

    }

    // we need the same password, salt and iv to decrypt it
    public static String Decipher(String cText, String password) throws Exception {

        byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));
        System.out.println(decode);
        // get back the iv and salt from the cipher text
        ByteBuffer bb = ByteBuffer.wrap(decode);

        byte[] iv = IV.getBytes(StandardCharsets.UTF_8);
        bb.get(iv);

        byte[] salt = SALT.getBytes(StandardCharsets.UTF_8);
        bb.get(salt);

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        // get back the aes key from the same password and salt
        SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        try {
            byte[] plainText = cipher.doFinal(cipherText);
            bb.clear();
            return new String(plainText, UTF_8);
        }
        catch(AEADBadTagException e)
        {
            System.out.println("Bad pwd :(");
            bb.clear();
            return "this message are not encrypred";
        }

    }

}
class CryptoUtils
{
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    // AES secret key
    public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    // Password derived AES 256 bits secret key
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        // iterationCount = 65536
        // keyLength = 256
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;

    }

    // hex representation
    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // print hex with block size split
    public static String hexWithBlockSize(byte[] bytes, int blockSize) {

        String hex = hex(bytes);

        // one hex = 2 chars
        blockSize = blockSize * 2;

        // better idea how to print this?
        List result = new ArrayList<>();
        int index = 0;
        while (index < hex.length()) {
            result.add(hex.substring(index, Math.min(index + blockSize, hex.length())));
            index += blockSize;
        }

        return result.toString();

    }

}