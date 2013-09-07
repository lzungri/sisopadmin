package ar.com.grupo306.commons.mailSender;

import java.util.ArrayList;

public class testSendMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		//Parametros, si estan vacios usa los del arch. de conf.
		String senderAddress="";
		String senderName="";
		String mailSubject="";
		String mailBodyTemplateFile="";
		//Mails de los destinatatios
		ArrayList<String> destinatarios = new ArrayList();
		destinatarios.add("dam_zam@yahoo.com.ar");
		
		//Paths a los attachs
		ArrayList<String> attachments = new ArrayList();
		
		Mail mail = new Mail("config/mailSample.conf",senderAddress, senderName, mailSubject, mailBodyTemplateFile);
		//Reemplazos en el template... ej $Nombre$ ==> Grupo9999
		mail.addClavesRemplazo("Nombre","Grupo9999");
		mail.addClavesRemplazo("Motivo","Mensaje de confirmación");
		//Hacer el envio
		mail.doSendMails(destinatarios, attachments);
	}

}
