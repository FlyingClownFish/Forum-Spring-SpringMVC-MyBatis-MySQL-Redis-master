package com.fc.util;


public class MyConstant {
    //七牛云相关
    public static final String QINIU_IMAGE_URL = "http://otbx7hhlk.bkt.clouddn.com/";
    public static final String QINIU_ACCESS_KEY = "eBCl2R5Hj1YmEbJQ_js8wefqcT8bThATtURvoixD";
    public static final String QINIU_SECRET_KEY = "qKJw93-HCHPXCQG95TgZld2ZBivAhOI-cUmfO0FL";
    public static final String QINIU_BUCKET_NAME = "yang";

    //发送邮件的邮箱，要与df.properties中的一致
    public static final String MAIL_FROM = "flyfish19920716@163.com";

    //域名
    public static final String DOMAIN_NAME = "http://localhost:8083/flyfish/";
    //public static final String DOMAIN_NAME = "http://www.doublefuck.top/";

    //三种操作
    public static final int OPERATION_CLICK_LIKE = 1;
    public static final int OPERATION_REPLY = 2;
    public static final int OPERATION_COMMENT = 3;
    
    public static String LOGIN_CRYPT_PASSWORD = "";
    /** 验证用半角空格 */
    public static final String DBC_SPACE = " ";
    /** 验证用全角空格 */
    public static final String SBC_SPACE = "　";
	public static String LOGIN_CRYPT_IV = "";
	/**
	 * 秘钥
	 */
	public static final String LOGIN_CRYPT_DEKEY = "!CitsKpod";
    /** 下载文件编码 导出文件用 */
    public static final String UTF_8 = "UTF-8";
}
