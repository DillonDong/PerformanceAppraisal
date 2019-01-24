package cn.edu.fjnu.towide.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @ClassName: PushMailUtil
 *  邮箱推送工具类
 * @author WangSheng
 * @date 2018年8月1日
 *
 */
@Component
public class PushMailUtil {
    @Value("${email.user}")
    private String user; // 邮箱用户名
    @Value("${email.password}")
    private String password; // SMTP密码
    @Value("${email.title}")
    private String title;


    private final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    private final String ALIDM_SMTP_PORT = "25";//或"80"

    public void SampleMail(String content, String receiverMail){
        // 配置发送邮件的环境属性
        System.out.println(user);
        final Properties props = new Properties();
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", ALIDM_SMTP_HOST);
        props.put("mail.smtp.port", ALIDM_SMTP_PORT);

        props.put("mail.user", user);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", password);
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
//        mailSession.setDebug(true);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        try {
            // 设置发件人
            InternetAddress fromAddress = new InternetAddress(user);
            message.setFrom(fromAddress);
            Address[] a = new Address[1];
            a[0] = new InternetAddress("1");
            message.setReplyTo(a);
            // 设置收件人
            InternetAddress to = new InternetAddress(receiverMail);
            message.setRecipient(MimeMessage.RecipientType.TO, to);
            // 设置邮件标题
            message.setSubject(title);
            // 设置邮件的内容体

            message.setContent(content, "text/html;charset=UTF-8");
           /* //创建附件
            MimeBodyPart attch = new MimeBodyPart();
            DataHandler dh1 = new DataHandler(new FileDataSource(file));
            attch.setDataHandler(dh1);
            String filename1 = dh1.getName();
            // MimeUtility 是一个工具类，encodeText（）用于处理附件字，防止中文乱码问题
            try {
                attch.setFileName(MimeUtility.encodeText(filename1));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(attch);
            message.setContent(mm);
            message.saveChanges(); //保存修改*/


            // 发送邮件
            Transport.send(message);
        }
        catch (MessagingException e) {
            String err = e.getMessage();
            // 在这里处理message内容， 格式是固定的
            System.out.println(err);
        }
    }


}
