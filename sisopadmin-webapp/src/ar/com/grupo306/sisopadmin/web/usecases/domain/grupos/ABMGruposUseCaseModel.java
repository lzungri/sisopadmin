/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.grupos;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.componentesGrupo.Alumno;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.estadosGrupo.EstadoGrupo;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;

/**
 * @author Sole
 *
 */
public class ABMGruposUseCaseModel extends ABMBaseUseCaseModel {

	private String nombre;
	private String nombreUsuario;
	private String email;
	private Set<Alumno> alumnos = CollectionFactory.createSet(Alumno.class);
	
	// Atributos del Alumnos a crear
	private String nombreAlumno;
	private String apellidoAlumno;
	private String legajoAlumno;
	private String emailAlumno;
	
	private Integer index;	

	@Override
	public void initUseCase(UseCaseContext context) {
		setMode(new CreateMode());
		super.initUseCase(context);
	}
	
	public void agregarIntegrante(UseCaseContext context){
		if(!validaAlumno(context))return;
		
		Alumno nuevoAlumno = new Alumno();
		nuevoAlumno.setNombre(this.getNombreAlumno());
		nuevoAlumno.setApellido(this.getApellidoAlumno());
		nuevoAlumno.setLegajo(this.getLegajoAlumno());
		nuevoAlumno.setEmail(this.getEmailAlumno());
		alumnos.add(nuevoAlumno);		
		context.refreshUseCase();
	}	
	
	public void eliminarIntegrante(UseCaseContext context){
		//Alumno removeAlumno=  alumnos.get(this.getIndex());
		//alumnos.remove(removeAlumno);
		context.refreshUseCase();		
	}
	
	public void modificarIntegrante(UseCaseContext context){
		//TODO: Hacer
	}	
	
	@Transactional
	public void cargarGrupo(UseCaseContext context){
	
		if(!validaGrupo(context)) return;
		
		for (Iterator it=alumnos.iterator(); it.hasNext(); ) {
		       ((Alumno)it.next()).save();
		}		
		
		Grupo nuevoGrupo = new Grupo();
		nuevoGrupo.setNombre(this.getNombre());
		nuevoGrupo.setLoginName(this.getNombreUsuario());
		nuevoGrupo.setEmail(this.getEmail());	
		nuevoGrupo.save();		
		
		nuevoGrupo.setAlumnos(this.getAlumnos());
		
		for (Iterator it=alumnos.iterator(); it.hasNext(); ) {
			Alumno alumno = ((Alumno)it.next()); 
			alumno.setGrupo(nuevoGrupo);   
			alumno.update();
		}		
		nuevoGrupo.setEstado(EstadoGrupo.REGISTRADO);
		nuevoGrupo.update();
		//TODO: madar mail de confirmación.
		
		context.acceptUseCase();		
	}
	
	public void volver(UseCaseContext context){
		context.cancelUseCase();
	}
	
	private Boolean validaAlumno(UseCaseContext context){
		if("".compareTo(this.getNombreAlumno()) == 0 )
			context.addErrorMessage("Debe ingresar un nombre para el alumnno");
		
		if("".compareTo(this.getApellidoAlumno()) == 0 )
			context.addErrorMessage("Debe ingresar un apellido para el alumnno");
			
		Pattern pLegajo = Pattern.compile("\\d{3}\\.\\d{3}-\\d");
	    Matcher mLegajo = pLegajo.matcher(this.getLegajoAlumno());	   
        if (! mLegajo.matches()){
        	context.addErrorMessage("Debe ingresar un legajo válido para el alumno");		
		}		
		
		Pattern pMail = Pattern.compile(".+@.+\\.[a-z]+");
	    Matcher mMail = pMail.matcher(this.getEmailAlumno());	   
        if (! mMail.matches()){
        	context.addErrorMessage("Debe ingresar una dirección de correo electrónico válida para el alumno");		
		}        
        return (!context.hasErrors());		
	}
	
	private Boolean validaGrupo(UseCaseContext context){
		
		if("".compareTo(this.getNombreAlumno()) == 0 )
			context.addErrorMessage("Debe ingresar un nombre para el grupo");
		else{
			if(Grupo.findMeByName(this.getNombre()) != null)
				context.addErrorMessage("El nombre del grupo ya existe");
		}
		
		if("".compareTo(this.getNombreUsuario()) == 0 )
			context.addErrorMessage("Debe ingresar un nombre de usuario");
		else{
			if(Usuario.findMeByLoginName(this.getNombreUsuario()) != null)
				context.addErrorMessage("El nombre de usuario ya existe");
		}
				
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	    Matcher m = p.matcher(this.getEmail());	   
        if (! m.matches()){
        	context.addErrorMessage("Debe ingresar una dirección de correo electrónico válida para el grupo");		
		}	
        
        if(alumnos.size() == 0)
        	context.addErrorMessage("Debe ingresar los integrantes del grupo");
        
        return (!context.hasErrors());    
		
	}

	public Set<Alumno> getAlumnos() {
		return alumnos;
	}
	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
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
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
}
