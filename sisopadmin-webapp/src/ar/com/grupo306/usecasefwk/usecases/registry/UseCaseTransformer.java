package ar.com.grupo306.usecasefwk.usecases.registry;

import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;

/**
 * Convierte un UseCase en otro tipo de objeto.
 *
 * @author Leandro
 */
public interface UseCaseTransformer {
	
	/**
	 * Convierte un UseCase a otro Object.
	 * 
	 * @param useCase
	 */
	public Object transform(UseCase useCase);

}