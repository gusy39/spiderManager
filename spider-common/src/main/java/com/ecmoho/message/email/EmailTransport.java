package com.ecmoho.message.email;


import com.ecmoho.message.MessageTransport;
import com.ecmoho.message.MessagingEngine;
import com.ecmoho.message.Msg;
import com.ecmoho.message.sms.EMACodeUnit;
import org.apache.commons.lang.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class EmailTransport implements MessageTransport {

	 // Cached SMTP sessions
    private Map<String, Session> smtpSessions = new HashMap<String, Session>();
    private String host ="smtp.mxhichina.com";
    private String port ="465";
    private String username ="admin@zrpei.com";
    private String password ="Zrpei@99";
    private String fromAddress ="admin@zrpei.com";
    private long timeout =30*1000; // 5 seconds
	@Override
	public void retry(Msg message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retryAsync(Msg message) {
		// TODO Auto-generated method stub

	}

	@Override
	public String send(Msg message, MessagingEngine.QueuePolicy queuePolicy) throws MessagingException {
		String fromName ="股王社区";
		String cc ="";
		String bcc="";
		String replyTo="";
		List<String> attachementsFileNames = message.getAttachementsFileNames();
		Session session =  getDefaultSMTPSession();
        Message msg = new MimeMessage(session);
		
		// Set to addresses
		Address[] addrs = InternetAddress.parse(message.getTo());
	    msg.addRecipients(Message.RecipientType.TO, addrs);
		// Set subject
		msg.setSubject(message.getSubject());
		// If provided, set from address and from name
		if(fromAddress != null){
			try{
				msg.setFrom(new InternetAddress(fromAddress, fromName, "UTF-8"));
			}catch(UnsupportedEncodingException e){
				// Should never happen
				System.out.println("Unsupported encoding");
			}
		}
		// If provided, set cc addresses
		if (!StringUtils.isEmpty(cc)){
			addrs = InternetAddress.parse(cc);
			msg.addRecipients(Message.RecipientType.CC, addrs);
		}
		// If provided, set bcc addresses
		if (!StringUtils.isEmpty(bcc)){
			addrs = InternetAddress.parse(bcc);
			msg.addRecipients(Message.RecipientType.BCC, addrs);
		}
		// If provided, set reply to address
		if (!StringUtils.isEmpty(replyTo)) {
			Address[] replyToAddress = { new InternetAddress(replyTo) };
			msg.setReplyTo(replyToAddress);
		}
		
		// Set headers
		msg.setHeader("X-Mailer", "JavaMailer");
		// Set sent date
		msg.setSentDate(Calendar.getInstance().getTime());

		// Set content
		if (attachementsFileNames == null || attachementsFileNames.size() == 0){ 
			// Send mail without any attachements
	    	msg.setContent(message.getContent(), "text/html; charset=UTF-8");
	    	
		}else{
	    	// Send mail with attachements
			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setText(message.getContent());
			messagePart.setHeader("Content-Type", "text/html; charset=UTF-8");
			
			Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(messagePart);
			
		    String basePath = "";//Cfg.getDirDataExt() + "/emailTemplate/attachments/";
		    
		    // Add all attachements to mail content
			for (String fileName : attachementsFileNames){			
				MimeBodyPart attachmentPart = new MimeBodyPart();
				
				FileDataSource fileDataSource = new FileDataSource(basePath + fileName){
				  	@Override
				    public String getContentType() {
				  			return "application/octet-stream";
				  	}
				};
				
				attachmentPart.setDataHandler(new DataHandler(fileDataSource));
				attachmentPart.setFileName(fileName);
				
				multipart.addBodyPart(attachmentPart);
			}
			
			msg.setContent(multipart);
		}
		
		Transport.send(msg);
		return EMACodeUnit.CODE_0.toString();
	}

	@Override
	public void sendAsync(Msg message, MessagingEngine.QueuePolicy queuePolicy) {
		// TODO Auto-generated method stub

	}
	
	/**
     * Get default SMTP session properties
     */
    private Session getDefaultSMTPSession() {
        return buildSMTPSession(this.host,this.port,this.username,this.password,this.timeout);
    }
    
    public Session buildSMTPSession(String host, String port, String username, String password, long timeout){
        
        String sessionId = host + ":" + port + ":" + (username != null ? username : "") + ":" + (password != null ? password : "") + ":" + timeout;
        
        // Return cached session
        if(smtpSessions.get(sessionId) != null){
            return smtpSessions.get(sessionId);
        }
        
        // Normalize port
        port = normalizeSMTPPort(port);

        // Set session properties
        Properties props = new Properties();
        Authenticator authenticator = null;
        
        //props.setProperty("mail.debug", "true"); // Enable debugging when needed
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        
        // Server requires authentication
        if(username != null && !username.isEmpty()){
            props.setProperty("mail.smtp.auth", "true");
            
            final String _username = username;
            final String _password = password;
            authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(_username, _password);
                }
            };
        }
        
        // Enable SSL connection
        if(port.equals("465")){
            props.setProperty("mail.smtp.ssl.enable", "true"); 
        }
        
        // Enable STARTTLS protocol by using ports 587 or 2587
        if(port.equals("587") || port.equals("2587")){
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.starttls.required", "true");
        }
        
        // Set connection timeout
        if(timeout > 0){
            props.setProperty("mail.smtp.timeout", "" + timeout);
        }
        
        Session session = authenticator != null ? Session.getInstance(props, authenticator) : Session.getInstance(props);
        
        // Cache session
        smtpSessions.put(sessionId, session);
        
        return session;
    }
    
    
    private String normalizeSMTPPort(String port){
        if(port == null || port.isEmpty()){
            return "25";
        }else{
            return port.trim();
        }
    }


	public Map<String, Session> getSmtpSessions() {
		return smtpSessions;
	}

	public void setSmtpSessions(Map<String, Session> smtpSessions) {
		this.smtpSessions = smtpSessions;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
