/**
 * 
 */
package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.util.Set;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Consigna;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.ItemPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.PlantillaCorreccion;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * @author Sole
 *
 */
public class TrabajoPracticoTablePopulator extends TablePopulator {
	private Fase fase1_emulaso;
	private Consigna consigna3_fase1;
	private Consigna consigna4_fase1;
	
	public void populateTrabajoPractico() {
		Tp tp = new Tp();
		tp.setNombre("Emulaso");
		tp.setArchivoEspecificacion("TP20072C-EMULASO.pdf");
		tp.save();
		
		// FASE 1
		
		Fase fase1 = new Fase();
		fase1.setNombre("MsShell y ADS Completos");
		fase1.setNumero(1);
		try{
			fase1.setFechaInicio(DateUtils.getDateFromString("01092007", "ddMMyyyy"));
		}
		catch(Exception ex){
			System.out.println("Fecha inválida");
		}		
		fase1.setFechaFin(DateUtils.addDays(fase1.getFechaInicio(), 15));
		fase1.setEntregaObligatoria(false);
		fase1.save();
		fase1_emulaso = fase1;
		
		
		Consigna consigna1 = new Consigna();
		consigna1.setNumero(1);
		consigna1.setDescripcion("Desarrollar la totalidad de las funcionalidades del proceso MShell");
		consigna1.save();
		
				
		Consigna consigna2 = new Consigna();
		consigna2.setNumero(2);
		consigna2.setDescripcion("Desarrollar la totalidad de las funcionalidades del proceso ADS, salvo las que requieran la existencia del proceso ACR");
		consigna2.save();	
		
		Consigna consigna4 = new Consigna();
		consigna4.setNumero(3);
		consigna4.setDescripcion("El proceso MShell y el proceso ADS deben estar conectados permanentemente, y la comunicación entre ellos debe ser posible en todo momento");
		consigna4.save();
		consigna4_fase1 = consigna4;
		
		
		Consigna consigna3 = new Consigna();
		consigna3.setNumero(4);
		consigna3.setDescripcion("Comunicar el proceso MShell con el proceso ADS mediante un canal encriptado");
		consigna3.save();
		consigna3_fase1 = consigna3;
		
		Set<Consigna> consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna1);
		consignas.add(consigna2);
		consignas.add(consigna3);
		consignas.add(consigna4);
		fase1.setConsignas(consignas);
		
		consigna1.setFase(fase1);
		consigna1.update();
		consigna2.setFase(fase1);
		consigna2.update();
		consigna3.setFase(fase1);
		consigna3.update();
		consigna4.setFase(fase1);
		consigna4.update();		
		
		fase1.update();
		
		// FASE 2
		
		Fase fase2 = new Fase();		
		fase2.setNombre("ACR y ADP, balanceo de carga");
		fase2.setNumero(2);
		fase2.setFechaInicio(fase1.getFechaFin());
		fase2.setFechaFin(DateUtils.addDays(fase2.getFechaInicio(), 7));
		fase2.setEntregaObligatoria(false);
		fase2.save();
		
		consigna1 = new Consigna();
		consigna1.setNumero(1);
		consigna1.setDescripcion("Desarrollar las funcionalidades del ACR y ADP necesarias para determinar el nodo de red de carga sobre el cual migrar");
		consigna1.save();		
		
		consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna1);
		
		consigna1.setFase(fase2);
		consigna1.update();
				
		fase2.setConsignas(consignas);
		fase2.update();
		
		// FASE 3
		
		Fase fase3 = new Fase();
		fase3.setNombre("PPCB Completo");
		fase3.setNumero(3);
		fase3.setFechaInicio(fase2.getFechaFin());				
		fase3.setFechaFin(DateUtils.addDays(fase3.getFechaInicio(), 15));
		fase3.setEntregaObligatoria(true);
		fase3.save();
		
		
		consigna1 = new Consigna();
		consigna1.setNumero(1);
		consigna1.setDescripcion("Desarrollar el PPCB completo");
		consigna1.save();
						
		consigna2 = new Consigna();
		consigna2.setNumero(2);
		consigna2.setDescripcion("Permitir  al usuario que ejecute un programa desde el Mshell y que el PPCB creado en el ACR migre al nodo de red");
		consigna2.save();		
		
		consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna1);
		consignas.add(consigna2);		
		fase3.setConsignas(consignas);
		
		consigna1.setFase(fase3);
		consigna1.saveOrUpdate();
		consigna2.setFase(fase3);
		consigna2.saveOrUpdate();
		
		fase3.update();
				
		// FASE 4
		
		Fase fase4 = new Fase();
		fase4.setNombre("ADP completo");
		fase4.setNumero(4);
		fase4.setFechaInicio(fase3.getFechaFin());				
		fase4.setFechaFin(DateUtils.addDays(fase4.getFechaInicio(), 22));
		fase4.setEntregaObligatoria(false);
		fase4.save();
		
		
		consigna1 = new Consigna();
		consigna1.setNumero(1);
		consigna1.setDescripcion("Desarrollar el ADP en su totalidad");
		consigna1.save();
						
		consigna2 = new Consigna();
		consigna2.setNumero(2);
		consigna2.setDescripcion("Planificar los PPCBs bajo el mando de el ADP, el pedido de recursos no debe ser desarrollado");
		consigna2.save();		
		
		consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna1);
		consignas.add(consigna2);		
		fase4.setConsignas(consignas);
		
		consigna1.setFase(fase4);
		consigna1.saveOrUpdate();
		consigna2.setFase(fase4);
		consigna2.saveOrUpdate();
		
		fase4.update();
		
		// FASE 5
		
		Fase fase5 = new Fase();
		fase5.setNombre("ACR, gestión y otorgamiento de recursos");
		fase5.setNumero(5);
		fase5.setFechaInicio(fase4.getFechaFin());				
		fase5.setFechaFin(DateUtils.addDays(fase5.getFechaInicio(), 15));
		fase5.setEntregaObligatoria(true);
		fase5.save();
		
		
		consigna1 = new Consigna();
		consigna1.setNumero(1);
		consigna1.setDescripcion("Desarrollar la totalidad de las funicones del ACR que permitan llevar a cabo la administración y otorgamiento de recursos compartidos");
		consigna1.save();
						
		consigna2 = new Consigna();
		consigna2.setNumero(2);
		consigna2.setDescripcion("Permitir al usuario ejecutar múltiples programas y visualizar los resultados que ellos generan");
		consigna2.save();		
		
		consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna1);
		consignas.add(consigna2);		
		fase5.setConsignas(consignas);
		
		consigna1.setFase(fase5);
		consigna1.saveOrUpdate();
		consigna2.setFase(fase5);
		consigna2.saveOrUpdate();
		
		fase5.update();
		
		// FASE 5
		
		Fase fase6 = new Fase();
		fase6.setNombre("Integración final");
		fase6.setNumero(6);
		fase6.setFechaInicio(fase5.getFechaFin());				
		fase6.setFechaFin(DateUtils.addDays(fase6.getFechaInicio(), 15));
		fase6.setEntregaObligatoria(true);
		fase6.save();
		
		
		consigna1 = new Consigna();
		consigna1.setNumero(1);
		consigna1.setDescripcion("Desarrollar por completo todos los módulos e integrarlos");
		consigna1.save();
						
		consigna2 = new Consigna();
		consigna2.setNumero(2);
		consigna2.setDescripcion("Entregar el sistema totalmente operativo");
		consigna2.save();		
		
		consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna1);
		consignas.add(consigna2);		
		fase6.setConsignas(consignas);
		
		consigna1.setFase(fase6);
		consigna1.saveOrUpdate();
		consigna2.setFase(fase6);
		consigna2.saveOrUpdate();
		
		fase6.update();
		
		//GENERAL
		
		Set<Fase> fases = CollectionFactory.createSet(Fase.class);
		fases.add(fase1);
		fases.add(fase2);
		fases.add(fase3);
				
		fase1.setTp(tp);
		fase1.update();
		fase2.setTp(tp);
		fase2.update();
		fase3.setTp(tp);
		fase3.update();
		fase4.setTp(tp);
		fase4.update();
		fase5.setTp(tp);
		fase5.update();
		fase6.setTp(tp);
		fase6.update();
		tp.update();		
	}
	
public void populatePlantillaCorrección1() {		
		PlantillaCorreccion plantilla = new PlantillaCorreccion();		
		plantilla.setObligatoria(true);		
		plantilla.setFase(fase1_emulaso);
		plantilla.setNombre("Requerimientos mínimos de evaluación");
		plantilla.setPeso(25);
		plantilla.save();
		
		ItemPlantilla item1 = new ItemPlantilla();
		item1.setConsigna(null);
		item1.setNombre("Documentación de la aplicación");
		item1.setObligatorio(true);
		item1.setProcedimiento("Verificar la existencia de: 1- Análisis de la estrategia 1.1- Explicación de los procesos utilizados y la comunicación entre ellos" + Character.LINE_SEPARATOR + "   1.2- Protocolos internos utilizados 2- Manual de usuario 3- Archivos de configuración explicados.");
		item1.setResultadoEsperado("Consigna realizada completa y satisfactoriamente");
		item1.setObservacionBajaCalificacion("El grupo debe conocer y comprender la importancia de la documentación ya que ésta será una de las principales fuentes de información para la entrega de feedback.");
		item1.setPeso(50);
		item1.save();
		
		ItemPlantilla item2 = new ItemPlantilla();		
		item2.setObligatorio(true);
		item2.setNombre("Compilación");
		item2.setProcedimiento("Verificar que la entrega posea un makefile y compilar la entrega");
		item2.setResultadoEsperado("Entrega con un makefile que funcione y que compile correctamente");
		item2.setObservacionBajaCalificacion("Es requisito obligatorio para evaluar el TP, que el mismo compile. Lea las NTP para mayor detalle");
		item2.setPeso(50);
		item2.save();
		
		Set<ItemPlantilla> items = CollectionFactory.createSet(ItemPlantilla.class);
		items.add(item1);
		items.add(item2);
		
		plantilla.setItems(items);
		plantilla.update();		
	}	 
	
