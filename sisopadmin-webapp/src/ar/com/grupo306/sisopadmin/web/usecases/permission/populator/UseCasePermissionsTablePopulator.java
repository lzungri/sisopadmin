package ar.com.grupo306.sisopadmin.web.usecases.permission.populator;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;
import ar.com.grupo306.sisopadmin.web.usecases.permission.PermisoUseCaseTransformer;
import ar.com.grupo306.sisopadmin.web.usecases.register.SisopAdminUseCaseRegister;
import ar.com.grupo306.usecasefwk.usecases.registry.UseCaseRegistry;

/**
 * Popula los permisos requeridos por los Casos de uso.
 * 
 * @author Leandro
 */
public class UseCasePermissionsTablePopulator extends TablePopulator {
	
	public void populate() {
		UseCaseRegistry useCaseRegistry = UseCaseRegistry.getInstance();
		
		// Se registran los Casos de uso propios de Sisop admin.
		new SisopAdminUseCaseRegister().registerUseCases(useCaseRegistry);
		
		// Se crea la Collection de Collection de permisos por UseCase.
		Collection metaCollectionPermisos = useCaseRegistry.transform(new PermisoUseCaseTransformer());
		final Set<Permiso> permisos = CollectionFactory.createSet(Permiso.class);
		
		// Ahora sí, se genera la Collection de Permisos.
		CollectionUtils.forAllDo(metaCollectionPermisos, new Closure() {
			public void execute(Object object) {
				permisos.addAll((Collection) object);
			}
		});
		
		for(Permiso permiso : permisos) {
			save(permiso);
		}
	}
	
}