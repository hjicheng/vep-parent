package com.huangjicheng.top.vepcommont.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @Author: huangjicheng
 * @Date: 2020/5/26 23:24
 */

public class AESUtil {
    private final static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     * @param srcData
     * @return
     */
    public static String AES_cbc_encrypt(String srcData) {
        byte[] key = "Ay#g$12(3K=yt*e&".getBytes();
        byte[] iv = "Ay#g$12(3K=yt*e&".getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        byte[] encData = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
            encData = cipher.doFinal(srcData.getBytes());
        } catch (Exception e) {
            logger.error("系统错误:", e);
        }
        return Base64.getEncoder().encodeToString(encData);
    }

    /**
     * 解密
     * @param encData
     * @return
     */
    public static String AES_cbc_decrypt(String encData) {
        byte[] key = "Ay#g$12(3K=yt*e&".getBytes();
        byte[] iv = "Ay#g$12(3K=yt*e&".getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        byte[] decbbdt = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
            decbbdt = cipher.doFinal(Base64.getDecoder().decode(encData));
        } catch (Exception e) {
            logger.error("系统错误:", e);
        }
        return new String(decbbdt);
    }

    public static void main(String[] args) {

        String srcStr = "123456";
        System.out.println(srcStr);

        String encbt = AES_cbc_encrypt(srcStr);
        System.out.println(encbt);
        String decbt = AES_cbc_decrypt(encbt);
        System.out.println(decbt);
    }
}
