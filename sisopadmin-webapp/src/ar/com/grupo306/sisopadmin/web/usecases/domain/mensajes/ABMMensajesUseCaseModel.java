package ar.com.grupo306.sisopadmin.web.usecases.domain.mensajes;

import java.util.Date;
import java.util.List;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.mensajes.Mensaje;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 *
 * @author Pablo
 */

public class ABMMensajesUseCaseModel extends ABMBaseUseCaseModel{
	
	private Mensaje mensaje;
	
	private List<Usuario> receptoresGrupos;
	private List<Usuario> receptoresAyudantes;
	private List<Usuario> receptoresCoordinadores;
	
	private String loginNameGrupoReceptor;
	private String loginNameAyudanteReceptor;
	private String loginNameCoordinadorReceptor;
	
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		if (mensaje == null){
			// Nuevo mensaje
			Usuario usuarioLogueado = getUsuarioLogueado(context);

			mensaje = new Mensaje();
			mensaje.setEmisor(usuarioLogueado);
			mensaje.setFechaAlta(new Date(System.currentTimeMillis()));						
			
			receptoresGrupos = usuarioLogueado.getPosiblesReceptoresGruposDeMensaje();
			receptoresAyudantes = usuarioLogueado.getPosiblesReceptoresAyudantesDeMensaje();
			receptoresCoordinadores = usuarioLogueado.getPosiblesReceptoresCoordinadoresDeMensaje();
			
			loginNameGrupoReceptor = "";
			loginNameAyudanteReceptor = "";
			loginNameCoordinadorReceptor = "";
		}
	}
	
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {
		mensaje = (Mensaje) modelObject;
		
		String loggedUserLoginName = ((LoggedUserContext) context.getUserContext()).getUserLoginName();
		if(loggedUserLoginName.equals(mensaje.getReceptor().getLoginName())){
			/* si el usuario logueado es el receptor del mensaje, setear que el mensaje
			   fue leido */
			mensaje.setLeidoPorReceptor(Boolean.TRUE);
			mensaje.update();
		}
	}
	
	@Transactional
	public void responder(UseCaseContext context) {
		if (mensaje.getMensajeHijo() != null){
			// el mensaje ya tiene un mensaje siguiente
			context.addErrorMessage("El mensaje ya ha sido respondido");
			context.refreshUseCase();
			return;
		}
		
		Usuario usuarioLogueado = getUsuarioLogueado(context);
		
		Mensaje nuevoMensaje = new Mensaje();
		nuevoMensaje.setTitulo(mensaje.getTitulo());
		nuevoMensaje.setEmisor(usuarioLogueado);		
		nuevoMensaje.setFechaAlta(new Date(System.currentTimeMillis()));
		nuevoMensaje.setMensajePadre(mensaje);
		
		if (mensaje.getEmisor().getLoginName().equals(usuarioLogueado.getLoginName())){
			/* El emisor del mensaje anterior es el usuario logueado, por lo que el
			   receptor del nuevo mensaje debe ser el receptor del anterior. */
			nuevoMensaje.setReceptor(mensaje.getReceptor());
		} else {
			/* El emisor del mensaje anterior no es el usuario logueado, por lo que el
			   receptor del nuevo mensaje debe ser el emisor del anterior. */
			nuevoMensaje.setReceptor(mensaje.getEmisor());	
		}
		
		mensaje = nuevoMensaje;
		setMode(new CreateMode());
		context.refreshUseCase();
	}
	
	@Transactional
	public void irAMensajeAnterior(UseCaseContext context) { 
		processModelObject(context, mensaje.getMensajePadre());
		context.refreshUseCase();
	}

	@Transactional
	public void irAMensajeSiguiente(UseCaseContext context) {
		processModelObject(context, mensaje.getMensajeHijo());
		context.refreshUseCase();
	}
	
	@Transactional
	public void enviarMensaje(UseCaseContext context) {
		if (!mensajeValido(new UseCaseValidationUtils(context))){
			context.refreshUseCase();
			return;
		}
			
		if (mensaje.getReceptor() == null){
			// mensaje nuevo (no es continuacion de una cadena de mensajes anterior)
			Usuario usuarioReceptor = buscarReceptor();
			mensaje.setReceptor(usuarioReceptor);
		}
		mensaje.setLeidoPorReceptor(false);
		mensaje.save();
		
		context.addMessage("Mensaje enviado exitosamente.");
		acceptUseCase(context);
	}

	private Usuario getUsuarioLogueado(UseCaseContext context){
		String loggedUserLoginName = ((LoggedUserContext) context.getUserContext()).getUserLoginName();
		return Usuario.findMeByLoginName(loggedUserLoginName);		
	}
	
	private Boolean mensajeValido(UseCaseValidationUtils validationUtils){
		validationUtils.assertNotEmpty(mensaje.getTitulo(),"Debe ingresar un título para el mensaje.");
		validationUtils.assertValidTextArea(mensaje.getContenido(), "El tamaño del mensaje es mayor al permitido.");
		if  (mensaje.getReceptor() == null &&
			(loginNameGrupoReceptor == null || "".equals(loginNameGrupoReceptor)) &&
			(loginNameAyudanteReceptor == null || "".equals(loginNameAyudanteReceptor)) &&
			(loginNameCoordinadorReceptor == null || "".equals(loginNameCoordinadorReceptor))){
			//Fuerzo el posteo del error
			validationUtils.assertNotEmpty("", "Debe seleccionar un destinatario del mensaje.");
		}
		
		return !validationUtils.hasErrors();
	}
	
	private Usuario buscarReceptor(){
		if (loginNameGrupoReceptor != null && !"".equals(loginNameGrupoReceptor)){
			for (Usuario usuario : receptoresGrupos){
				if (loginNameGrupoReceptor.equals(usuario.getLoginName())){
					return usuario;
				} 
			}
		}
		
		if (loginNameAyudanteReceptor != null && !"".equals(loginNameAyudanteReceptor)){
			for (Usuario usuario : receptoresAyudantes){
				if (loginNameAyudanteReceptor.equals(usuario.getLoginName())){
					return usuario;
				} 
			}
		}

		if (loginNameCoordinadorReceptor != null && !"".equals(loginNameCoordinadorReceptor)){
			for (Usuario usuario : receptoresCoordinadores){
				if (loginNameCoordinadorReceptor.equals(usuario.getLoginName())){
					return usuario;
				} 
			}
		}

		throw new RuntimeException("Receptor no encontrado en la lista"); // no se deberia entrar aca		
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	public String getLoginNameAyudanteReceptor() {
		return loginNameAyudanteReceptor;
	}

	public void setLoginNameAyudanteReceptor(String loginNameAyudanteReceptor) {
		this.loginNameAyudanteReceptor = loginNameAyudanteReceptor;
	}

	public String getLoginNameCoordinadorReceptor() {
		return loginNameCoordinadorReceptor;
	}

	public void setLoginNameCoordinadorReceptor(String loginNameCoordinadorReceptor) {
		this.loginNameCoordinadorReceptor = loginNameCoordinadorReceptor;
	}

	public String getLoginNameGrupoReceptor() {
		return loginNameGrupoReceptor;
	}

	public void setLoginNameGrupoReceptor(String loginNameGrupoReceptor) {
		this.loginNameGrupoReceptor = loginNameGrupoReceptor;
	}

	public List<Usuario> getReceptoresAyudantes() {
		return receptoresAyudantes;
	}

	public void setReceptoresAyudantes(List<Usuario> receptoresAyudantes) {
		this.receptoresAyudantes = receptoresAyudantes;
	}

	public List<Usuario> getReceptoresCoordinadores() {
		return receptoresCoordinadores;
	}

	public void setReceptoresCoordinadores(List<Usuario> receptoresCoordinadores) {
		this.receptoresCoordinadores = receptoresCoordinadores;
	}

	public List<Usuario> getReceptoresGrupos() {
		return receptoresGrupos;
	}

	public void setReceptoresGrupos(List<Usuario> receptoresGrupos) {
		this.receptoresGrupos = receptoresGrupos;
	}

}