package ar.com.grupo306.sisopadmin.web.usecases.permission;

import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.commons.lang.ClassUtils;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.registry.UseCaseTransformer;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;

/**
 * Transforma un UseCase en Permisos para su ejecución.
 *
 * @author Leandro
 */
public class PermisoUseCaseTransformer implements UseCaseTransformer {

	/**
	 * Retorna una Collection con los Permisos requeridos para el UseCase.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Object transform(UseCase useCase) {
		Collection<Permiso> permisos = CollectionFactory.createCollection(Permiso.class);
		
		Method []methods = useCase.useCaseModelClass().getMethods();
		
		try {
			for(Method method : methods) {
				if(UseCaseUtils.isSecureMethod(method)) {
					permisos.add(this.createPermiso(useCase.useCaseModelClass(), method));
					// Se crea un permiso por cada Modo.
					for(Class<? extends UseCaseModelMode> modeClass : UseCaseUtils.getSecureModes(useCase, method.getName(), method.getParameterTypes())) {
						permisos.add(this.createPermiso(useCase.useCaseModelClass(), modeClass, method));
					}
				}
			}
		}
		catch(Exception excep) {
			throw ExceptionFactory.createProgramException("Error al transformar un UseCase en Permisos.", excep);
		}
		
		return permisos;
	}
	
	/**
	 * Dada un Class de UseCaseModel y un método retorna el permiso asociado
	 * al mismo.
	 * 
	 * @param useCaseModelClass
	 * @param method
	 */
	public Permiso createPermiso(Class useCaseModelClass, Method method) {
		Permiso permiso = this.createPermiso(useCaseModelClass, method.getName());
		this.addDescription(useCaseModelClass, permiso, method);
		
		return permiso;
	}
	
	public Permiso createPermiso(Class useCaseModelClass, Class<? extends UseCaseModelMode> modeClass, Method method) throws InstantiationException, IllegalAccessException {
		Permiso permiso = this.createPermiso(useCaseModelClass, modeClass, method.getName());
		this.addDescription(useCaseModelClass, permiso, method);
		
		return permiso;
	}	
	
	public Permiso createPermiso(Class useCaseModelClass, String methodName) {
		return new Permiso(useCaseModelClass.getName() + "." + methodName, methodName);
	}
	
	public Permiso createPermiso(Class useCaseModelClass, Class<? extends UseCaseModelMode> modeClass, String methodName) throws InstantiationException, IllegalAccessException {
		return new Permiso(useCaseModelClass.getName() + "." + methodName + "." + modeClass.newInstance().getModeIdentifier(), methodName);
	}
	
	private void addDescription(Class useCaseModelClass, Permiso permiso, Method method) {
		Description descAnnotation = method.getAnnotation(Description.class);
		if(descAnnotation != null) {
			permiso.setDescription(ClassUtils.getShortClassName(useCaseModelClass).replaceFirst("UseCaseModel", "") + " - " + descAnnotation.value());
		}
		else {
			System.out.println("WARNING: El método '" + method.getName() + "' del UseCaseModel '" + method.getDeclaringClass() + "' no posee asociada una Description.");
		}
	}
	
}