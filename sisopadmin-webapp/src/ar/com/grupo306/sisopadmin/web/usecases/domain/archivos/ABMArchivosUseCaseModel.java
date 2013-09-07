package ar.com.grupo306.sisopadmin.web.usecases.domain.archivos;


import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.archivos.Archivo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

/**
 * Modelo para el CU de ABM de integrantes de la Cátedra.
 *
 * @author Damian
 */

public class ABMArchivosUseCaseModel extends BaseUseCaseModel{
	String ruta;
	String nombre;
	String descripcion;
	String archivo;
	Usuario usuarioCreador;
	
	@Secure ({})
	@Description ("ABM Información de la Cátedra")
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
	}
	
	@Transactional
	public void cargarArchivo(UseCaseContext context) {
		try{
			if (! validar(context)) return;
			
			Archivo nuevoArchivo= new Archivo();
			Usuario usuario;
			UserContext userContext = (UserContext)context.getUserContext();
			if(!userContext.isUserLogged()){
				throw new BusinessException("No se puede crear una encuesta ya que tiene que estar logueado el usuario en el sistema");
			}
			LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
			usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
			//Cuando esté arreglado el mapeo
			nuevoArchivo.setNombre(nombre);
			nuevoArchivo.setDescripcion(descripcion);
			nuevoArchivo.setRuta(ruta);
			String A = archivo;
			
			
			
			nuevoArchivo.setUsuarioCreador(usuario);
			
			nuevoArchivo.save();
			/**
			 * TODO 
			 * Cargar el Archivo!!
			 */
			
			context.addMessage("Archivo cargado exitosamente.");
			context.acceptUseCase();			
		}catch(Exception e){
			context.addElement("login.message", "No se pudo cargar el archivo");
			context.refreshUseCase();
		}
	}
	
	private Boolean validar(UseCaseContext context){
		Boolean valida = true;
		if(nombre.compareTo("") == 0){
			context.addErrorMessage("Debe ingresar el Nombre de la información");
			valida = false;
		}
		if(descripcion.compareTo("") == 0){
			context.addErrorMessage("Debe ingresar la Descripción de la información");
			valida = false;
		}
		if(ruta.compareTo("") == 0){
			context.addErrorMessage("Debe ingresar la Ruta al archivo");
			valida = false;
		}
		
		return valida;
	}

	// GETTER AND SETTERS
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}


}
