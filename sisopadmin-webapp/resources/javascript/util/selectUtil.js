/*
* Utilidades javaScript para trabajar con los options de los Selects
*
* @author Damian
*/

//Asigna los options seleccionados en el origen al destino.
function asignar(origen,destino){
	var arrayOptions = new Array();
	var selectOrigen = window.document.getElementById(origen);
	var selectDestino = window.document.getElementById(destino);
	var i=0;
	var count=0;
	//Creo una variable que contenga los options a mover
	for(i=0;i<selectOrigen.options.length;i++){
		if(selectOrigen.options[i].selected){
			arrayOptions[count] = new Option(selectOrigen.options[i].text,selectOrigen.options[i].value);
			count++;
		}
	}
	//Limpio la selección...
	selectOrigen.selectedIndex=-1;
	selectDestino.selectedIndex=-1;
	//Hago el pasaje...
	pasaje( arrayOptions, selectOrigen, selectDestino);
}

//Hace el pase de un select a otro.
function pasaje( arrayOptions, selectOrigen, selectDestino){
	var i=0;
	for(i;i<arrayOptions.length;i++){
		var opt = arrayOptions[i];
		//Agrego el option en el destino
		selectDestino.options.add(opt,orden(selectDestino,opt));
		//Remuevo el option en el origen
		selectOrigen.remove(buscarOptionIndex(selectOrigen, arrayOptions[i].text, arrayOptions[i].value));
	}
}

//Busca el index de un option en un select.
function buscarOptionIndex(select, text, value){
	var index=-1;
	var i=0;
	for(i;i<select.length;i++){
		var opt = select[i];
		if(opt.text==text){index=i}
	}
	return index;
}

//Busca por el texto del option donde tiene que agregarlo para mantener el orden
function orden(select, option){
	var i=0;
	for(i;i<select.length;i++){
		var opt = select[i];
		if(opt.text>option.text){return i}
	}
	return i;
}

function seleccionarTodos(id){
	var select = window.document.getElementById(id);
	var i=0;
	for(i;i<select.length;i++){
		select[i].selected=true;
	}

}

function ordenInicial(){
	pasaje(window.document.getElementById('permisosDelRol').options,window.document.getElementById('permisosDelRol'),window.document.getElementById('permisosDelRol'));
	pasaje(window.document.getElementById('usuariosDelRol').options,'usuariosDelRol','usuariosDelRol');
}