package com.fc.util;


import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
 

public final class StringUtils{

    /**
     * 编码格式定义
     */
    private static final String ENCODE = "UTF-8";

    /**
     * 随机对象
     */
    private static Random crsRand;

    /**
     * 随机对象
     */
    private static SecureRandom crsSecureRand;

    /**
     * token 用
     */
    private static final int PAD_BELOW = 0x10;

    /**
     * token 用
     */
    private static final int TWO_BYTES = 0xFF;

    /**
     * 访问Ip
     */
    private static String s_id;

    /**
     * 构造函数
     */
    private StringUtils() {
    }

    /**
     * 加载数据
     */
    static {
        crsSecureRand = new SecureRandom();
        long secureInitializer = crsSecureRand.nextLong();
        crsRand = new Random(secureInitializer);
        try {
            s_id = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            //DebugLogger.log(e.getMessage());
        }
    }

    /**
     * 字符串转黄成字符创数组，用逗号分组
     * 
     * @param str
     * @return
     */
    public static String[] str2Array(String str) {
        return str2Array(str, ",");
    }

    /**
     * 根据只等分隔符处理String装换成数组
     * 
     * @param str
     * @param sep
     * @return
     */
    public static String[] str2Array(String str, String sep) {
        StringTokenizer token = null;
        String[] array = null;

        // check
        if (str == null || sep == null) {
            return null;
        }

        // get string array
        token = new StringTokenizer(str, sep);
        array = new String[token.countTokens()];
        for (int i = 0; token.hasMoreTokens(); i++) {
            array[i] = token.nextToken();
        }

        return array;
    }

    /**
     * String 数组转换成String
     * 
     * @param str
     * @return
     */
    public static String array2String(String[] str) {
        int num = 0;
        StringBuffer result = new StringBuffer("");
        if (str == null) {
            return "";
        }
        num = str.length;
        for (int i = 0; i < num; i++) {
            if (str[i] != null) {
                result.append(str[i]);
            }
        }
        return result.toString();
    }

    /**
     * 正则表达式匹配<br>
     * 
     * @param value
     * @param regexp
     * @return boolean
     */
    public static boolean matches(String value, String regexp) {
        if (isEmpty(value) || isEmpty(regexp)) {
            return false;
        } else {
        
            return value.matches(regexp);
        }
    }

