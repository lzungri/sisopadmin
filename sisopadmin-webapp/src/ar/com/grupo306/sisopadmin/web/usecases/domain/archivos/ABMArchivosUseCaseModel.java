package ar.com.grupo306.sisopadmin.web.usecases.domain.archivos;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts.upload.FormFile;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.archivos.Archivo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para el CU de ABM de integrantes de la Cátedra.
 *
 * @author Damian
 */

public class ABMArchivosUseCaseModel extends ABMBaseUseCaseModel{
	private String ruta;
	private String nombre;
	private String descripcion;
	private Usuario usuarioCreador;
	private String nombreOriginal="";
	private int entradas=0;
	private FormFile archivoSubido;
	
	@Secure ({})
	@Description ("ABM Archivos de la Cátedra")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		Archivo archivo=null;
		Long idArchivo = (Long) context.getParameter("archivoSelected");
		if(idArchivo!=null && idArchivo!=-1){
			archivo= Archivo.findMeById(idArchivo);
		}
		
		if(archivo!=null){
			nombre = archivo.getName();
			descripcion = archivo.getDescripcion();
			ruta = archivo.getRuta();
		}
		
		if(entradas==0 && nombre!=null){
			nombreOriginal=nombre;
		}
		entradas++;
	}
	
	@Transactional
	public void altaArchivo(UseCaseContext context) {
		try {
			// Graba en disco el archivo.
			String uploadPath = SisopAdminConfig.getInstance().getProperty(Module.RECURSOS, "abmArchivos.uploadFiles.path");
			String downloadPath = SisopAdminConfig.getInstance().getProperty(Module.RECURSOS, "abmArchivos.downloadFiles.path");
			File archivoAGrabar = new File(uploadPath + archivoSubido.getFileName());
			
			if (!validacionOK(new UseCaseValidationUtils(context))) {
				return;
			}
			
			InputStream archivoIS = archivoSubido.getInputStream();
			OutputStream archivoOS = FileUtils.openOutputStream(archivoAGrabar);
			IOUtils.copy(archivoIS, archivoOS);

			archivoIS.close();
			archivoOS.close();

			// Graba en la bd la referencia al archivo.
			Usuario usuario;
			UserContext userContext = (UserContext)context.getUserContext();
			if(!userContext.isUserLogged()){
				throw new BusinessException("No se puede cargar un archivo ya que tiene que estar logueado el usuario en el sistema");
			}
			LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
			usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
			//Cuando esté arreglado el mapeo
			Archivo nuevoArchivo= new Archivo();
			nuevoArchivo.setNombre(nombre);
			nuevoArchivo.setDescripcion(descripcion);
			nuevoArchivo.setRuta(downloadPath + archivoSubido.getFileName());
			nuevoArchivo.save();
			
			context.addMessage("Archivo cargado exitosamente.");
			context.acceptUseCase();			
		}
		catch(Exception excep) {
			throw ExceptionFactory.createProgramException("Error al intentar subir el archivo " + archivoSubido.getFileName(), excep);
		}
	}
	
	
	@Transactional
	public void bajaArchivo(UseCaseContext context) {	
		try {
			Long idArchivo = (Long) context.getParameter("archivoSelected");
			Archivo borrarArchivo= Archivo.findMeById(idArchivo);
			String nombreArchivo = borrarArchivo.getRuta().split("/")[borrarArchivo.getRuta().split("/").length-1];
			
			// Borra de la bd la referencia al archivo.
			Usuario usuario;
			UserContext userContext = (UserContext)context.getUserContext();
			if(!userContext.isUserLogged()){
				throw new BusinessException("No se puede eliminar un archivo ya que tiene que estar logueado el usuario en el sistema");
			}
			LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
			usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
			//Cuando esté arreglado el mapeo
			borrarArchivo.remove();
			
			//Borra del disco el archivo.
			String uploadPath = SisopAdminConfig.getInstance().getProperty(Module.RECURSOS, "abmArchivos.uploadFiles.path");
			File archivoABorrar = new File(uploadPath + nombreArchivo);
			FileUtils.forceDelete(archivoABorrar);
			
			context.addMessage("Archivo eliminado exitosamente.");
			context.acceptUseCase();			
		}
		catch(Exception excep) {
			throw ExceptionFactory.createProgramException("Error al intentar eliminar el archivo seleccionado.", excep);
		}
	}
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils) {
		validationUtils.assertNotEmpty(nombre,"Debe ingresar un nombre para el archivo.");
		//Si cambia el nombre, validar que se pueda asignar
		if(!nombreOriginal.equalsIgnoreCase(nombre)){
			validationUtils.assertTrue(Archivo.findMeBy("nombre", nombre)==0,"El nombre del archivo ya existe en el sistema.");
		}
		validationUtils.assertNotEmpty(descripcion,"Debe ingresar una descripción para el archivo.");
		validationUtils.assertTextoSeguro(nombre, " El nombre contiene caracteres no admitidos por el sistema.");
		validationUtils.assertTextoSeguro(descripcion, " La descripción contiene caracteres no admitidos por el sistema.");
		validationUtils.assertIsUploaded( archivoSubido,"El archivo que intenta subir no es válido.");
		return !validationUtils.hasErrors();
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

	public FormFile getArchivoSubido() {
		return archivoSubido;
	}

	public void setArchivoSubido(FormFile archivoSubido) {
		this.archivoSubido = archivoSubido;
	}

	public int getEntradas() {
		return entradas;
	}

	public void setEntradas(int entradas) {
		this.entradas = entradas;
	}

	public String getNombreOriginal() {
		return nombreOriginal;
	}

	public void setNombreOriginal(String nombreOriginal) {
		this.nombreOriginal = nombreOriginal;
	}
	
	

}