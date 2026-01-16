package com.anuppur.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anuppur.bean.EmailBean;
import com.anuppur.exception.DMSBusinessException;




@Service
@SuppressWarnings("all")
public class EmailServiceUtil {

//	@Value("${mail.smtp.host}")
	private String host;
	
//	@Value("${mail.from}")	
	private String sender;
	
//	@Value("${mail.smtp.port}")
	private String port;

//	@Value("${mail.username}")
	private String userName;

//	@Value("${mail.password}")
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EmailServiceUtil() {
		
	}

	/**
	 * This method sends email message to recipients and CC address specified in
	 * dto and also it sets message body, subject to the value specified in dto
	 *
	 * @param email
	 * @param subject
	 * @throws EmailException
	 */
	public void sendEmailmessage(final EmailBean email) throws DMSBusinessException {

		try {
			// Get the session object
			Properties props = System.getProperties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.ssl.checkserveridentity", "true");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userName, password);
						}
					});
			final Message msg = new MimeMessage(session);
			InternetAddress[] toAddrs = null, ccAddrs = null;

			// if priority is passed then set it to message header
			if (email.getPriority() != null) {
				msg.addHeader("X-Priority", email.getPriority());
			}
			email.setSender(sender);
			toAddrs = InternetAddress.parse(email.getRecipients(), false);
			msg.setRecipients(Message.RecipientType.TO, toAddrs);
			msg.setFrom(new InternetAddress(userName, email.getSender()));
			msg.setSubject(email.getSubject());

			// setting CC if one set on dto
			if (email.getcC() != null) {
				ccAddrs = InternetAddress.parse(email.getcC(), false);
				msg.setRecipients(Message.RecipientType.CC, ccAddrs);
			}

			String mimeType = null;
			if (email.getHTML()) {
				mimeType = "text/html; charset=UTF-8";
			} else {
				mimeType = "text/plain; charset=UTF-8";
			}
			msg.setDataHandler(new DataHandler(email.getBody(), mimeType));

			sendEmail(msg);

		} catch (AddressException ex) {
			throw new DMSBusinessException("AddressException Occured while parsing email ids.", ex);
		} catch (MessagingException ex) {
			throw new DMSBusinessException("MessagingException Occured while preparing email message object.", ex);
		} catch (UnsupportedEncodingException ex) {
			throw new DMSBusinessException("UnsupportedEncodingException Occured while preparing email message object.", ex);
		} catch(DMSBusinessException e) {
			throw e;
		}
	}

	private void sendEmail(Message msg) throws DMSBusinessException {
		try {
			Transport.send(msg);
		} catch (MessagingException ex) {
			throw new DMSBusinessException("MessagingException Occured while sending email without retry.", ex);
		}
	}
	
	public void sendEmailmessageC(final EmailBean email) throws DMSBusinessException {

		try {
			// Get the session object
			Properties props = System.getProperties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.ssl.checkserveridentity", "true");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userName, "Support123$");
						}
					});
			final Message msg = new MimeMessage(session);
			InternetAddress[] toAddrs = null, ccAddrs = null;

			// if priority is passed then set it to message header
			if (email.getPriority() != null) {
				msg.addHeader("X-Priority", email.getPriority());
			}
			email.setSender(sender);
			toAddrs = InternetAddress.parse(email.getRecipients(), false);
			msg.setRecipients(Message.RecipientType.TO, toAddrs);
			msg.setFrom(new InternetAddress(userName, email.getSender()));
			msg.setSubject(email.getSubject());

			// setting CC if one set on dto
			if (email.getcC() != null) {
				ccAddrs = InternetAddress.parse(email.getcC(), false);
				msg.setRecipients(Message.RecipientType.CC, ccAddrs);
			}

			String mimeType = null;
			if (email.getHTML()) {
				mimeType = "text/html; charset=UTF-8";
			} else {
				mimeType = "text/plain; charset=UTF-8";
			}
			msg.setDataHandler(new DataHandler(email.getBody(), mimeType));

			sendEmail(msg);

		} catch (AddressException ex) {
			throw new DMSBusinessException("AddressException Occured while parsing email ids.", ex);
		} catch (MessagingException ex) {
			throw new DMSBusinessException("MessagingException Occured while preparing email message object.", ex);
		} catch (UnsupportedEncodingException ex) {
			throw new DMSBusinessException("UnsupportedEncodingException Occured while preparing email message object.", ex);
		}
	}


}
