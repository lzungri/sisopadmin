package ar.com.grupo306.sisopadmin.web.usecases.domain.archivos;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.archivos.Archivo;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para Caso de uso de Administrar Archivos de la Cátedra.
 *
 * @author Damian
 */

public class AdministrarArchivosUseCaseModel extends BaseUseCaseModel{
	private String valorBusqueda;
	private String buscarPor;
	private String nombre;
	private String descripcion;
	private String path;
	private int entradas=0;
	private Long id;
	private Collection<Archivo> archivos = null;
	private Collection<Archivo> archivosById = null;
	private Long index;
	
	
	@Transactional
	public void initUseCase(UseCaseContext context) {
		if(entradas==0){
			archivos = Archivo.findMeByMostrarInicio();
		}
		super.initUseCase(context);
	}
	
	
	@Transactional
	public void buscarArchivo(UseCaseContext context){
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
				
		
		if(buscarPor!=null && buscarPor.equalsIgnoreCase("0")){
			archivos = Archivo.findLike("nombre",valorBusqueda);
		} else {
			archivos = Archivo.findLike("descripcion",valorBusqueda);
		}

		int size = 0;
		if(archivos!=null){
			size = archivos.size();
		}
		context.addMessage("Se han encontrado " + size + " elementos que coinciden con el filtro.");
		entradas++;
		
		context.refreshUseCase();				
	}
	
	@Secure ({})
	@Description ("Administración de Archivos (Alta)")
	@Transactional
	public void altaArchivo(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		context.goToChildUseCase(ABMArchivosUseCase.class, new CreateMode(), new HashMap(), "buscarArchivo");
	}
	
	@Secure ({})
	@Description ("Administración de Archivos (Eliminación)")
	@Transactional
	public void bajaArchivo(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		Map parametros = new HashMap();
		parametros.put("archivoSelected",index);
		context.goToChildUseCase(ABMArchivosUseCase.class, new RemoveMode(), parametros,"buscarArchivo");
		
	}
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils) {
		validationUtils.assertTextoSeguro(valorBusqueda,"El campo de busqueda contiene caracteres no admitidos por el sistema.");
		return !validationUtils.hasErrors();
	}
	
	
	// GETTER AND SETTERS 
	
	public String getBuscarPor() {
		return buscarPor;
	}

	public void setBuscarPor(String buscarPor) {
		this.buscarPor = buscarPor;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection getArchivos() {
		return archivos;
	}

	public int getTest() {
		return entradas;
	}

	public void setTest(int test) {
		this.entradas = test;
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}


	public Collection<Archivo> getArchivosById() {
		return archivosById;
	}


	public void setArchivosById(Collection<Archivo> archivosById) {
		this.archivosById = archivosById;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public int getEntradas() {
		return entradas;
	}


	public void setEntradas(int entradas) {
		this.entradas = entradas;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public void setArchivos(Collection<Archivo> archivos) {
		this.archivos = archivos;
	}
	

	
	

}
