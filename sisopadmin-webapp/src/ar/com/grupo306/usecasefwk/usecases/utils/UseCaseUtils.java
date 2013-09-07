package ar.com.grupo306.usecasefwk.usecases.utils;

import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.sisopadmin.web.usecases.permission.PermisoUseCaseTransformer;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;

/**
 * Utilidades varias para UseCases.
 *
 * @author Leandro
 */
public class UseCaseUtils {
	
	/**
	 * Indica si el método es seguro, para ello analiza si tiene la Annotation Secure.
	 * 
	 * @param method
	 *
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static boolean isSecureMethod(Method method) {
		return method.getAnnotation(Secure.class) != null;
	}
	
	/**
	 * Dado un UseCase indica si el método, perteneciente al UseCaseModel asociado
	 * a él, está registrado como seguro.
	 * 
	 * @param useCase
	 * @param methodName
	 * @param params
	 *
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static boolean isSecureMethod(UseCase useCase, String methodName, Class []params) throws SecurityException, NoSuchMethodException {
		Class modelClass = useCase.useCaseModelClass();
		
		return isSecureMethod(modelClass.getMethod(methodName, params));
	}
	
	/**
	 * Retorna una lista de modos seguros para un método de useCase.
	 * 
	 * @param useCase
	 * @param methodName
	 * @param params
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Collection<Class<? extends UseCaseModelMode>> getSecureModes(UseCase useCase, String methodName, Class []params) throws SecurityException, NoSuchMethodException {
		Method method = useCase.useCaseModelClass().getMethod(methodName, params);
		Secure secureAnnot = method.getAnnotation(Secure.class);
		
		return secureAnnot != null ? CollectionFactory.createList(secureAnnot.value()) : CollectionFactory.createList();
	}
	
	/**
	 * Dado un UseCase, indica si el permiso asociado al UseCaseModel.methodName
	 * para el modo indicado se encuentra incluído en la Collection de permisos.
	 * 
	 * Si el método no está registrado como seguro se retorna true.
	 * 
	 * @param useCase
	 * @param mode
	 * @param methodName
	 * @param params
	 * @param permisos
	 * 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static boolean hasPermission(UseCase useCase, UseCaseModelMode mode, String methodName, Class []params, Collection<Permiso> permisos) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		if(!isSecureMethod(useCase, methodName, params)) {
			return true;
		}
		
		Class modelClass = useCase.useCaseModelClass();
		Permiso permisoRaiz = new PermisoUseCaseTransformer().createPermiso(modelClass, methodName);
		
		if(containsPermiso(permisos, permisoRaiz)) {
			// Se halló el permiso raíz. Ahora se busca el asociado al mode (si se definió).
			if(mode != null && getSecureModes(useCase, methodName, params).contains(mode.getClass())) {
				Permiso permisoMode = new PermisoUseCaseTransformer().createPermiso(modelClass, mode.getClass(), methodName);
				return containsPermiso(permisos, permisoMode);
			}
			else {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Dado un UseCase, indica si el permiso asociado al UseCaseModel.methodName
	 * para el modo indicado se encuentra incluído en la Collection de permisos.
	 * 
	 * Se asume que la firma del método es methodName(UseCaseContext).
	 * 
	 * Si el método no está registrado como seguro se retorna true.
	 * 
	 * @param useCase
	 * @param mode
	 * @param methodName
	 * @param permisos
	 * 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static boolean hasPermission(UseCase useCase, UseCaseModelMode mode, String methodName, Collection<Permiso> permisos) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		return hasPermission(useCase, mode, methodName, new Class [] {UseCaseContext.class}, permisos);
	}
	
	
	private static boolean containsPermiso(Collection<Permiso> permisos, Permiso permiso) {
		for(Permiso permisoIter : permisos) {
			if(permisoIter.getDomainCode().equals(permiso.getDomainCode())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Dado un UseCase retorna el path asociado a él.
	 * 
	 * @param useCase
	 */
	public static String getUseCasePath(UseCase useCase) {
		return StringUtils.uncapitalize(ClassUtils.getShortClassName(useCase.getClass()));
	}
	
	/**
	 * Dado un UseCase retorna el path de inicio asociado a él.
	 * 
	 * @param useCase
	 */
	public static String getUseCaseInitPath(UseCase useCase) {
		return "/" + getUseCasePath(useCase) + ".do?reqCode=initUseCase";
	}

}