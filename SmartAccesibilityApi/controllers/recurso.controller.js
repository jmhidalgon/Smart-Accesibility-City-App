// Intermediario entre el recurso y la base de datos (MVC)
'use strict'

// Cargamos nuestro modelo de recurso
var Recurso = require('../models/recurso.model');
// Cargamos el modulo de encriptacion para la contraseña
var bcrypt = require('bcrypt-nodejs');
// Cargamos el módulo de servicio JWT
var jwt = require('../services/jwt.service');
// Modulos para trabajar con el sistema de ficheros del servidor
var fs = require('fs');
var path = require('path');
var HttpStatus = require('http-status-codes');
// Modulo paginate de moogose
var mongoosePaginate = require('mongoose-pagination');


function pruebas(req, res){
	res.status(HttpStatus.OK).send({
		message: 'Probando una acción del controlador de recurso del API REST con node y mongo'
	});
}

function guardarRecurso(req, res){
	var recurso = new Recurso();
	var parametros = req.body;

	recurso.idEntidad = parametros.idEntidad;
	recurso.nombreRecurso = parametros.nombreRecurso;

	console.log("Registrar recurso");
	Recurso.findOne({idEntidad: recurso.idEntidad, nombreRecurso: recurso.nombreRecurso}, (err, u) =>{
		if(u != null){ 
			res.status(HttpStatus.METHOD_NOT_ALLOWED).send({message : 'El recurso ya esta registado para esa entidad'});
		} else {
			if(recurso.idEntidad != null && recurso.nombreRecurso != null){ // comprobamos que tenga datos
				// guardamos recurso
				recurso.save((err, recursoStored) => {
					if(err){ // Error al guardar
						console.log("0");

						res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error al guardar el recurso'});
					} else { // Guardado correctamente
						if(!recursoStored) { // Comprobamos que se ha guardado
							res.status(HttpStatus.BAD_REQUEST).send({message : 'No se ha resgistrado el recurso'});
						} else {

							res.status(HttpStatus.OK).send(recursoStored);
						}
					}
				});

			} else {
				res.status(HttpStatus.OK).send({message : 'Rellena todos los campos'});
			}
		} 
	});
}

// Funcion para obtener las recursos y representarlas
function getRecursos(req, res){
	var recurso = new Recurso();
	var parametros = req.headers;
	var idEntidad = parametros.idEntidad;
	
	console.log("Carga datos: " + idEntidad);

	// Recurso.find({idEntidad: idEntidad}, (err, recursos) =>{
	Recurso.find().paginate(1, 20, function(err, recursos, total){
		if(err){
			console.log("Error");
			res.status(HttpStatus.INTERNAL_SERVER_ERROR).send("Se produjo un error al consultar los recursos de la entidad");			
		} else {
			console.log("Correcto");

			if(!recursos){
				res.status(HttpStatus.NOT_FOUND).send("No encontrado");			
			} else {
				return res.status(HttpStatus.OK).send({recursos : recursos});
			}
		}

	});

}


// Exportamos
module.exports = {
	pruebas,
	guardarRecurso,
	getRecursos
};