package com.ecmoho.common.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.ssl.PKCS8Key;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSAUtil {
    /**
     * 方法描述：初始化私钥
     *
     */
    public static PrivateKey initPrivateKey(String path, String pwd) {
        InputStream in = null;
        try {

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            File file = new File(path);
            byte[] b = null;

            in = new FileInputStream(file);

            PKCS8Key pkcs8 = new PKCS8Key(in, pwd.toCharArray());

            b = pkcs8.getDecryptedBytes();

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b);

            PrivateKey prikey = keyFactory.generatePrivate(keySpec);

            return prikey;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Runtime.getRuntime().exit(-100);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Runtime.getRuntime().exit(-100);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            Runtime.getRuntime().exit(-100);
        } catch (IOException e) {
            e.printStackTrace();
            Runtime.getRuntime().exit(-100);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return null;
    }

    /**
     * 方法描述：初始化公钥
     *
     */
    public static PublicKey initPublicKey(String str) {
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance("RSA");

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(str));

            PublicKey pubkey = keyFactory.generatePublic(keySpec);

            return pubkey;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Runtime.getRuntime().exit(-100);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            Runtime.getRuntime().exit(-100);
        }

        return null;

    }

    /**
     * 私钥签名
     */
    public static String signPrivateKey(String content, PrivateKey key, String charsetSet) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        PrivateKey prikey = key;

        Signature signature = Signature.getInstance("SHA1WithRSA");

        signature.initSign(prikey);

        signature.update(content.getBytes(charsetSet));

        byte[] signBytes = signature.sign();

        String sign = new String(Base64.encodeBase64(signBytes));
        return sign;
    }

    /**
     * 私钥签名
     */
    public static String signPrivateKey(String content, PrivateKey key) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        return signPrivateKey(content,key,"UTF-8");
    }

    /**
     * 方法描述：验签
     */
    public static boolean verifyPublicKey(String content, String sign, PublicKey key, String charsetSet) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        PublicKey pubkey = key;

        byte[] signed = Base64.decodeBase64(sign.getBytes());

        Signature signature = Signature.getInstance("SHA1WithRSA");

        signature.initVerify(pubkey);

        signature.update(content.getBytes(charsetSet));

        return signature.verify(signed);
    }


    /**
     * 方法描述：验签
     *
     */
    public static boolean verifyPublicKey(String content, String sign, PublicKey key) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        PublicKey pubkey = key;

        byte[] signed = Base64.decodeBase64(sign.getBytes());

        Signature signature = Signature.getInstance("SHA1WithRSA");

        signature.initVerify(pubkey);

        signature.update(content.getBytes("UTF-8"));

        return signature.verify(signed);
    }

    /**
     * 方法描述：验签
     */
    public static boolean verifyPublicKey(String content, String sign, String pkey) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        PublicKey pubkey = RSAUtil.initPublicKey(pkey);
        byte[] signed = Base64.decodeBase64(sign.getBytes());

        Signature signature = Signature.getInstance("SHA1WithRSA");

        signature.initVerify(pubkey);

        signature.update(content.getBytes("UTF-8"));

        return signature.verify(signed);
    }

    /**
     * 方法描述：验签
     */
    public static boolean verifyPublicKey(String content, String sign, String key, String charsetSet) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        PublicKey pubkey = RSAUtil.initPublicKey(key);

        byte[] signed = Base64.decodeBase64(sign.getBytes());

        Signature signature = Signature.getInstance("SHA1WithRSA");

        signature.initVerify(pubkey);

        signature.update(content.getBytes(charsetSet));

        return signature.verify(signed);
    }

    /**
     * 加密
     *
     * @param publicKey
     *            公钥
     * @param data
     *            数据
     * @return 加密后的数据
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) {
        Assert.notNull(publicKey);
        Assert.notNull(data);
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密
     *
     * @param publicKey
     *            公钥
     * @param text
     *            字符串
     *
     * @return Base64编码字符串
     */
    public static String encrypt(PublicKey publicKey, String text) {
        Assert.notNull(publicKey);
        Assert.notNull(text);
        byte[] data = encrypt(publicKey, text.getBytes());
        return data != null ? Base64.encodeBase64String(data) : null;
    }

    /**
     * 解密
     *
     * @param privateKey
     *            私钥
     * @param data
     *            数据
     * @return 解密后的数据
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
        Assert.notNull(privateKey);
        Assert.notNull(data);
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param privateKey
     *            私钥
     * @param text
     *            Base64编码字符串
     * @return 解密后的数据
     */
    public static String decrypt(PrivateKey privateKey, String text) {
        Assert.notNull(privateKey);
        Assert.notNull(text);
        byte[] data = decrypt(privateKey, Base64.decodeBase64(text));
        return data != null ? new String(data) : null;
    }

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException {
        PrivateKey privateKey = initPrivateKey("E:\\product\\bdsh\\ecmoho_private.key", "bd123456");
        PublicKey pubkey = RSAUtil.initPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDAFZu2LW/J5WiUSeApN7KaiqdL eoy601i3FlpQkdOIcY7Zkk7y2o01L3PoA7qBufBaejUNuBQafNH9nA/3PllaGB1z BcDUF+R1/DdFDq4Noyl017dxcM6bbY59dm8tFKlXq+qrUSek6l76dzIp6j45eHnj q3S9IXZqTALKACi0uwIDAQAB");
        String content = "userName=111&pwd=1832973427382378947129837849123";
        String sing = signPrivateKey(content, privateKey);
        System.out.println(verifyPublicKey(content, sing, pubkey));

        String sing2 = RSAUtil.encrypt(pubkey, content);
        System.out.println("共钥加密:"+sing2);
        System.out.println("私钥解密:"+RSAUtil.decrypt(privateKey,sing2));

    }
}
