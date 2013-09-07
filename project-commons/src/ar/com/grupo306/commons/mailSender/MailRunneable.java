package ar.com.grupo306.commons.mailSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.jscape.inet.email.HtmlEmailMessage;
import com.jscape.inet.mime.Attachment;
import com.jscape.inet.mime.MimeException;
import com.jscape.inet.smtpssl.SmtpSsl;


/**
 *  * @author Damian
 *  Se encarga de realizar el envio de mails.
 *  USO:
 *  	SendMail mail = new SendMail(propertyConf,senderAddress, senderName, mailSubject, mailBodyTemplateFile);
 *      //Agregar las claves para el template elegido.
 *  	mail.addClavesRemplazo("Nombre","Grupo9999");
 *  	mail.addClavesRemplazo("Motivo","Mensaje de confirmación");
 *  	//Hacer el envio
 *  	mail.doSendMails(destinatatios, attachments);
 *    
 *  	
 */
public class MailRunneable implements Runnable{
	
	private static Logger logger = Logger.getLogger(MailRunneable.class.getName());

	private String emailHost;
	
	private int emailPort;

	private String recipientAddress;

	private String recipientName;

	private String senderAddress;

	private String senderName;
	
	private String senderPass;

	private String mailSubject;

	private String mailBodyTemplate;

	private String passwd;
	
	private Collection emailAttachements;
	
	private Vector<String> claves=new Vector<String>();
	
	private HashMap<String,String> valores=new HashMap<String,String>();
	
	private String configFile;
	
	public void run() {
		try {
			this.doSendMail();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	
	public MailRunneable(Mail mail) {
		this.logger = Logger.getLogger(MailRunneable.class.getName());
		this.emailHost = mail.getEmailHost();
		this.emailPort = mail.getEmailPort();
		this.recipientAddress = mail.getRecipientAddress();
		this.recipientName = mail.getRecipientName();
		this.senderAddress = mail.getSenderAddress();
		this.senderName = mail.getSenderName();
		this.senderPass = mail.getSenderPass();
		this.mailSubject = mail.getMailSubject();
		this.mailBodyTemplate = mail.getMailBodyTemplate();
		this.passwd = mail.getPasswd();
		this.emailAttachements = mail.getEmailAttachements();
		this.claves = mail.getClaves();
		this.valores = mail.getValores();
		this.configFile = mail.getConfigFile();
	}
	

	
	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public Vector getClaves() {
		return claves;
	}

	public void setClaves(Vector<String> claves) {
		this.claves = claves;
	}

	public HashMap getValores() {
		return valores;
	}

	public void setValores(HashMap<String,String> valores) {
		this.valores = valores;
	}

	public Collection getEmailAttachements() {
		return emailAttachements;
	}

	public void setEmailAttachements(Collection emailAttachements) {
		this.emailAttachements = emailAttachements;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	public String getMailBodyTemplate() {
		return mailBodyTemplate;
	}

	public void setMailBodyTemplate(String mailBodyTemplate) {
		this.mailBodyTemplate = mailBodyTemplate;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRecipientAddress() {
		return recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderPass() {
		return senderPass;
	}

	public void setSenderPass(String senderPass) {
		this.senderPass = senderPass;
	}
	
	
	public int getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(int emailPort) {
		this.emailPort = emailPort;
	}

	
	private String doSendMail() throws EmailException {
		try {
			// Create the email message
			logger.info("creating message");
	
			logger.info("setting recipient address");
			logger.debug(" - recipientAddress: '" + recipientAddress + "'");
			logger.debug(" - recipientName: '" + recipientName + "'");
			logger.info("setting sender address");
			logger.debug(" - senderAddress: '" + senderAddress + "'");
			logger.debug(" - senderName: '" + senderName + "'");
			logger.debug(" - mailSubject: '" + mailSubject + "'");
			logger.debug(" - mailBody: '" + parseTemplate(claves, valores) + "'");
	
			HtmlEmailMessage message = new HtmlEmailMessage();
		    message.setTo(recipientAddress);
		    //message.setFrom(senderName);
		    message.setFrom(senderAddress);
		    message.setSubject(mailSubject);
		    message.setHtmlBody(parseTemplate(claves, valores));
			
			//add the attachments
			Iterator iter = emailAttachements.iterator();
			while (iter.hasNext()) {
				Attachment attachment = (Attachment) iter.next();
				logger.debug("adding attachment");			
				message.addAttachment(attachment );	
			}
		    
		    
			// send the email
			logger.debug("sending email");
			try {
				SmtpSsl smtp = new SmtpSsl("smtp.gmail.com",465);
			    smtp.connect();
			    smtp.login(senderAddress, senderPass);
			    smtp.send(message);  
			    smtp.disconnect();     
				logger.debug("mail sent!");
			} catch (Exception e) {
				logger.debug("sendMail exeption!!!");
			}
		} catch (Exception e) {
			logger.debug("sendMail General Exeption!!!");
			e.printStackTrace();
		}
		return "OK";
	}
	
	/**
	 * @author Damian
	 * 
	 * Genera un attachment
	 * 
	 * @param attachmentPath
	 * 			Ruta al archivo
	 * @param attachmentDescription
	 * 			Descripción del attach
	 * @param attachmentName
	 * 			Nombre del attach
	 * @return
	 */
	/*
	public EmailAttachment Attachment(String attachmentPath, String attachmentDescription, String attachmentName) {
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(attachmentPath);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(attachmentDescription);
		attachment.setName(attachmentName);
		return attachment;
	}
	*/
	
	/**
	 * @author Damian
	 * Agrega las claves y valores de remplazo para el template de mail.
	 * @param clave
	 * @param valor
	 */
	public void addClavesRemplazo(String clave, String valor){
		claves.add(clave);
		valores.put(claves.elementAt(claves.size()-1), valor);
	}
	
	/**
	 * @author Damian
	 * 
	 * Parsea el Template para reemplazar las claves por los valores.
	 * 
	 * @param claves
	 * @param valores
	 * @return 
	 * 		Template parseado.
	 */
	private String parseTemplate(Vector claves, HashMap valores) {
		String s = new String(mailBodyTemplate);

		Iterator i = claves.iterator();
		while (i.hasNext()) {
			String clave = (String) i.next();
			String valor = (String) valores.get(clave);

			if (valor != null) {
				s = s.replaceAll("\\$" + clave + "\\$", valor);
			}
		}
		return s;
	}
	

}
