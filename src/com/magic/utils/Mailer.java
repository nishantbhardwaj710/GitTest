package com.magic.utils;


import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

import org.apache.log4j.Logger;

   

public class Mailer
{
	private static Logger log=Logger.getLogger(Mailer.class);
	static String configFilename="./config/config";
	public static void send(final String from,final String password,String address,String sub,String msg,String filename)
	{ 
		Properties props = new Properties();    
		props.put("mail.smtp.host", "smtp.gmail.com");    
		props.put("mail.smtp.socketFactory.port", "465");    
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    
		props.put("mail.smtp.auth", "true");    
		props.put("mail.smtp.port", "435");    
		Session session = Session.getInstance(props, new javax.mail.Authenticator() 
		{
		    protected PasswordAuthentication getPasswordAuthentication() 
		    {
		        return new PasswordAuthentication(from, password);
		    }
		});  
		try 
		{    
			 Message message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse(address));
	         message.setSubject("sussex automation report");
	         BodyPart messageBodyPart = new MimeBodyPart();
	         messageBodyPart.setText("This is message body");
	         Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);
	         messageBodyPart = new MimeBodyPart();
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);
	         message.setContent(multipart);
	         Transport.send(message);
			log.info("Automation email report has been sent to all recipients");		
		} 
		catch (Exception e) 
		{
			log.error("Error sending email "+e.getMessage());
			throw new RuntimeException(e.getMessage());
		}    
	}
	
	
	

	@SuppressWarnings("unused")
	public static void sendMail(String message, String email_subject) throws Exception
	{
	
		String senderEmail=PropertyUtil.getproperty(configFilename, "sender_email");
		String senderPassword=PropertyUtil.getproperty(configFilename, "sender_password");
		String recepientEmails=PropertyUtil.getproperty(configFilename, "recepient_emails");
		//String email_subject=PropertyUtil.getproperty(configFilename, "email_subject");
	    //Mailer.send(senderEmail, senderPassword, recepientEmails, email_subject, message);
		StringSelection ss = new StringSelection("D:/sussex_Report.html");
				     //upload your file using RobotClass
				     //attach your path where file is located.
				     Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
				     Robot robot = new Robot();
				     Thread.sleep(5000);
				     robot.keyPress(KeyEvent.VK_CONTROL);
				     robot.keyPress(KeyEvent.VK_V);
				     robot.keyRelease(KeyEvent.VK_CONTROL);
				     robot.keyRelease(KeyEvent.VK_V);
				     Thread.sleep(6000);
				     robot.keyPress(KeyEvent.VK_ENTER);
				     robot.keyRelease(KeyEvent.VK_ENTER);
				     Thread.sleep(10000);
				     //  driver.findElement(By.xpath("//div[text()='Send']")).click();

	}

	public static void main(String[] args) 
	{    
		//from,password,to,subject,message  
		//Mailer.send("sanjay.dewrari@magicsw.com","software@8802","sanjaydewrari@gmail.com","hello javatpoint","How r u?");  
		//change from, password and to  
	}  
	


	public void executionReportMail(String from,String to,String emailPassword,String subject,String messageText,String attachment) throws IOException
	{
		

		

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");    
		props.put("mail.smtp.socketFactory.port", "465");    
		props.put("mail.smtp.socketFactory.class",    
				"javax.net.ssl.SSLSocketFactory");    
		props.put("mail.smtp.auth", "true");    
		props.put("mail.smtp.port", "465");   

		// Get the Session object.
		Session session = Session.getDefaultInstance(props,    
				new javax.mail.Authenticator() {    
			protected PasswordAuthentication getPasswordAuthentication() 
			{    
				return new PasswordAuthentication(from,emailPassword);  
			}    
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText(messageText);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = attachment;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (Exception e) {
			//throw new RuntimeException(e);
			e.printStackTrace();
		}
	}


}    