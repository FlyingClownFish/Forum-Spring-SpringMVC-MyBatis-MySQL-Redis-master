package com.fc.util;


public class MyConstant {
    //七牛云相关
    public static final String QINIU_IMAGE_URL = "url";
    public static final String QINIU_ACCESS_KEY = "key1";
    public static final String QINIU_SECRET_KEY = "key2";
    public static final String QINIU_BUCKET_NAME = "yang";

    //发送邮件的邮箱，要与df.properties中的一致
    public static final String MAIL_FROM = "flyfish19920716@163.com";

    //域名
    public static final String DOMAIN_NAME = "http://localhost:8080/df/";
    //public static final String DOMAIN_NAME = "http://www.doublefuck.top/";

    //三种操作
    public static final int OPERATION_CLICK_LIKE = 1;
    public static final int OPERATION_REPLY = 2;
    public static final int OPERATION_COMMENT = 3;

}
