// Intermediario entre el Comentario y la base de datos (MVC)
'use strict'

// Cargamos nuestro modelo de Comentario
var Comentario = require('../models/comentario.model');
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
		message: 'Probando una acción del controlador de comentario del API REST con node y mongo'
	});
}

function guardarComentario(req, res){
	var comentario = new Comentario();
	var parametros = req.body;

	comentario.nombreUsuario = parametros.nombreUsuario;
	comentario.idEntidad = parametros.idEntidad;
	comentario.contenido = parametros.contenido;
	comentario.rating = parametros.rating;

	console.log(comentario.nombreUsuario + " \n" + comentario.idEntidad + " \n" + comentario.contenido + " \n" + comentario.rating + " \n");

	console.log("Registrar comentario");
	Comentario.findOne({idEntidad: comentario.idEntidad, nombreUsuario: comentario.nombreUsuario}, (err, u) =>{
		if(u != null){ 
			res.status(HttpStatus.METHOD_NOT_ALLOWED).send({message : 'El usuario ya ha comentado'});
		} else {
			if(comentario.idEntidad != null && comentario.nombreUsuario != null && comentario.rating != null && comentario.contenido != null){ // comprobamos que tenga datos
				// guardamos comentario
				comentario.save((err, comentarioStored) => {
					if(err){ // Error al guardar
						console.log("0");

						res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error al guardar el comentario'});
					} else { // Guardado correctamente
						if(!comentarioStored) { // Comprobamos que se ha guardado
							res.status(HttpStatus.BAD_REQUEST).send({message : 'No se ha resgistrado el comentario'});
						} else {

							res.status(HttpStatus.OK).send(comentarioStored);
						}
					}
				});

			} else {
				console.log("Aqui");
				res.status(HttpStatus.OK).send({message : 'Rellena todos los campos'});
			}
		} 
	});
}


// Funcion para obtener las comentarios y representarlas
function getComentarios(req, res){
	/*var comentario = new Comentario();
	var parametros = req.headers;
	var idEntidad = parametros.idEntidad;
	
	console.log("Carga datos: " + idEntidad);

	// comentario.find({idEntidad: idEntidad}, (err, comentarios) =>{
	Comentario.find().paginate(1, 20, function(err, comentarios, total){
		if(err){
			console.log("Error");
			res.status(HttpStatus.INTERNAL_SERVER_ERROR).send("Se produjo un error al consultar los comentarios de la entidad");			
		} else {
			console.log("Correcto");

			if(!comentarios){
				res.status(HttpStatus.NOT_FOUND).send("No encontrado");			
			} else {
				return res.status(HttpStatus.OK).send({comentarios : comentarios});
			}
		}

	});*/

	var idEntidad = req.params.idEntidad;
	console.log(idEntidad);
	if(!idEntidad){
		console.log("Id entidad nulo");
	} else {
		var find = Comentario.find({idEntidad : idEntidad});
	}

	find.populate({path: 'idEntidad'}).exec((err, comentarios) =>{
		if(err){
			console.log("Error en la peticion");
			res.status(500).send({message : 'Error en la peticion'});
		} else if(!comentarios) {
			console.log("Nu hay");
			res.status(404).send({message : 'No hay comentarios'});
		} else {
			console.log(comentarios);
			res.status(200).send({comentarios : comentarios});
		}
	});

}

// Exportamos
module.exports = {
	pruebas,
	guardarComentario,
	getComentarios
};