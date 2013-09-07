/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.grupos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.Predicate;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.commons.mailSender.Mail;
import ar.com.grupo306.commons.utils.PasswordUtils;
import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.mensajes.Mensaje;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Entrega;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFasePorPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.InformeEvaluacionFase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.componentesGrupo.Alumno;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.estadosGrupo.EstadoGrupo;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCaseModel;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * @author Sole
 *
 */
public class ABMGruposUseCaseModel extends ABMBaseUseCaseModel {
	private Grupo grupo = new Grupo();	
	private Alumno alumnoAModificar = null;
	private Boolean modificandoAlumno = false;
	private Set<Alumno> integrantesEliminados = CollectionFactory.createSet(Alumno.class);
	private Integer cantMaxAlum = 0;
	private Boolean alumnoVisible = false;
	private String ayudanteAsignado = "";
	
	
	// Atributos del Alumnos a crear
	private String nombreAlumno;
	private String apellidoAlumno;
	private String legajoAlumno;
	private String emailAlumno;
	
	private String index;	

	@Override
	@Transactional
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);
		cantMaxAlum = Integer.parseInt(SisopAdminConfig.getInstance().getProperty(Module.GRUPOS, "cantidadMaximaIntegrantes"));		
	}
	
	@Override
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {
		grupo = (Grupo) modelObject;
		if (grupo.getAyudante() != null)
			ayudanteAsignado = grupo.getAyudante().getNombre() + " " + ((Ayudante)grupo.getAyudante()).getApellido();		
	}
	
	public void agregarIntegrante(UseCaseContext context){
		Alumno alumno = null;
		if(!validacionAlumnoOK(new UseCaseValidationUtils(context))) return;
		
		if(!modificandoAlumno){
			alumno = new Alumno();			
		}
		else{
			alumno = alumnoAModificar;
			this.setAlumnoVisible(false);
			modificandoAlumno = false;
		}		
		alumno.setNombre(this.getNombreAlumno());
		alumno.setApellido(this.getApellidoAlumno());
		alumno.setLegajo(this.getLegajoAlumno());
		alumno.setEmail(this.getEmailAlumno());	
		LimpiarCamposAlumno();	
		grupo.getAlumnos().add(alumno);		
		context.refreshUseCase();		
	}	
	
	
	public void cancelarModificarIntegrante(UseCaseContext context){
		modificandoAlumno = false;		
		LimpiarCamposAlumno();
		this.setAlumnoVisible(false);
		context.refreshUseCase();
	}
	
	public void eliminarIntegrante(UseCaseContext context){
		integrantesEliminados.add(this.selectAlumno(context));
		grupo.getAlumnos().remove(this.selectAlumno(context));		
		context.refreshUseCase();		
	}
	
	@Transactional
	public void confirmar(UseCaseContext context){		
		grupo.setEstado(EstadoGrupo.CONFIRMADO_MANUAL);
		grupo.update();
		context.acceptUseCase();
	}	
	
	public void modificarIntegrante(UseCaseContext context){
		this.alumnoAModificar = this.selectAlumno(context);
		modificandoAlumno = true;
		this.setNombreAlumno(alumnoAModificar.getNombre());
		this.setApellidoAlumno(alumnoAModificar.getApellido());
		this.setEmailAlumno(alumnoAModificar.getEmail());
		this.setLegajoAlumno(alumnoAModificar.getLegajo());		
		this.setAlumnoVisible(true);		
	}	
	
	private Alumno selectAlumno(UseCaseContext context){
		if(context.getRequestParameter("useCaseModel.index") == null) 
			return null;
		else{
			try{				
				return (Alumno) grupo.getAlumnos().toArray()[Integer.parseInt(index)];
			}
			catch(IndexOutOfBoundsException excep) {
				throw ExceptionFactory.createBusinessException("Ha intentado seleccionar un elemento inexistente.");
			}
		}
	}	
	
	@Transactional
	public void createAccept(UseCaseContext context){
	
		if(!validacionGrupoOK(new UseCaseValidationUtils(context)))  return;
		
		for (Iterator it=grupo.getAlumnos().iterator(); it.hasNext(); ) {
		       ((Alumno)it.next()).save();
		}
	
		grupo.save();	
		
		for (Iterator it=grupo.getAlumnos().iterator(); it.hasNext(); ) {
			Alumno alumno = ((Alumno)it.next()); 
			alumno.setGrupo(grupo);   
			alumno.update();
		}		
		grupo.setEstado(EstadoGrupo.REGISTRADO);		
		grupo.addRol(Rol.findMeByDomainCode(Rol.ROL_GRUPO_REGISTRADO));
		grupo.setFechaAlta(new Date());
		grupo.setPassword(new String(PasswordUtils.generateStrongPassword(6),0,6));
		grupo.update();	
		
		mandarMailConfirmacion();	
		context.addMessage("El grupo ha sido registrado exitosamente. Puede loguearse en la aplicación con la contraseña " + grupo.getPassword());
		
		context.acceptUseCase();		
	}
	
	@Transactional
	public void editAccept(UseCaseContext context){
	
		if(!validacionGrupoOK(new UseCaseValidationUtils(context)))  return;
						
		for (Iterator it=grupo.getAlumnos().iterator(); it.hasNext(); ) {
			Alumno alumno = ((Alumno)it.next()); 
			alumno.setGrupo(grupo);   
			alumno.saveOrUpdate();
		}
		
		for(Iterator it= integrantesEliminados.iterator();it.hasNext();){
			((Alumno)it.next()).remove();
		}		
		grupo.update();		
		context.acceptUseCase();		
	}
	
	@Transactional
	public void removeAccept(UseCaseContext context){
		grupo = Grupo.findMeByName(grupo.getNombre());
		if(grupo != null) {
			grupo.remove();		
			context.acceptUseCase();		
		}
		else {
			context.addErrorMessage("El grupo indicado no existe, no se realiza la operación de eliminación.");
		}
	}
	
	public void volver(UseCaseContext context){
		context.cancelUseCase();
	}
	
	
	private boolean validacionAlumnoOK(UseCaseValidationUtils validationUtils){		
		validationUtils.assertNotEmpty(nombreAlumno, "Debe ingresar un nombre para el alumnno");
		validationUtils.assertNotEmpty(apellidoAlumno, "Debe ingresar un apellido para el alumnno");
		validationUtils.assertValidLegajo(legajoAlumno, "Debe ingresar un legajo válido para el alumno. Ejemplo: 114.894-1");
		validationUtils.assertValidMail(emailAlumno, "Debe ingresar una dirección de correo electrónico válida para el alumno");
		
		//ValidarLegajoRepetido
		Boolean repetido = false;
		for (Iterator it=grupo.getAlumnos().iterator(); it.hasNext(); ) {
			Alumno alum = (Alumno)it.next(); 
			if((!modificandoAlumno ||( modificandoAlumno && !alum.equals(alumnoAModificar))) && alum.getLegajo().compareTo(legajoAlumno) == 0)
				repetido = true;
		}
		validationUtils.assertFalse(repetido, "No puede ingresar dos alumnos con el mismo legajo");
		
		
		return !validationUtils.hasErrors();	
	}
	
	private Boolean validacionGrupoOK(UseCaseValidationUtils validationUtils){
		Boolean nombreRepetido = false;
		Boolean loginRepetido = false;		
		
		validationUtils.assertNotEmpty(grupo.getNombre(), "Debe ingresar un nombre para el grupo");		
		if(this.getMode().equals(new CreateMode()) && "".compareTo(grupo.getNombre()) != 0){
			nombreRepetido = Grupo.findMeByName(grupo.getNombre()) != null;
		}
		validationUtils.assertFalse(nombreRepetido,"El nombre del grupo ya existe" );
		
		validationUtils.assertNotEmpty(grupo.getLoginName(), "Debe ingresar un nombre de usuario");
		validationUtils.assertTrue(!grupo.getLoginName().contains(" "), "El nombre de usuario no puede contener espacios en blanco");
		if(this.getMode().equals(new CreateMode()) && "".compareTo(grupo.getLoginName()) != 0){
			loginRepetido = Usuario.findMeByLoginName(grupo.getLoginName()) != null;
		}
		validationUtils.assertFalse(loginRepetido,"El nombre de usuario ya existe" );
		
		validationUtils.assertValidMail(grupo.getEmail(), "Debe ingresar una dirección de correo electrónico válida para el grupo");
		validationUtils.assertTrue(grupo.getAlumnos().size() > 0, "Debe ingresar los integrantes del grupo");
		validationUtils.assertTrue(grupo.getAlumnos().size() <= cantMaxAlum, "Un grupo no puede tener más de " + cantMaxAlum + " integrantes");
				
		
		
		return !validationUtils.hasErrors();
	}
	
	@Secure({})
	@Description ("Seleccionar un ayudante para asignarle")
	public void seleccionarAyudante(UseCaseContext context){
		Predicate filter = new Predicate() {
			public boolean evaluate(Object object) {
				return (((Ayudante) object).getGrupos() != null) && (((Ayudante) object).getGrupos().size() < ((Ayudante) object).getMaximaCantidadGrupos()); 
			}
		};
		Map parameters = CollectionFactory.createMap();
		parameters.put(AdminBaseUseCaseModel.SEARCH_FILTER_KEY, filter);
		
		context.goToChildUseCase(AdministrarIntegranteUseCase.class, new SelectionMode(), parameters , "returnFromSelectAyudante");
	}
	
	public void returnFromSelectAyudante (UseCaseContext context){
		if(context.getReturnedModel() != null){
			AdministrarIntegranteUseCaseModel model = (AdministrarIntegranteUseCaseModel)context.getReturnedModel();
			grupo.setAyudante((Ayudante)model.getSelectedEntities().iterator().next());
			ayudanteAsignado = grupo.getAyudante().getNombre() + " " + ((Ayudante)grupo.getAyudante()).getApellido();		
			context.refreshUseCase();
		}
		else{ // si presiona cancelar se desselecciona.
			grupo.setAyudante(null);
			this.ayudanteAsignado = "";
		}
			
	}
	
	public String getLegajoAlumno() {
		return legajoAlumno;
	}
	public void setLegajoAlumno(String legajoAlumno) {
		this.legajoAlumno = legajoAlumno;
	}
	public String getApellidoAlumno() {
		return apellidoAlumno;
	}
	public void setApellidoAlumno(String apellidoAlumno) {
		this.apellidoAlumno = apellidoAlumno;
	}
	public String getNombreAlumno() {
		return nombreAlumno;
	}
	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}
	public String getEmailAlumno() {
		return emailAlumno;
	}
	public void setEmailAlumno(String emailAlumno) {
		this.emailAlumno = emailAlumno;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Integer getCantMaxAlum() {
		return cantMaxAlum;
	}

	public void setCantMaxAlum(Integer cantMaxAlum) {
		this.cantMaxAlum = cantMaxAlum;
	}

	public String getAyudanteAsignado() {
		return ayudanteAsignado;
	}

	public void setAyudanteAsignado(String ayudanteAsignado) {
		this.ayudanteAsignado = ayudanteAsignado;
	}	
	
	private void mandarMailConfirmacion(){
		//Parametros, si estan vacios usa los del arch. de conf.
		String senderAddress="";
		String senderName="";
		String mailSubject="Grupo registrado exitosamente";		
		
		//Mails de los destinatatios
		ArrayList<String> destinatarios = new ArrayList();		
		destinatarios.add(grupo.getEmail());	
								
		Mail mail = new Mail(senderAddress, senderName, mailSubject, "bodyTemplateAltaGrupo");		
		mail.addClavesRemplazo("NombreDelGrupo", grupo.getNombre());
		mail.addClavesRemplazo("LoginDelGrupo", grupo.getLoginName());		
		mail.addClavesRemplazo("Passwd",grupo.getPassword());		
		mail.setMailSubject(mailSubject);	
		
		mail.doSendMails(destinatarios, new ArrayList() );
		}

	public Boolean getAlumnoVisible() {
		return alumnoVisible;
	}

	public void setAlumnoVisible(Boolean alumnoVisible) {
		this.alumnoVisible = alumnoVisible;
	}

	public Boolean getModificandoAlumno() {
		return modificandoAlumno;
	}

	public void setModificandoAlumno(Boolean modificandoAlumno) {
		this.modificandoAlumno = modificandoAlumno;
	}
	
	private void LimpiarCamposAlumno(){
		nombreAlumno = "";
		apellidoAlumno = "";
		legajoAlumno = "";
		emailAlumno = "";	
	}
}
