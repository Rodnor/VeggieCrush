package com.utils;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Classe permettant l'envoi de mail (pour la récupération de mot de passe)
 */
public class SendMail {
	
	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	/**
	 * Constructeur
	 * @param to
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendEmailSSL(String to, String subject, String content) throws AddressException, MessagingException {		
		
		Properties props = new Properties();
		props.put("mail.smtp.host", applicationProperties.getString("mail.smtp.host"));
		props.put("mail.smtp.socketFactory.port", applicationProperties.getString("mail.smtp.socketFactory.port"));
		props.put("mail.smtp.socketFactory.class", applicationProperties.getString("mail.smtp.socketFactory.class"));
		props.put("mail.smtp.auth", applicationProperties.getString("mail.smtp.auth"));
		props.put("mail.smtp.port", applicationProperties.getString("mail.smtp.port"));

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(applicationProperties.getString("mail.session.user"), applicationProperties.getString("mail.session.pass"));
				}
			}
		);

		Message message = new MimeMessage(session);
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);
		message.setContent(content, "text/html; charset=ISO-8859-1");

		Transport.send(message);
	}
}