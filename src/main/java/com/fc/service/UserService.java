package com.fc.service;

import com.fc.async.MailTask;
import com.fc.mapper.UserMapper;
import com.fc.model.Info;
import com.fc.model.User;
import com.fc.util.MD5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private JedisPool jedisPool;

    public User getProfile(int sessionUid, int uid) {
        //如果是浏览别人的主页，则增加主页浏览数
        if(sessionUid!=uid){
            userMapper.updateScanCount(uid);
        }
        //从数据库得到User对象
        User user = userMapper.selectUserByUid(uid);
        //设置获赞数，关注数，粉丝数
        Jedis jedis = jedisPool.getResource();
        user.setFollowCount((int)(long)jedis.scard(uid+":follow"));
        user.setFollowerCount((int)(long)jedis.scard(uid+":fans"));
        String likeCount = jedis.hget("vote",uid+"");
        if(likeCount==null){
            user.setLikeCount(0);
        }else {
            user.setLikeCount(Integer.valueOf(likeCount));
        }

        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
        return user;
    }

    public User getEditInfo(int uid) {
        return userMapper.selectEditInfo(uid);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void record(StringBuffer requestURL, String contextPath, String remoteAddr) {
        Info info = new Info();
        info.setRequestUrl(requestURL.toString());
        info.setContextPath(contextPath);
        info.setRemoteAddr(remoteAddr);
        userMapper.insertInfo(info);
    }

    public List<User> listUserByTime() {
        return userMapper.listUserByTime();
    }

    public List<User> listUserByHot() {
        return userMapper.listUserByHot();
    }

    public void updateHeadUrl(int uid, String headUrl) {
        userMapper.updateHeadUrl(uid,headUrl);
    }

    public void unfollow(int sessionUid, int uid) {
        Jedis jedis = jedisPool.getResource();
        Transaction tx = jedis.multi();
        tx.srem(sessionUid+":follow", String.valueOf(uid));
        tx.srem(uid+":fans", String.valueOf(sessionUid));
        tx.exec();

        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
    }

    public void follow(int sessionUid, int uid) {
        Jedis jedis = jedisPool.getResource();
        Transaction tx = jedis.multi();
        tx.sadd(sessionUid+":follow", String.valueOf(uid));
        tx.sadd(uid+":fans", String.valueOf(sessionUid));
        tx.exec();
        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
    }

    public boolean getFollowStatus(int sessionUid, int uid) {
        Jedis jedis = jedisPool.getResource();
        boolean following = jedis.sismember(sessionUid+":follow", String.valueOf(uid));
        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
        return following;
    }

    public String updatePassword(String password, String newpassword, String repassword, int sessionUid,String eamil) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String oldPassword = userMapper.selectPasswordByUid(sessionUid);
        //根据email和密码进行加密
        if(!oldPassword.equals(MD5.MD5_64bit(eamil,password))){
            return "原密码输入错误~";
        }

        if(newpassword.length()<6 ||newpassword.length()>20){
            return "新密码长度要在6~20之间~";
        }

        if(!newpassword.equals(repassword)){
            return "新密码两次输入不一致~";
        }

        userMapper.updatePassword(MD5.MD5_64bit(eamil,newpassword),sessionUid);
        return "ok";
    }

    //发送忘记密码确认邮件
    public void forgetPassword(String email) {
        String verifyCode = userMapper.selectVerifyCode(email);
        System.out.println("verifyCode:"+verifyCode);
        //发送邮件
        String secretKey = UUID.randomUUID().toString(); // 密钥
        Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
        long expired = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql 取出时间是忽略毫秒数的
    	Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("secretKey",secretKey);
		map.put("expired",String.valueOf(expired));
        userMapper.updateUserEmail(map);
        taskExecutor.execute(new MailTask(verifyCode,email,javaMailSender,2,secretKey,expired));
    }

    public void verifyForgetPassword(String code,String email) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("更新前："+code);
        Map<String, String> map=new HashMap<String, String>();
        map.put("code", code);
        map.put("password", MD5.MD5_64bit(email, code.substring(0, 8)));
        userMapper.updatePasswordByActivateCode(map);
        System.out.println("更新后："+code);
    }
    
    public Integer findUserKey(String email, String expired, String secretKey) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("secretKey",secretKey);
		map.put("expired",expired);
		return userMapper.findUserKey(map);
	}
}

