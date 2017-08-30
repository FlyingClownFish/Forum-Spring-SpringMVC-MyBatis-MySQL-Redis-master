package com.fc.async;

import java.sql.Timestamp;
import java.util.UUID;

import com.fc.util.DES3Util;
import com.fc.util.MyConstant;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

public class MailTask implements Runnable {

    private String code;
    private String email;
    private JavaMailSender javaMailSender;
    private int operation;
    private String secretKey;
    private long expired;
    public MailTask(String code, String email, JavaMailSender javaMailSender,int operation,String secretKey,long expired) {
        this.code = code;
        this.email = email;
        this.javaMailSender = javaMailSender;
        this.operation = operation;
        this.secretKey=secretKey;
        this.expired=expired;
    }

    @Override
    public void run() {
        javaMailSender.send(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                System.out.println("开始发邮件...");
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
                mimeMessageHelper.setFrom(MyConstant.MAIL_FROM);
                mimeMessageHelper.setTo(email);
                mimeMessageHelper.setSubject("一封激活邮件");
                StringBuilder sb  = new StringBuilder();
                sb.append("<html><head></head><body>");
                String key =code+"$"+ email + "$" + secretKey+ "$" + expired ;
                if(operation==1){
                    sb.append("<a href="+MyConstant.DOMAIN_NAME+"activate.do?code=");
                    sb.append(code);
                    sb.append(">点击激活</a></body>");
                }else{
                    sb.append("是否将您的密码修改为:");
                    sb.append(code.substring(0,8));
                    sb.append("<a href="+MyConstant.DOMAIN_NAME+"verify.do?key="+DES3Util.getEncryptData(key)+">");
                    sb.append("点击是</a></body>");
                }

                mimeMessageHelper.setText(sb.toString(),true);

                System.out.println("结束发邮件...");
            }
        });
    }
}








