package ar.com.grupo306.commons.mailSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
	
	public void run() {
		try {
			this.doSendMail();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @author Damian
	 * Genera la clase mail, cargando los parametros.
	 * @param configFile
	 * 		Path al archivo de properties. Único parametro obligatorio, si los demás no se setean se leen del archivo.
	 * @param senderAddress
	 * @param senderName
	 * @param mailSubject
	 * @param mailBodyTemplate
	 */
	
	public Mail(String senderAddress, String senderName, String mailSubject, String mailBodyTemplate) {
		super();
		
		try{
			String file;
			
			logger.info("loading configuration...");
			
			String curDir = System.getProperty("user.dir");
			InputStream inputStream =
				Thread.currentThread().getContextClassLoader().getResourceAsStream("sisopadmin_MAILS.properties");
			

			//FileInputStream inputStream = new FileInputStream("C:Documents and Settings/Damian/Desktop/Facultad/Proyecto/workspace/sisopadmin-application/config/sisopadmin_MAILS.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			inputStream.close();	
									
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
			
			if((mailBodyTemplate!=null) && (!mailBodyTemplate.equalsIgnoreCase(""))){
				file = properties.getProperty(mailBodyTemplate);
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
				
		Collection attachs = new ArrayList<Attachment>();
		
		try {
			logger.info("loading configuration...");
			InputStream inputStream =
				Thread.currentThread().getContextClassLoader().getResourceAsStream("sisopadmin_MAILS.properties");
			
			//FileInputStream inputStream = new FileInputStream("C:Documents and Settings/Damian/Desktop/Facultad/Proyecto/workspace/sisopadmin-application/config/sisopadmin_MAILS.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			inputStream.close();	
			
			/*Crear los Attachs*/
			Iterator itera = attachments.iterator();
			while (itera.hasNext()) {
				String attachPath = (String) itera.next();
				if(attachPath!=null && !attachPath.equalsIgnoreCase("")){
					File archivo = new File(attachPath);
					try {
						attachs.add(new Attachment(archivo));
					} catch (MimeException e) {}
				}
			}
			////////////////////
			
			setEmailAttachements(attachs);
			/**
			 * TODO
			 * Iterar los destinatarios como objetos de la clase Usuarios
			 */
			Iterator iter = destinatatios.iterator();
			while (iter.hasNext()) {
				String element = (String) iter.next();
				setRecipientAddress(element);
				/**
				 * Armo el thread y lo lanzo...
				 */
				MailRunneable mailRuneable = new MailRunneable(this);
				Thread t = new Thread(mailRuneable);
				t.start();
			}

		} catch (IOException e) {
			logger.error("error loading configuration");
		}/* catch (EmailException e) {
			logger.error("error sending email", e);
		}*/
		
		return "OK MAIL Send";
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
	public String doSendMailsStatic(Collection destinatatios, Collection attachments){
				
		Collection attachs = new ArrayList<Attachment>();
		
		try {
			logger.info("loading configuration...");
			InputStream inputStream =
				Thread.currentThread().getContextClassLoader().getResourceAsStream("sisopadmin_MAILS.properties");
			
			//FileInputStream inputStream = new FileInputStream("C:Documents and Settings/Damian/Desktop/Facultad/Proyecto/workspace/sisopadmin-application/config/sisopadmin_MAILS.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			inputStream.close();	
			
			/*Crear los Attachs*/
			Iterator itera = attachments.iterator();
			while (itera.hasNext()) {
				String attachPath = (String) itera.next();
				if(attachPath!=null && !attachPath.equalsIgnoreCase("")){
					File archivo = new File(attachPath);
					try {
						attachs.add(new Attachment(archivo));
					} catch (MimeException e) {}
				}
			}
			////////////////////
			
			setEmailAttachements(attachs);
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
	
	/**
	 * @author Damian
	 * 
	 *  Carga el template del mail.
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private String loadFile(String template) throws IOException {
		StringBuffer text = new StringBuffer();
		FileReader input = null;
		
//		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(template);
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		IOUtils.copy(inputStream, outputStream);
//		text.append(outputStream.toString("UTF-8"));

		input = new FileReader(template);

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
