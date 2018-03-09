package net.gzhqlf.dszdy.util;

import java.security.MessageDigest;

/**
 * Created by n0elle on 2017/10/25.
 */
public class EncryptTool {

    private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // base64解密
    public static byte[] base64Decode(String content) {
        if (content.length() % 4 != 0) {
            return null;
        }
        byte decoded[] = new byte[((content.length() * 3) / 4)
                - (content.indexOf('=') > 0 ? (content.length() - content.indexOf('=')) : 0)];
        char[] inChars = content.toCharArray();
        int j = 0;
        int b[] = new int[4];
        for (int i = 0; i < inChars.length; i += 4) {
            // This could be made faster (but more complicated) by precomputing
            // these index locations.
            b[0] = CODES.indexOf(inChars[i]);
            b[1] = CODES.indexOf(inChars[i + 1]);
            b[2] = CODES.indexOf(inChars[i + 2]);
            b[3] = CODES.indexOf(inChars[i + 3]);
            decoded[j++] = (byte) ((b[0] << 2) | (b[1] >> 4));
            if (b[2] < 64) {
                decoded[j++] = (byte) ((b[1] << 4) | (b[2] >> 2));
                if (b[3] < 64) {
                    decoded[j++] = (byte) ((b[2] << 6) | b[3]);
                }
            }
        }
        return decoded;
    }

    // base64加密
    public static String base64Encode(byte[] contentBytes) {
        StringBuilder out = new StringBuilder((contentBytes.length * 4) / 3);
        int b;
        for (int i = 0; i < contentBytes.length; i += 3) {
            b = (contentBytes[i] & 0xFC) >> 2;
            out.append(CODES.charAt(b));
            b = (contentBytes[i] & 0x03) << 4;
            if (i + 1 < contentBytes.length) {
                b |= (contentBytes[i + 1] & 0xF0) >> 4;
                out.append(CODES.charAt(b));
                b = (contentBytes[i + 1] & 0x0F) << 2;
                if (i + 2 < contentBytes.length) {
                    b |= (contentBytes[i + 2] & 0xC0) >> 6;
                    out.append(CODES.charAt(b));
                    b = contentBytes[i + 2] & 0x3F;
                    out.append(CODES.charAt(b));
                } else {
                    out.append(CODES.charAt(b));
                    out.append('=');
                }
            } else {
                out.append(CODES.charAt(b));
                out.append("==");
            }
        }
        return out.toString();
    }

    // md5加密
    public static String MD5Encode(String content) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = content.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
