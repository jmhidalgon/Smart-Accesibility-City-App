// Intermediario entre el entidad y la base de datos (MVC)
'use strict'

// Cargamos nuestro modelo de entidad
var Entidad = require('../models/entidad.model');
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
		message: 'Probando una acción del controlador de entidad del API REST con node y mongo'
	});
}

// Metodo encargado del almacenamiento de un nuevo entidad
function guardarEntidad(req, res){
	var entidad = new Entidad();

	var parametros = req.body;

	entidad.email = parametros.email;
	entidad.nombreEntidad = parametros.nombreEntidad;
	entidad.direccion = parametros.direccion;
	entidad.longitud = parametros.longitud;
	entidad.latitud = parametros.latitud;
	entidad.website = parametros.website;
	entidad.rol = 'ROLE_ENTITY';
	entidad.imagen = 'null';
	entidad.pass = parametros.pass;
	var passNoEncryp = parametros.pass; 

	console.log("Registrar entidad");
	Entidad.findOne({email: entidad.email.toLowerCase()}, (err, u) =>{
		if(u != null){ 
			res.status(HttpStatus.METHOD_NOT_ALLOWED).send({message : 'El entidad ya esta registado con ese email'});
		} else {
			bcrypt.hash(parametros.pass, null, null, function(err, hash){
				entidad.pass = hash;
				
				console.log("Vamos a guardarlo si tiene todos los datos");
				console.log(entidad);


				if(entidad.nombreEntidad != null && entidad.email != null){ // comprobamos que tenga datos
					// guardamos entidad
					entidad.save((err, entidadStored) => {
						if(err){ // Error al guardar
							console.log("0");

							res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error al guardar el entidad'});
						} else { // Guardado correctamente
							if(!entidadStored) { // Comprobamos que se ha guardado
								res.status(HttpStatus.BAD_REQUEST).send({message : 'No se ha resgistrado el entidad'});
							} else {
								entidadStored.pass = passNoEncryp;
								res.status(HttpStatus.OK).send(entidadStored);
							}
						}
					});

				} else {
					res.status(HttpStatus.OK).send({message : 'Rellena todos los campos'});
				}
			});
		} 

		if(entidad == null) {
			res.status(HttpStatus.OK).send({message : 'Introduce la contraseña'});
		}
	});
}

// Metodo para el login del entidad
function loginEntidad(req, res){
	// Gracias a body parser convertimos la request en un json
	var parametros = req.headers;

	// Recogemos los parametros
	var email = parametros.email;
	var pass = parametros.pass;

	console.log("User: " +email);
	console.log("pass: " +pass);


	Entidad.findOne({email: email.toLowerCase()}, (err, entidad) =>{
		if(err){ // Error en la peticion
			res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error en la peticion'});
		} else {
			if(!entidad){ // No existe el entidad
				res.status(HttpStatus.NOT_FOUND).send({message : 'El entidad no existe'});
			} else { // Existe el entidad
				// Comprobar la contraseña
				bcrypt.compare(pass, entidad.pass, function(err, check){
					if(check){ // Contraseña correcta
						// devolvemos datos del entidad logeado
						if(parametros.gethash){
							console.log("TOKEN");
							// devolvemos token de JWT para poder tener los datos de entidad para cada una de las peticiones de la API
							res.status(HttpStatus.OK).send( {stringToken: jwt.createToken(entidad)});
						} else {
							entidad.pass = pass;
							res.status(HttpStatus.OK).send(entidad);
						}
					} else { // Contraseña incorrecta
						res.status(HttpStatus.NOT_FOUND).send({message : 'El entidad no ha podido autenticarse'});
					}
				});
			}
		}
	});
}

// Método para actualizart el entidad
function actualizarEntidad(req, res){
	var entidadId = req.params.id;
	var update = req.body;
	console.log(entidadId);

	if(req.body.pass){
		res.status(HttpStatus.METHOD_NOT_ALLOWED).send({message : 'No esta permitido cambiar la contraseña'});
	}

	Entidad.findByIdAndUpdate(entidadId, update, (err, entidadActualizado) => {
		if(err){ // No existe el entidad
			res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error al actualizar el entidad'});
		} else {
			if(!entidadActualizado){ // No se ha actualizado
				res.status(HttpStatus.NOT_FOUND).send({message : 'No se ha podido actualizar el entidad'});
			} else {
				res.status(HttpStatus.OK).send(entidadActualizado);
			}
		}

	});
}


