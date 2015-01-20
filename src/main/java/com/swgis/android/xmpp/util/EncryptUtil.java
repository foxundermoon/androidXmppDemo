package com.swgis.android.xmpp.util;

/**
 * Created by foxundermoon on 2015/1/16.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Base64;


public class EncryptUtil {

    private static final int BUFFER_SIZE = 2048;

    /**
     * UTF-8解码
     *
     * @param in
     * @return
     */

    @SuppressLint("NewApi")
    public static String decryptUTF8(byte[] in) {
        return new String(in, Charset.forName("UTF-8"));
    }

    /**
     * UTF-8编码
     *
     * @param in
     * @return
     */
    public static byte[] encryptUTF8(String in) {
        if (in == null || in == "")
            return null;
        try {
            return in.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * UTF-8 String -> GZIP -> BASE64 -> UTF-8 String
     * @param str
     * @return
     */
    public static String encrBASE64ByGzip(String str) {
        return decryptUTF8(encryptBASE64(encryptGZIP(encryptUTF8(str))));
    }

    public static String decryptBASE64ByGzip(String base64){
        return decryptUTF8(decryptGZIP(decryptBASE64(encryptUTF8(base64))));
    }
    /**
     * BASE64 UTF-8字符串编码
     *
     * @param str
     * @return
     */

    public static String encryptBASE64(String str) {
        return  decryptUTF8(encryptBASE64(encryptUTF8(str)));
    }

    /**
     * BASE64编码
     * @param in
     * @return
     */
    public static byte[] encryptBASE64(byte[] in){
        if(in==null||in.length==0) return null;
        return Base64.encode(in, 0, in.length, Base64.DEFAULT);

    }
    /**
     * BASE64 UTF-8 字符串解密
     *
     * @param str
     * @return
     */
    public static String decryptBASE64(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return decryptUTF8(decryptBASE64(encryptUTF8(str)));
    }
    public static byte[] decryptBASE64(byte[] in){
        if(in==null||in.length==0)
            return null;
        return Base64.decode(in,0,in.length,Base64.DEFAULT);
    }
    /**
     * GZIP编码
     *
     * @param in
     * @return
     */
    public static byte[] encryptGZIP(byte[] in) {
        if (in == null || in.length == 0)
            return null;
        try {
            // gzip压缩
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(in);
            gzip.close();
            byte[] encode = baos.toByteArray();
            baos.flush();
            baos.close();
            // base64 加密
            return encode;
//			return new String(encode, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GZIP UTF-8字符串编码
     * @param str
     * @return
     */
    public static byte[] encryptGZIP(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return encryptGZIP(encryptUTF8(str));
    }

    /**
     * GZIP解码
     * @param in
     * @return
     */
    public static byte[] decryptGZIP(byte[] in) {
        if (in == null || in.length == 0)
            return null;
        try {
            //gzip 解压缩
            ByteArrayInputStream bais = new ByteArrayInputStream(in);
            GZIPInputStream gzip = new GZIPInputStream(bais);
            byte[] buf = new byte[BUFFER_SIZE];
            int len = 0;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ((len = gzip.read(buf, 0, BUFFER_SIZE)) != -1) {
                baos.write(buf, 0, len);
            }
            gzip.close();
            baos.flush();

            byte[] out = baos.toByteArray();
            baos.close();
            return out;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 十六进制字符串 转换为 byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
        // return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * byte[] 转换为 十六进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");

        if (src == null || src.length <= 0) {
            return null;
        }

        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
