package ar.com.grupo306.commons.mailSender;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
public class Mail {
	
	private static Logger logger = Logger.getLogger(Mail.class.getName());

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
	
	/**
	 * @author Damian
	 * Genera la clase mail, cargando los parametros.
	 * @param configFile
	 * 		Path al archivo de properties. Único parametro obligatorio, si los demás no se setean se leen del archivo.
	 * @param senderAddress
	 * @param senderName
	 * @param mailSubject
	 * @param mailBodyTemplateFile
	 */
		
	public Mail(String configFile, String senderAddress, String senderName, String mailSubject, String mailBodyTemplateFile) {
		super();
		
		try{
			
			Properties properties = new Properties();
			PropertyConfigurator.configure(configFile);
			String file;
			
			logger.info("loading configuration...");
			FileInputStream config = new FileInputStream(configFile);
			properties.load(config);
			config.close();
			
			this.configFile= configFile;
			this.emailHost = properties.getProperty("smtp.host");
			this.emailPort = Integer.parseInt(properties.getProperty("smtp.port"));
			this.senderPass = properties.getProperty("smtp.sender.pass");
			
			if((senderAddress!=null) && (!senderAddress.equalsIgnoreCase(""))){
				this.senderAddress = senderAddress;
			}else{
				this.senderAddress = properties.getProperty("smtp.sender.address");
			}
			
			if((senderName!=null) && (!senderName.equalsIgnoreCase(""))){
				this.senderName = senderName;
			}else{
				this.senderName = properties.getProperty("smtp.sender.name");
			}
			
			if((mailSubject!=null) && (!mailSubject.equalsIgnoreCase(""))){
				this.mailSubject = mailSubject;
			}else{
				this.mailSubject = properties.getProperty("smtp.mail.subject");
			}
			
			if((mailBodyTemplateFile!=null) && (!mailBodyTemplateFile.equalsIgnoreCase(""))){
				file = mailBodyTemplateFile;
			}else{
				file = properties.getProperty("smtp.mail.bodyTemplate");
			}			
			this.mailBodyTemplate = loadFile(file);
			
		} catch (IOException e) {
			logger.error("error loading configuration");
		}
		
		
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

	/**
	 * @author Damian
	 * 
	 * Prepara y envia el mail a los destinatarios indicados.
	 * 
	 * @param Destinatatios
	 * 			Colección de usuarios destinatarios del mail.
	 * @param Attachments
	 * 			Colección de attachs para enviar en el mail.
	 * @return "OK"
 	 */
	public String doSendMails(Collection destinatatios, Collection attachments){
		
		//SendMails sendMails;// = new SendMails();
		Properties properties = new Properties();
		PropertyConfigurator.configure(configFile);
		
		try {
			logger.info("loading configuration...");
			FileInputStream config = new FileInputStream(configFile);
			properties.load(config);
			config.close();

			setEmailAttachements(attachments);
			/**
			 * TODO
			 * Iterar los destinatarios como objetos de la clase Usuarios
			 */
			Iterator iter = destinatatios.iterator();
			while (iter.hasNext()) {
				String element = (String) iter.next();
				setRecipientAddress(element);
				//setRecipientName(element.getNombre());
				doSendMail();
				
			}

		} catch (IOException e) {
			logger.error("error loading configuration");
		} catch (EmailException e) {
			logger.error("error sending email", e);
		}
		
		
		
		
		return "OK";
	}
	
	private String doSendMail() throws EmailException {
		
		
		// Create the email message
		logger.info("creating message");
		HtmlEmail email = new HtmlEmail();
		email.setHostName(emailHost);	
		email.setSmtpPort(emailPort);
		email.setFrom(senderAddress);
		email.setAuthentication(senderAddress, senderPass);
		logger.info("setting recipient address");
		logger.debug(" - recipientAddress: '" + recipientAddress + "'");
		logger.debug(" - recipientName: '" + recipientName + "'");
		email.addTo(recipientAddress, recipientName);
		logger.info("setting sender address");
		logger.debug(" - senderAddress: '" + senderAddress + "'");
		logger.debug(" - senderName: '" + senderName + "'");
		logger.debug(" - mailSubject: '" + mailSubject + "'");
		email.setFrom(senderAddress, senderName);
		email.setSubject(mailSubject);
		
		//set msg
		email.setHtmlMsg(parseTemplate(claves, valores));
		
		
		//add the attachments
		Iterator iter = emailAttachements.iterator();
		while (iter.hasNext()) {
			EmailAttachment attachment = (EmailAttachment) iter.next();
			logger.debug("adding attachment");
			email.attach(attachment);	
		}

		// send the email
		logger.debug("sending email");
		email.send();
		logger.debug("mail sent!");
		
		
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
	public EmailAttachment Attachment(String attachmentPath, String attachmentDescription, String attachmentName) {
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(attachmentPath);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(attachmentDescription);
		attachment.setName(attachmentName);
		return attachment;
	}
	
	
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
	
	/**
	 * @author Damian
	 * 
	 *  Carga el template del mail.
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private String loadFile(String path) throws IOException {
		StringBuffer text = new StringBuffer();
		FileReader input = null;

		input = new FileReader(path);

		BufferedReader reader = new BufferedReader(input);

		String linea = reader.readLine();
		while (linea != null) {
			text.append(linea);
			linea = reader.readLine();
		}
		input.close();

		return text.toString();
	}

}