    /**
     * 判断是否都是半角 <br>
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHalf(String txt) {
        if (txt == null) {
            return true;
        }
        try {
            txt = txt.replaceAll("\\?", "");

            byte[] b = txt.getBytes(ENCODE);
            for (int i = 0; i < b.length; i++) {
                if ((b[i] & 0x80) == 0x80) {
                    if (!(((byte) 0xA0) <= b[i] && b[i] <= ((byte) 0xDf))) {
                        return false;
                    }
                } else if (b[i] == 0x3F) {
                    return false;
                }
            }
            return true;
        } catch (java.io.UnsupportedEncodingException e) {
            return false;
        }
    }

    /**
     * 判断是否包含全角空格或者半角空格 <br>
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHasSpace(String txt) {
        if (txt == null) {
            return false;
        }
        if (txt.contains(MyConstant.DBC_SPACE) || txt.contains(MyConstant.SBC_SPACE)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否都是半角的英文和数字
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHalfENGNum(String txt) {
        StringBuffer checkText = null;

        if (txt == null) {
            return true;
        }
        checkText = new StringBuffer(txt);
        for (int i = 0; i < checkText.length(); i++) {
            String subcheckText = checkText.substring(i, i + 1);
            if (!matches(subcheckText, "([a-zA-Z]+)") && !matches(subcheckText, "([0-9]+)")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断半角大写英文字符和数字
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHalfUpperENGNum(String txt) {
        StringBuffer checkText = null;

        if (txt == null) {
            return true;
        }
        checkText = new StringBuffer(txt);
        for (int i = 0; i < checkText.length(); i++) {
            String subcheckText = checkText.substring(i, i + 1);
            if (!matches(subcheckText, "([A-Z]+)") && !matches(subcheckText, "([0-9]+)")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断半角大写英文字符和数字和符号"-"
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHalfUpperENGNumHalfSymbol(String txt) {
        StringBuffer checkText = null;

        if (txt == null) {
            return true;
        }
        checkText = new StringBuffer(txt);
        for (int i = 0; i < checkText.length(); i++) {
            String subcheckText = checkText.substring(i, i + 1);
            if (!matches(subcheckText, "([A-Z]+)") && !matches(subcheckText, "([0-9]+)") && !matches(subcheckText, "-") && !matches(subcheckText, " ")) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isHalfUpperENGNumHalfSymbol1(String txt) {
        StringBuffer checkText = null;

        if (txt == null) {
            return true;
        }
        checkText = new StringBuffer(txt);
        for (int i = 0; i < checkText.length(); i++) {
            String subcheckText = checkText.substring(i, i + 1);
            if (!matches(subcheckText, "([0-9]+)") && !matches(subcheckText, "-") && !matches(subcheckText, "+")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断半角大写英文字符和数字和符号"-" 加空格
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHalfUpperENGNumHalfSymbolSpace(String txt) {
        StringBuffer checkText = null;

        if (txt == null) {
            return true;
        }
        checkText = new StringBuffer(txt);
        for (int i = 0; i < checkText.length(); i++) {
            String subcheckText = checkText.substring(i, i + 1);
            if (!matches(subcheckText, "([A-Z]+)") && !matches(subcheckText, "([0-9]+)") && !matches(subcheckText, "-") && !isEqual(subcheckText, " ")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断半角大写英文字符与数字
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHalfEN(String txt) {
        if (txt == null) {
            return true;
        }
        if (matches(txt, "([a-zA-Z]+)")) {
            return true;
        }
        return false;
    }

    /**
     * 正整数验证
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isPositiveNumHalf(String txt) {
        if (isEmpty(txt)) {
            return true;
        }

        if (matches(txt, "([0-9]+)")) {
            if (Integer.parseInt(txt) <= 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是否是半角数字
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHalfNum(String txt) {
        if (txt == null) {
            return true;
        }
        if (matches(txt, "([0-9]+)")) {
            return true;
        }
        return false;
    }

    /**
     * 整数验证
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isInteger(String txt) {
        if (isEmpty(txt)) {
            return true;
        }

        if (matches(txt, "([-+]?[0-9]+)")) {
            return true;
        }
        return false;
    }
    
    /**
     * 判断是否是半角数字和小数点
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHalfNumPoint(String txt) {
        if (txt == null) {
            return true;
        }
        if (!txt.contains(".")) {
            if (matches(txt, "([0-9]+)")) {
                return true;
            }
        }
        if (matches(txt, "([0-9]+.[0-9]+)")) {
            return true;
        }
        return false;
    }
    /**
     * 判断是否是半角数字和小数点
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isBigDecimal(String txt) {
        if (txt == null) {
            return true;
        }
        if (!txt.contains(".")) {
            if (matches(txt, "^[-+]?([0-9]+)")) {
                return true;
            }
        }
        if (matches(txt, "^[-+]?([0-9]+.[0-9]+)")) {
            return true;
        }
        return false;
    }
    
    /**
     * 判断两个String 是否相等， 如果都是null 也表示相等
     * 
     * @param s1
     * @param s2
     * @return boolean
     */
    public static boolean isEqual(String s1, String s2) {
        if (s1 == null) {
            s1 = "";
        }
        if (s2 == null) {
            s2 = "";
        }

        return (s1.equals(s2));
    }

    /**
     * 判断s1 是否包含s2
     * 
     * @param s1
     * @param s2
     * @return boolean
     */
    public static boolean isContains(String s1, String s2) {
        if (s1 == null) {
            return false;
        }
        if (s2 == null) {
            return false;
        }

        return (s1.contains(s2));
    }

    /**
     * 处理String null 如果是null 返回空字符串，否则返回trim后的String
     * 
     * @param value
     * @return String
     */
    public static String nvl(String value) {
        if (value == null) {
            return "";
        } else {
            return value.trim();
        }
    }

