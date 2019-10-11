package com.suntak.eightdisciplines.ExcelUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import com.sun.mail.util.MailSSLSocketFactory;
/**
 * 发送邮件工具类
 * 
 * @author tp
 *
 */
public class SendMailUtils {

    /**
     * 发送邮件
     * @param subject 主题
     * @param receiver 接收人
     * @param content 邮件内容
     * @param is 文件字节流
     * @param RMANO 索赔单号
     * @return
     * @throws IOException
     */
    public  static  boolean sendMail(String subject, String receiver, String content, InputStream is,String RMANO) throws IOException {
        boolean isFlag = false;
        try {
            String smtpFromMail = "sysadmin8d@suntakpcb.com";  //发送账号
            String pwd = "asDF!@34"; //密码
            String host = "fastsmtphz.qiye.163.com"; //服务器域名


            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
            session.setDebug(false);

            MimeMessage message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(smtpFromMail, "8D财务扣款单"));
                // 设置发送给谁
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
                message.setSubject(subject);
                message.addHeader("charset", "UTF-8");

                /*添加正文内容*/
                Multipart multipart = new MimeMultipart();
                BodyPart contentPart = new MimeBodyPart();
                contentPart.setText(content);

                contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
                multipart.addBodyPart(contentPart);

                /*添加附件*/
                MimeBodyPart fileBody = new MimeBodyPart();
                DataSource source =  new ByteArrayDataSource(is, "application/msexcel");
                fileBody.setDataHandler(new DataHandler( source));
                String fileName  = "8D财务扣款单-"+ RMANO +".xls";
                // 中文乱码问题
                fileBody.setFileName(MimeUtility.encodeText(fileName));
                multipart.addBodyPart(fileBody);

                message.setContent(multipart);
                message.setSentDate(new Date());
                message.saveChanges();
                Transport transport = session.getTransport("smtp");

                // 不需要端口
                transport.connect(host,smtpFromMail, pwd);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                isFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
                isFlag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFlag;
    }
}