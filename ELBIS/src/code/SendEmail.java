/**
 * This Class with methodes to Send Email to the Editor when new post has to be proved 
 * and To the User with the decision of the Editor 
 * @author Mohammed Ali Anis, Hamze Ali
 */
package code;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	/**
	 * To Send Email to the Edtior when new bolg has been created 
	 * 
	 * @param an,
	 * @param username,
	 * @param title,
	 * @param subsection
	 */
	public static void emailAddBlog(String an, String username, String title, String subsection) {
		String to = an; // to address. It can be any like gmail, yahoo etc.
	
		//This email Address has been created to send all Email from it ( noreply.elbis@gmail.com )
		String from = "noreply.elbis@gmail.com"; // from address. As this is using Gmail SMTP your from address should by Gmail
													
		String password = "ProPra.ppgrp2";

		Properties prop = new Properties();
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(an));
			message.setSubject("Share request from Username : " + username);
			message.setText("The User " + username + " has request to share a blog with the title ( " + title
					+ " ), in the subsection ( " + subsection + " ). Please check it as soon as possible ! ");

			Transport.send(message);

			System.out.println("Mail Sent...");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To Send Email to the Edtior when abolg has been updated or edited from User or Editor 
	 * 
	 * @param an,
	 * @param username,
	 * @param title,
	 * @param subsection
	 */
	public static void emailUpdateBlog(String an, String username, String title, String subsection) {
		String to = an; // to address. It can be any like gmail, yahoo etc.
		String from = "noreply.elbis@gmail.com"; // from address. As this is using Gmail SMTP your from address should
													// be Gmail
		String password = "ProPra.ppgrp2";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(an));
			message.setSubject("Update request from Username : " + username);
			message.setText("The User " + username + " has request to update a blog with the title ( " + title
					+ " ), in the subsection ( " + subsection + " ). Please check it as soon as possible ! ");

			Transport.send(message);

			System.out.println("Mail Sent...");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To Send Email to the User when new bolg has been accepted 
	 * 
	 * @param an,
	 * @param username,
	 * @param title
	 */
	public static void emailAcceptBlog(String an, String username, String title) {
		String to = an; // to address. It can be any like gmail, yahoo etc.
		String from = "noreply.elbis@gmail.com"; // from address. As this is using Gmail SMTP your from address should
													// be Gmail
		String password = "ProPra.ppgrp2";

		Properties prop = new Properties();
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(an));
			message.setSubject("Accepted Blog ");
			message.setText("Your Blog " + title + " has been from the Editor " + username + " accepted !");

			Transport.send(message);

			System.out.println("Mail Sent...");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * To Send Email to the User when new bolg has been rejected 
	 * 
	 * @param an,
	 * @param username,
	 * @param title,
	 * @param reason
	 */
	public static void emailRejectBlog(String an, String username, String title, String reason) {
		String to = an; // to address. It can be any like gmail, yahoo etc.
		String from = "noreply.elbis@gmail.com"; // from address. As this is using Gmail SMTP your from address should
													// be Gmail
		String password = "ProPra.ppgrp2";

		Properties prop = new Properties();
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(an));
			message.setSubject("Rejected Blog ");
			message.setText("Your Blog " + title + " has been from the Editor " + username + " rejected !" + "\n" + "The reason is : " + reason + "." );

			Transport.send(message);

			System.out.println("Mail Sent...");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
