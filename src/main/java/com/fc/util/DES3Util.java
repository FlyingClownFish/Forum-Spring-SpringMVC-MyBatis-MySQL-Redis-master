package com.fc.util;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * copyright CITS 2015
 * 
 * @date 2015-02-27
 * @author Yuchengye
 * @version V0.1
 * @brief AES加密工具类
 * @since 0.1
 */
public final class DES3Util {
    // 定义加密算法
    private static final String ALGORITHM = "desede";

    private DES3Util() {
        
    }

    /**
     * 数据加密加密
     * 
     * @param data
     * @return
     */
    public static String dataEncrypt(String data, String password) {
        if (StringUtils.isEmpty(data)) {
            return data;
        }
        try {
            data = java.net.URLEncoder.encode(data, "UTF-8");
            return DES3Util.parseByte2HexStr(DES3Util.encrypt(data, password));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 加密数据
     * 
     * @param para 要加密的数据
     * @param deskey 密钥
     * @return 加密后的密文
     * @throws UnsupportedEncodingException
     */
    public static byte[] encrypt(String content, String password) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(password.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory
                    .getInstance(ALGORITHM);
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(MyConstant.LOGIN_CRYPT_IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] result = cipher.doFinal(content.getBytes("utf-8"));

            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * 
     * @param content 密文
     * @param password 密钥
     * @return 解密后的内容
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(password.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory
                    .getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(MyConstant.LOGIN_CRYPT_IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return 转换后结果
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    
    /**
     * MD5的加密秘钥为!CitsKpod 截取
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static void getLoginCryptpassword() throws NoSuchAlgorithmException, UnsupportedEncodingException{
    		String result=MD5.MD5_32bit(MyConstant.LOGIN_CRYPT_DEKEY);
    		 if (result != null && result.length() > 24) {
                 MyConstant.LOGIN_CRYPT_IV = result.substring(3, 11);
                 MyConstant.LOGIN_CRYPT_PASSWORD = result.substring(0, 24);
             } else {
                 throw new SystemException("登录加密密码错误");
             }
    }
    
    /**
     * 解密
     * @param data 数据
     * @param loginPassword 用户密码
     * @return
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static String getDecryptData(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	getLoginCryptpassword();
    	String data1 ="";
    	try {
    		if(StringUtils.isEmpty(data)){
    			return data;
    		}
    		byte[] temp = decrypt(parseHexStr2Byte(data), MyConstant.LOGIN_CRYPT_PASSWORD);
    		if(StringUtils.anyIsEmpty(temp)){
    			data1 = null;
    		}else{
    			
    			data1= new String(temp,MyConstant.UTF_8);
    		}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
    	return data1;
    }
    
    /**
     * 加密
     * @param data  数据
     * @param loginPassword 用户密码
     * @return  返回16进制数
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static String getEncryptData(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    		getLoginCryptpassword();
    	return parseByte2HexStr(encrypt(data, MyConstant.LOGIN_CRYPT_PASSWORD));	
    	
    }
}