    /**
     * int 转换成String
     * 
     * @param value
     * @return String
     */
    public static String parseInt(Integer value) {
        if (value == null) {
            return "";
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * long 转换成字符串
     * 
     * @param value
     * @return String
     */
    public static String parseLong(Long value) {
        if (value == null) {
            return "";
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * Short 转换成字符串
     * 
     * @param value
     * @return String
     */
    public static String parseShort(Short value) {
        if (value == null) {
            return "";
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * 判断是否为空
     * 
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        if (str==null||"".equals(nvl(str))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * String list转换成String 数组
     * 
     * @param inputList
     * @return String[]
     */
    public static String[] listToStringArray(List<String> inputList) {
        return inputList.toArray(new String[inputList.size()]);
    }

    /**
     * 取得 hash String
     * 
     * @param md5Data
     * @return String
     */
    public static String getHashString(byte[] md5Data) {
        StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < md5Data.length; ++i) {
            String hex = Integer.toHexString(md5Data[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(0));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        return hashString.toString();
    }

    /**
     * 取得HexString
     * 
     * @param in
     * @return String
     */
    public static String toHexString(byte[] in) {
        byte ch = 0x00;
        int i = 0;

        if (in == null || in.length <= 0) {
            return null;
        }

        String[] pseudo = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

        StringBuffer out = new StringBuffer(in.length * 2);

        while (i < in.length) {
            ch = (byte) (in[i] & 0xF0);
            ch = (byte) (ch >>> 4);
            ch = (byte) (ch & 0x0F);
            out.append(pseudo[(int) ch]);
            ch = (byte) (in[i] & 0x0F);
            out.append(pseudo[(int) ch]);
            i++;
        }

        String rslt = new String(out);
        return rslt;

    }

    /**
     * String 数组转List
     * 
     * @param arrays
     * @return List<String>
     */
    public static List<String> stringArray2List(String[] arrays) {
        if (arrays == null) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < arrays.length; i++) {
            list.add(arrays[i]);
        }
        return list;
    }

    /** 
    * @Title: anyIsEmpty
    * @Description: 判断是否为空
    * @author hl
    * @return boolean
    * @throws 
    */ 
    public static boolean anyIsEmpty(Object... object){
    	for(Object o:object){
    		if(o==null){
    			return true;
    		}
    		if(o instanceof String){
    			if(isEmpty((String)o)){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * 随机字符串源
     */
    private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 或得随机字符串
     * 
     * @param length
     * @return String
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sbReturn = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE_CHAR.length());
            sbReturn.append(BASE_CHAR.charAt(number));
        }
        return sbReturn.toString();
    }

    /**
     * String 转换成 bigDecimail
     * 
     * @param number
     * @return BigDecimal
     */
    public static BigDecimal formatString2BigDecimal(String number) {
        if (isEmpty(number)) {
            return null;
        }
        try {
            return new BigDecimal(number.trim().replace(",", ""));
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    /**
     * BigDecimal 转String
     * 
     * @param value
     * @param suffix
     * @return String
     */
    public static String formatBigDecimal(BigDecimal value, int suffix) {
        if (value == null) {
            return "";
        }
        String fmtString = "###,###,###,###,##0";

        if (suffix > 0) {
            fmtString += ".";

            for (int i = 0; i < suffix; i++) {
                fmtString += "0";
            }
        }

        DecimalFormat fmt = new DecimalFormat(fmtString);
        if (value.doubleValue() == 0) {
            return fmt.format(0.00);
        } else {
            return fmt.format(value.doubleValue());
        }
    }
    
    /**
     * BigDecimal 转String
     * 
     * @param value
     * @param suffix
     * @return String
     */
    public static String bigDecimalToString(BigDecimal value, int suffix) {
        if (value == null) {
            return "";
        }
        String fmtString = "##############0";

        if (suffix > 0) {
            fmtString += ".";

            for (int i = 0; i < suffix; i++) {
                fmtString += "0";
            }
        }

        DecimalFormat fmt = new DecimalFormat(fmtString);
        if (value.doubleValue() == 0) {
            return fmt.format(0.00);
        } else {
            return fmt.format(value.doubleValue());
        }
    }

    /**
     * BigDecimal 转String 如果格式错误， 返回原字符串
     * 
     * @param value
     * @param suffix
     * @return String
     */
    public static String formatBigDecimal(String value, int suffix) {
        if (!isBigDecimal(value)) {
            return value;
        }
        BigDecimal bigDecimalvalue = formatString2BigDecimal(value);
        String fmtString = "###,###,###,###,###,##0";

        if (suffix > 0) {
            fmtString += ".";

            for (int i = 0; i < suffix; i++) {
                fmtString += "0";
            }
        }

        DecimalFormat fmt = new DecimalFormat(fmtString);
        if (bigDecimalvalue.doubleValue() == 0) {
            return fmt.format(0.00);
        } else {
            return fmt.format(bigDecimalvalue.doubleValue());
        }
    }

    /** 半角符号 */
    private static String halfSymbol = "!\"#$%&'()=~|`{+*}<>?_-^\\@[;:],./";

    /**
     * 判断是否是半角符号
     * 
     * @param txt
     * @return boolean
     */
    public static boolean isHalfSymbol(String txt) {
        if (halfSymbol.indexOf(txt) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * token create<br>
     * 
     * @return String token
     */
    public static String createToken(boolean secure) {
        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer(128);
        String valueBeforeMD5 = "";
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // Should not happen
            throw new IllegalStateException(e);
        }

        long time = System.currentTimeMillis();
        long rand = 0;

        if (secure) {
            rand = crsSecureRand.nextLong();
        } else {
            rand = crsRand.nextLong();
        }
        sbValueBeforeMD5.append(s_id);
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(time));
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(rand));

        valueBeforeMD5 = sbValueBeforeMD5.toString();
        md5.update(valueBeforeMD5.getBytes());

        byte[] array = md5.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int j = 0; j < array.length; ++j) {
            int b = array[j] & TWO_BYTES;
            if (b < PAD_BELOW) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(b));
        }

        return sb.toString();
    }

    /**
     * 长度范围内前加0
     * 
     * @param val
     * @param length
     * @return String
     */
    public static String addPreZero(Object val, int length) {
        if (val == null) {
            val = "";
        }
        String result = val.toString();
        int strLen = result.length();
        if (strLen < length) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length - strLen; i++) {
                sb.append('0');
            }
            sb.append(val);
            result = sb.toString();
        } else {
            result = result.substring(0, length);
        }

        return result;
    }

    /**
     * 判断List是否为空，空返回true
     */
    @SuppressWarnings("rawtypes")
	public static boolean isListEmpty(List list) {
        if (null != list && list.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否不为空，不空返回true
     * 
     * @param str
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 去除所有空格,包括中间的
     * 
     * @param str
     * @return boolean
     */
    public static String removeAllSpace(String str) {
        return str.replaceAll("\\s+", "");
    }
    
    /**
     * 封装String类的split
     * 
     * @param str
     * @param symbol
     * @return String[]
     */
    public static String[] split(String str, String symbol) {
        if (isEmpty(str)) {
            return null;
        }

        return str.split(symbol);
    }

    /**
     * 对指定字符串解密
     * 
     * @return String
     * @throws Exception
     */
    public static String decodeStr(String str) throws Exception {
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] temp = decoder.decodeBuffer(str);
        return new String(temp).trim();
    }

    /**
     * 去掉 resource中的空元素
     * 
     * @param resource
     * @return String[]
     */
    public static String[] removeNull(String[] resource) {
        List<String> target = new LinkedList<String>();

        if (!isNotEmptyStringArray(resource)) {
            return null;
        }

        for (String s : resource) {
            if (isNotEmpty(s)) {
                target.add(s);
            }
        }

        return target.toArray(new String[target.size()]);
    }

    /**
     * 判断是否是空的字符串数组
     * 
     * @param strs
     * @return boolean
     */
    public static boolean isNotEmptyStringArray(String[] strs) {
        return strs != null && strs.length != 0;
    }

    /**
     * 验证金额输入框的值
     * 
     * @return
     */
    public static boolean isMoneyInput(String value) {
        if (StringUtils.matches(value, "(^[1-9]\\d{0,8}$)|(^0\\.\\d{1,2}$)|(^[1-9]\\d{0,8}\\.\\d{1,2}$)")) {
            return true;
        }
        return false;
    }
    
    /**
     * 验证积分输入框的值
     * 
     * @return
     */
    public static boolean isIntegralInput(String value) {
    	 if (StringUtils.matches(value, "^[-+]?([1-9][\\d]{0,6}|0)(\\.[\\d]{1,2})?$")) {
             return true;
         }
         return false;
    }

    /**
     * 验证金额的整数部分是不是大于5位
     * 
     * @param value
     * @return
     */
    public static boolean checkInputFiveBelow(BigDecimal value) {
        if (null == value) {
            return false;
        }
        String val = String.valueOf(value);
        String intVal = "";
        if (val.indexOf(".") < 0) {
            intVal = val;
        } else {
            intVal = val.substring(0, val.indexOf("."));
        }
        if (intVal.length() > 5) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断String中是否包含空格
     * 
     * @param str 判断字符串
     * @return 是否包含空格
     */
    public static boolean checkHasBlankSpace(String str) {
        if (str.contains(" ") || str.contains("　")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Object 转换成String 避免出现空指针异常
     * 
     * @param obj 传入对象
     * @return 转换后的字符串
     */
    public static String object2String(Object obj) {
        return String.valueOf(obj);
    }

    /**
     * 去除全角半角的前后空格
     * 
     * @param str 源字符串
     * @return trim 后的字符串
     */
    public static String trim(String str) {
        if (str == null) {
            return "";
        } else {
            str = str.trim();
            if (str.startsWith("　") || str.startsWith(" ") || str.startsWith(" ")) {
                str = str.substring(1, str.length());
                return trim(str);
            } else if (str.endsWith("　") || str.endsWith(" ") ||  str.endsWith(" ")) {
                str = str.substring(0, str.length() - 1);
                return trim(str);
            } else {
                return str;
            }
        }
    }

    /**
     * String 字符串格式化， 传入格式化模式， 源字符串
     * 
     * @param formatPartten
     * @param regionString
     * @return 格式化后的字符串
     */
    public static String stringFormat(String formatPartten, String regionString) {
        try {
            String s = String.format(formatPartten, regionString);
            return s;
        } catch (Exception e) {
            return regionString.toString();
        }
    }
    
    /** 
     * 使用java正则表达式去掉多余的.与0 
     * @param s 
     * @return  
     */  
    public static String subZeroAndDot(String s){ 
    	if(s.indexOf("0E") == 0){
    		s = "0";
    	}
		if(s.indexOf(".") > 0){  
			s = s.replaceAll("0+?$", "");//去掉多余的0  
			s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
		}  
		return s;  
	}
    
    /** 
     * BigDecimal转String
     * @param s 
     * @return  
     */  
    public static String decimal2String(BigDecimal d){  
		if(d == null){
			return null;
		}else{
			return d.toString();  
		}
	}
    public static String removeSpecilChar(String str){
    	String result = "";
    	if(null != str){
    	Pattern pat = Pattern.compile("\\s*|\n|\r|\t");
    	Matcher mat = pat.matcher(str);
    	result = mat.replaceAll("");
    	}
    	return result;
    	}
   /**
    * 是否包含 数字 "-" "+"
    * @param txt
    * @return
    */
    public static boolean isHalfNumHalfSymbol(String txt) {
        StringBuffer checkText = null;

        if (txt == null) {
            return true;
        }
        checkText = new StringBuffer(txt);
        for (int i = 0; i < checkText.length(); i++) {
            String subcheckText = checkText.substring(i, i + 1);
            if (!matches(subcheckText, "([0-9]+)") && !matches(subcheckText, "-") && !isEqual(subcheckText, "+")) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 字符串长度
     * @param value
     * @return
     */
    public static int strLength(String value){
    	if(isNotEmpty(value)){
    		return value.length();    		
    	}else{
    		return 0;
    	}
    }

	/**
	 * function：SQL单引号处理
	 * 
	 * @param strInputField
	 *            处理前字段
	 * @return String 处理后的字段
	 */
	public static String convertString(String strInputField) {
		String strOutputField = "";
		// 对单引号进行处理
		if (strInputField != null) {
			strOutputField = strInputField.replaceAll("\\'", "\\'\\'");
		}
		return strOutputField;
	}
    
    public static void main(String[] args) {
    
    }
}