// Funcion para subir avatar
function uploadImagen(req, res){
	var idEntidad = req.params.id;
	var nombreFichero = 'No subido...';

	console.log(req.files);

	if(req.files){
		var pathFichero = req.files.imagen.path;
		var splitFichero = pathFichero.split('/');
		var nombreFichero = splitFichero[2];
		var splitExtension = nombreFichero.split('.');
		var extension = splitExtension[1]; 

		if(extension == 'png' || extension == 'jpg' || extension == 'gif'){

			Entidad.findByIdAndUpdate(idEntidad, {imagen: nombreFichero}, (err, entidadActualizado) => {
				if(!entidadActualizado){ // No se ha actualizado
					res.status(HttpStatus.NOT_FOUND).send({message : 'No se ha podido actualizar el Entidad'});
				} else {
					res.status(HttpStatus.OK).send({ imagen: nombreFichero, entidad: entidadActualizado});
				}
			});

		} else {
			res.status(HttpStatus.OK).send({message : 'Extension del archivo no valida'});
		}
	} else {
		res.status(HttpStatus.OK).send({message : 'No has subido ninguna imagen...'});
	}
}

// Funcion para obtener las entidades y representarlas
function getEntidades(req, res){
	var pag = 1000;
	console.log("Consulta de entidades");
	Entidad.find().paginate(1, pag, function(err, entidades, total){
		console.log("Consulta Entidades");
		if(err){
			res.status(HttpStatus.INTERNAL_SERVER_ERROR).send("Se produjo un error al consultar las entidades");			
		} else {
			console.log("Ningun error. Token:");
			if(!entidades){
				res.status(HttpStatus.NOT_FOUND).send("No encontrado");			
			} else {				
				/*var response;
				for(var i=1; i<total; i++){
					response = {entidad:entidades[i]};
					
				}
				return res.status(HttpStatus.OK).send({entidades : response});*/
				
				return res.status(HttpStatus.OK).send({total : total, entidads : entidades});
			}
		}
	});

}

function getEntidadPorId(req, res){
	var idEntidad = req.params.idEntidad;
	console.log(idEntidad);
	if(!idEntidad){
		console.log("Id entidad nulo");
	} else {
		Entidad.findById(idEntidad, (err, entidad) =>{
		if(err){ // Error en la peticion
			res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error en la peticion'});
		} else {
			if(!entidad){ // No existe el entidad
				res.status(HttpStatus.NOT_FOUND).send({message : 'La entidad no existe'});
			} else { // Existe el entidad
				console.log("Ningun error. Entidad: "+ entidad);
				res.status(HttpStatus.OK).send({entidads : [entidad]});
			}
		}
	});
	}

}

// Metodo encargado del almacenamiento de un nuevo entidad
function guardarEntidadPublica(req, res){
	var entidad = new Entidad();

	var parametros = req.body;

	entidad.nombreEntidad = parametros.nombreEntidad;
	entidad.direccion = parametros.direccion;
	entidad.longitud = parametros.longitud;
	entidad.latitud = parametros.latitud;
	entidad.website = 'null';
	entidad.rol = 'ROLE_PUBLIC';
	entidad.imagen = 'null';
	entidad.pass = 'null';

	console.log("Registrar entidad publica");
	Entidad.findOne({nombreEntidad: entidad.nombreEntidad.toLowerCase()}, (err, u) =>{
		if(u != null){ 
			res.status(HttpStatus.METHOD_NOT_ALLOWED).send({message : 'La entidad publica ya esta registado con ese nombre'});
		} else {
			if(entidad.nombreEntidad != null){ // comprobamos que tenga datos
				// guardamos entidad
				entidad.save((err, entidadStored) => {
					if(err){ // Error al guardar
						console.log("0");

						res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error al guardar el entidad'});
					} else { // Guardado correctamente
						if(!entidadStored) { // Comprobamos que se ha guardado
							res.status(HttpStatus.BAD_REQUEST).send({message : 'No se ha resgistrado el entidad'});
						} else {
							res.status(HttpStatus.OK).send(entidadStored);
						}
					}
				});

			} else {
				res.status(HttpStatus.OK).send({message : 'Rellena todos los campos'});
			}
		} 
	});
}


// Exportamos
module.exports = {
	pruebas,
	guardarEntidad,
	guardarEntidadPublica,
	loginEntidad,
	actualizarEntidad,
	uploadImagen,
	getEntidades,
	getEntidadPorId
};