public void populatePlantillaCorrección2() {		
	PlantillaCorreccion plantilla = new PlantillaCorreccion();		
	plantilla.setObligatoria(true);		
	plantilla.setFase(fase1_emulaso);
	plantilla.setNombre("Requerimientos básicos del tp en ejecución");
	plantilla.setPeso(50);
	plantilla.save();
	
	ItemPlantilla item1 = new ItemPlantilla();
	item1.setConsigna(null);
	item1.setNombre("Cantidad de procesos");
	item1.setObligatorio(true);
	item1.setProcedimiento("Verificar que se cumpla con cada proceso mencionado en el enunciado del TP");
	item1.setResultadoEsperado("Existencia de procesos MSShell y ADS");
	item1.setObservacionBajaCalificacion("Los procesos que se encuentran especificados son obligatorios");
	item1.setPeso(25);
	item1.save();
	
	ItemPlantilla item2 = new ItemPlantilla();		
	item2.setObligatorio(false);
	item2.setNombre("Verificar archivos logs");
	item2.setProcedimiento("Se deberá ir verificando en cada test que estos archivos sean generados correctamente y que se logueen las acciones realizadas por cada proceso.");
	item2.setResultadoEsperado("Archivos de logs generados correctamente");
	item2.setObservacionBajaCalificacion("Es requisito que en todas las fases se graben en un archivo Log los eventos significativos (hitos, errores, información, etc.) Esta información será de utilidad para el alumno y ayudante para comprender el comportamiento del proceso.");
	item2.setPeso(25);
	item2.save();
	
	ItemPlantilla item3 = new ItemPlantilla();		
	item3.setObligatorio(true);
	item3.setConsigna(consigna4_fase1);
	item3.setNombre("Verificar conexiones");
	item3.setProcedimiento("Levantar un proceso MSShell y un Proceso ADS, verificar la conexión entre los mismos.");
	item3.setResultadoEsperado("Los procesos deben haber establecido correctamente la comunicación");
	item3.setObservacionBajaCalificacion("Si los procesos no se comunican no pueden realizar intercambio de información, que es una de las principales funcionalidades de la aplicación ");
	item3.setPeso(50);
	item3.save();
	
	Set<ItemPlantilla> items = CollectionFactory.createSet(ItemPlantilla.class);
	items.add(item1);
	items.add(item2);
	items.add(item3);
	
	plantilla.setItems(items);
	plantilla.update();		
}	 

	public void populatePlantillaCorrección3() {		
	PlantillaCorreccion plantilla = new PlantillaCorreccion();		
	plantilla.setObligatoria(true);		
	plantilla.setFase(fase1_emulaso);	
	plantilla.setNombre("Comunicación Mshell ADS");
	plantilla.setPeso(25);
	plantilla.save();
	
	ItemPlantilla item1 = new ItemPlantilla();
	item1.setConsigna(consigna3_fase1);
	item1.setNombre("Encripción de datos");
	item1.setObligatorio(true);
	item1.setProcedimiento("Escribir algo, ver con el comando strace lo que se envía en el Mshell, leer lo que le llega al ADS, ver que lo que se imprime por pantalla es lo que se envio desde el mshell.");
	item1.setResultadoEsperado("Información que viaje encriptada");
	item1.setObservacionBajaCalificacion("Mantener la seguridad de esta comunicación es primordial, porque se transmiten datos sensibles mediante la misma, por ejemplo la contraseña del usuario");
	item1.setPeso(100);
	item1.save();
		
	
	Set<ItemPlantilla> items = CollectionFactory.createSet(ItemPlantilla.class);
	items.add(item1);	
	
	plantilla.setItems(items);
	plantilla.update();		
}	 

	
	public void populateTPColas() {
		Tp tp = new Tp();
		tp.setNombre("Programación de una cola dinámica");
		tp.setArchivoEspecificacion("TP20072C-COLASDINAMICAS.pdf");
		tp.save();
		
		// FASE 1
		
		Fase fase1 = new Fase();
		fase1.setNombre("Generar estructuras básicas");
		fase1.setNumero(1);
		try{
			fase1.setFechaInicio(DateUtils.getDateFromString("01082007", "ddMMyyyy"));
		}
		catch(Exception ex){
			System.out.println("Fecha inválida");
		}		
		fase1.setFechaFin(DateUtils.addDays(fase1.getFechaInicio(), 7));
		fase1.setEntregaObligatoria(false);
		fase1.save();
		
		
		Consigna consigna1 = new Consigna();
		consigna1.setNumero(1);
		consigna1.setDescripcion("El alumno deberá implementar una UNICA lista con los nodos definidos");		
		consigna1.save();
		
				
		Consigna consigna2 = new Consigna();
		consigna2.setNumero(2);
		consigna2.setDescripcion("Esta lista deberá poder ser recorrida según el estado de los procesos. Es decir, que la funcionalidad del programa deberá permitir distinguir y recorrer en forma independiente, por ejemplo, la Cola de Listos y la Cola de Terminados");
		consigna2.save();		
		
		Consigna consigna3 = new Consigna();
		consigna3.setNumero(3);
		consigna3.setDescripcion("La solución deberá estar codificada en lenguaje C/C++ para el compilador gcc, sobre el sistema operativo Linux");
		consigna3.save();
		
		
		Set<Consigna> consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna1);
		consignas.add(consigna2);
		consignas.add(consigna3);
		fase1.setConsignas(consignas);
		
		consigna1.setFase(fase1);
		consigna1.update();
		consigna2.setFase(fase1);
		consigna2.update();
		consigna3.setFase(fase1);
		consigna3.update();
		
		fase1.update();
		
		// FASE 2
		
		Fase fase2 = new Fase();		
		fase2.setNombre("Presentar Funcionalidad Básica");
		fase2.setNumero(2);
		fase2.setFechaInicio(fase1.getFechaFin());
		fase2.setFechaFin(DateUtils.addDays(fase2.getFechaInicio(), 7));
		fase2.setEntregaObligatoria(false);
		fase2.save();
		
		consigna1 = new Consigna();
		consigna1.setNumero(1);
		consigna1.setDescripcion("Los eventos de ingreso a la cola vendrán especificados en forma externa (i.e. en un archivo de texto) para permitir múltiples repeticiones y comparación entre los distintos grupos");
		consigna1.save();		
		
		consigna2 = new Consigna();
		consigna2.setNumero(2);
		consigna2.setDescripcion("Se deberá incluir la opción de simulación por números aleatorios, distribución uniforme");
		consigna2.save();
		
		consigna3 = new Consigna();
		consigna3.setNumero(3);
		consigna3.setDescripcion("En la línea de parámetros se especificará o bien el nombre del archivo o bien el valor del  nodo");
		consigna3.save();
		
		Consigna consigna4 = new Consigna();
		consigna4.setNumero(4);
		consigna4.setDescripcion("El programa deberá proveer los mecanismos de interrupción por operador");
		consigna4.save();		

		
		consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna1);
		consignas.add(consigna2);
		consignas.add(consigna3);
		consignas.add(consigna4);
		
		consigna1.setFase(fase2);
		consigna1.update();
		consigna2.setFase(fase2);
		consigna2.update();
		consigna3.setFase(fase2);
		consigna3.update();
		consigna4.setFase(fase2);
		consigna4.update();
				
		fase2.setConsignas(consignas);
		fase2.update();
		
		// FASE 3
		
		Fase fase3 = new Fase();
		fase3.setNombre("Presentar Funcionalidad Completa");
		fase3.setNumero(3);
		fase3.setFechaInicio(fase2.getFechaFin());				
		fase3.setFechaFin(DateUtils.addDays(fase3.getFechaInicio(), 15));
		fase3.setEntregaObligatoria(true);
		fase3.save();
		
		
		consigna1 = new Consigna();
		consigna1.setNumero(1);
		consigna1.setDescripcion("Utilizar Notación Húngara, Lenguaje ANSI C y Make-files");
		consigna1.save();
						
		consigna2 = new Consigna();
		consigna2.setNumero(2);
		consigna2.setDescripcion("Deberán redactarse un manual de uso y otro técnico para el programa que genera archivos de carga, el programa que genera archivos de configuración y para el Administrador de Colas");
		consigna2.save();		
		
		consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna1);
		consignas.add(consigna2);		
		fase3.setConsignas(consignas);
		
		consigna1.setFase(fase3);
		consigna1.saveOrUpdate();
		consigna2.setFase(fase3);
		consigna2.saveOrUpdate();
		
		fase3.update();
		
		
		//GENERAL
		
		Set<Fase> fases = CollectionFactory.createSet(Fase.class);
		fases.add(fase1);
		fases.add(fase2);
		fases.add(fase3);
				
		fase1.setTp(tp);
		fase1.update();
		fase2.setTp(tp);
		fase2.update();
		fase3.setTp(tp);
		fase3.update();	
		
		tp.update();		
	}
}
