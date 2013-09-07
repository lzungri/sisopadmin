package ar.com.grupo306.sisopadmin.persistence.tests.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.componentesGrupo.Alumno;


public class UseCaseModule {
	
	
	public List findPlantillaEncuestaById(Long id){
		List list = new ArrayList();
		list.add(PlantillaEncuesta.findMeById(id));
		return list;
	}
	
	public void crearPlantillaEncuesta(){
		PlantillaEncuesta enc = new PlantillaEncuesta();
		enc.setFechaAlta(new Date());
		enc.setFechaFin(new Date());
		enc.setNombre("Plantilla loca");
		enc.save();
	}
	
	 @Transactional
     public void testUsuarios(){
		 try{
	 				Ayudante ayudante = (Ayudante)Usuario.findMeByLoginName("pepin");
                    System.out.println(ayudante.getNombre() + " " + ayudante.getApellido());

                    Coordinador coordinador = new Coordinador();
                    coordinador.setNombre("Coordinator");
                    coordinador.save();

                    Set ayuAux = CollectionFactory.createSet(Ayudante.class);
                    ayuAux.add(ayudante);
                    ayudante.setApellido("Merlo III");
                    ayudante.update();
                    coordinador.setAyudantes(ayuAux);
                    coordinador.update();                                                                                    
         }catch(Exception e){
                   //error;puse una linea para poner un breakpoint….
                    int i = 1;

         }
	 }
	 
	 @Transactional
	    public void cargarGrupo(){
		 	
	        Set alumnos = CollectionFactory.createSet();
	        Alumno alumno1 = new Alumno();
	        alumno1.setApellido("PerezIV");
	        alumno1.setLegajo("12345");
	        alumno1.setNombre("JoseIV");
	        alumnos.add(alumno1);
	       
	        for (Iterator it=alumnos.iterator(); it.hasNext(); ) {
	               ((Alumno)it.next()).save();
	        }       
	       
	        Grupo nuevoGrupo = new Grupo();
	        nuevoGrupo.setNombre("GrupoTestII");
	        nuevoGrupo.setLoginName("namedeloginV");
	        nuevoGrupo.setEmail("el mail mas loco del mundo");   
	        nuevoGrupo.save();       
	        nuevoGrupo.setAlumnos(alumnos);
	        
	        for (Iterator it=alumnos.iterator(); it.hasNext(); ) {
	        		
	               Alumno alumno = ((Alumno)it.next());
	               alumno.setGrupo(nuevoGrupo);
	               alumno.update();
	        }       
	        nuevoGrupo.update();
	       
	    }
}
