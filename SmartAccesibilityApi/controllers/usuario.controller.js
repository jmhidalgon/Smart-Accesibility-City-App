// Intermediario entre el usuario y la base de datos (MVC)
'use strict'

// Cargamos nuestro modelo de usuario
var Usuario = require('../models/usuario.model');
// Cargamos el modulo de encriptacion para la contraseña
var bcrypt = require('bcrypt-nodejs');
// Cargamos el módulo de servicio JWT
var jwt = require('../services/jwt.service');
// Modulos para trabajar con el sistema de ficheros del servidor
var fs = require('fs');
var path = require('path');
var HttpStatus = require('http-status-codes');


function pruebas(req, res){
	res.status(HttpStatus.OK).send({
		message: 'Probando una acción del controlador de usuarios del API REST con node y mongo'
	});
}

// Metodo encargado del almacenamiento de un nuevo usuario
/*function guardarUsuario(req, res){
	var usuario = new Usuario();

	var parametros = req.body;

	usuario.nombre = parametros.nombre;
	usuario.apellidos = parametros.nombre;
	usuario.email = parametros.email;
	usuario.nombreUsuariotipoMovilidadReducida = parametros.nombreUsuario;
	usuario.rol = 'ROLE_USER';
	usuario.imagen = 'null';
	usuario.pass = parametros.pass;

	console.log("Registrar usuario");
	Usuario.findOne({email: usuario.email.toLowerCase()}, (err, u) =>{
		
		if(u != null){ 
			res.status(HttpStatus.METHOD_NOT_ALLOWED).send({message : 'El usuario ya esta registado con ese email'});
		} else if(parametros.pass != "" && usuario == null){
			// Encriptamos contraseña y guardamos
			console.log("sadfsdfa");
			bcrypt.hash(parametros.pass, null, null, function(err, hash){
				usuario.pass = hash;
				
				console.log("Vamos a guardarlo si tiene todos los datos");


				if(usuario.nombre != null && usuario.apellidos != null && usuario.nombreUsuario != null && usuario.email != null){ // comprobamos que tenga datos
					// guardamos usuario
					usuario.save((err, usuarioStored) => {
						if(err){ // Error al guardar
							console.log("0");

							res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error al guardar el usuario'});
						} else { // Guardado correctamente
							if(!usuarioStored) { // Comprobamos que se ha guardado
								console.log("1");
								res.status(HttpStatus.BAD_REQUEST).send({message : 'No se ha resgistrado el usuario'});
							} else {
								console.log("2");

								res.status(HttpStatus.OK).send(usuarioStored);
							}
						}
					});

				} else {
					res.status(HttpStatus.OK).send({message : 'Rellena todos los campos'});
				}
			});
		} else if(usuario == null) {
			res.status(HttpStatus.OK).send({message : 'Introduce la contraseña'});
		}
	}
	});

}*/

// Metodo encargado del almacenamiento de un nuevo usuario
function guardarUsuario(req, res){
	var usuario = new Usuario();

	var parametros = req.body;

	usuario.nombre = parametros.nombre;
	usuario.apellidos = parametros.nombre;
	usuario.email = parametros.email;
	usuario.tipoMovilidadReducida = parametros.tipoMovilidadReducida;
	usuario.rol = 'ROLE_USER';
	usuario.imagen = 'null';
	usuario.pass = parametros.pass;

	console.log("Registrar usuario");
	Usuario.findOne({email: usuario.email.toLowerCase()}, (err, u) =>{
		if(u != null){ 
			res.status(HttpStatus.METHOD_NOT_ALLOWED).send({message : 'El usuario ya esta registado con ese email'});
		} else {
			console.log("sadfsdfa");
			bcrypt.hash(parametros.pass, null, null, function(err, hash){
				usuario.pass = hash;
				
				console.log("Vamos a guardarlo si tiene todos los datos");


				if(usuario.nombre != null && usuario.apellidos != null && usuario.tipoMovilidadReducida != null && usuario.email != null){ // comprobamos que tenga datos
					// guardamos usuario
					usuario.save((err, usuarioStored) => {
						if(err){ // Error al guardar
							console.log("0");

							res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error al guardar el usuario'});
						} else { // Guardado correctamente
							if(!usuarioStored) { // Comprobamos que se ha guardado
								console.log("1");
								res.status(HttpStatus.BAD_REQUEST).send({message : 'No se ha resgistrado el usuario'});
							} else {
								console.log("2");

								res.status(HttpStatus.OK).send(usuarioStored);
							}
						}
					});

				} else {
					res.status(HttpStatus.OK).send({message : 'Rellena todos los campos'});
				}
			});
		} 

		if(usuario == null) {
			res.status(HttpStatus.OK).send({message : 'Introduce la contraseña'});
		}
	}
	);
}

// Metodo para el login del usuario
function loginUsuario(req, res){
	// Gracias a body parser convertimos la request en un json
	var parametros = req.headers;

	// Recogemos los parametros
	var email = parametros.email;
	var pass = parametros.pass;

	console.log("User: " +email);
	console.log("pass: " +pass);


	Usuario.findOne({email: email.toLowerCase()}, (err, usuario) =>{
		if(err){ // Error en la peticion
			res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error en la peticion'});
		} else {
			if(!usuario){ // No existe el usuario
				res.status(HttpStatus.NOT_FOUND).send({message : 'El usuario no existe'});
			} else { // Existe el usuario
				// Comprobar la contraseña
				bcrypt.compare(pass, usuario.pass, function(err, check){
					if(check){ // Contraseña correcta
						// devolvemos datos del usuario logeado
						if(parametros.gethash){
							console.log("TOKEN");
							// devolvemos token de JWT para poder tener los datos de usuario para cada una de las peticiones de la API
							res.status(HttpStatus.OK).send( {stringToken: jwt.createToken(usuario)});
						} else {
							usuario.pass = pass;
							res.status(HttpStatus.OK).send(usuario);
						}
					} else { // Contraseña incorrecta
						res.status(HttpStatus.NOT_FOUND).send({message : 'El usuario no ha podido autenticarse'});
					}
				});
			}
		}
	});
}


// Metodo para el login del usuario
function loginGetUsuario(req, res){
	// Gracias a body parser convertimos la request en un json
	var parametros = req.params;

	// Recogemos los parametros
	var email = parametros.email;
	var pass = parametros.pass;

	console.log("User: " +email);
	console.log("pass: " +pass);


	Usuario.findOne({email: email.toLowerCase()}, (err, usuario) =>{
		if(err){ // Error en la peticion
			res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error en la peticion'});
		} else {
			if(!usuario){ // No existe el usuario
				res.status(HttpStatus.NOT_FOUND).send({message : 'El usuario no existe'});
			} else { // Existe el usuario
				// Comprobar la contraseña
				bcrypt.compare(pass, usuario.pass, function(err, check){
					if(check){ // Contraseña correcta
						// devolvemos datos del usuario logeado
						if(parametros.gethash){
							// devolvemos token de JWT para poder tener los datos de usuario para cada una de las peticiones de la API
							res.status(HttpStatus.OK).send({
								token: jwt.createToken(usuario)
							});
						} else {
							console.log("Usuario: " +usuario);
							res.status(HttpStatus.OK).send(usuario);
						}
					} else { // Contraseña incorrecta
						res.status(HttpStatus.NOT_FOUND).send({message : 'El usuario no ha podido autenticarse'});
					}
				});
			}
		}
	});
}

// Método para actualizart el usuario
function actualizarUsuario(req, res){
	var usuarioId = req.params.id;
	var update = req.body;
	console.log('quepasaaqui');
	Usuario.findByIdAndUpdate(usuarioId, update, (err, usuarioActualizado) => {
		if(err){ // No existe el usuario
			res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error al actualizar el usuario'});
		} else {
			if(!usuarioActualizado){ // No se ha actualizado
				res.status(HttpStatus.NOT_FOUND).send({message : 'No se ha podido actualizar el usuario'});
			} else {
				res.status(HttpStatus.OK).send(usuarioActualizado);
			}
		}

	});
}

// Funcion para subir avatar
function uploadImagen(req, res){
	var idUsuario = req.params.id;
	var nombreFichero = 'No subido...';

	console.log(req.files);

	if(req.files){
		var pathFichero = req.files.imagen.path;
		var splitFichero = pathFichero.split('/');
		var nombreFichero = splitFichero[2];
		var splitExtension = nombreFichero.split('.');
		var extension = splitExtension[1]; 

		if(extension == 'png' || extension == 'jpg' || extension == 'gif'){

			Usuario.findByIdAndUpdate(idUsuario, {imagen: nombreFichero}, (err, usuarioActualizado) => {
				if(!usuarioActualizado){ // No se ha actualizado
					res.status(HttpStatus.NOT_FOUND).send({message : 'No se ha podido actualizar el usuario'});
				} else {
					res.status(HttpStatus.OK).send({ imagen: nombreFichero, usuario: usuarioActualizado});
				}
			});

		} else {
			res.status(HttpStatus.OK).send({message : 'Extension del archivo no valida'});
		}
	} else {
		res.status(HttpStatus.OK).send({message : 'No has subido ninguna imagen...'});
	}
}

function getArchivoImagen(req, res){
	var archivoImagen = req.params.archivoImagen;
	var rutaArchivo = './uploads/usuarios/' + archivoImagen;

	fs.exists(rutaArchivo, function(exists){
		if(exists){
			res.sendFile(path.resolve(rutaArchivo));
		} else {
			res.status(HttpStatus.OK).send({message : 'No existe la imagen'});	
		}
	});
}

// Exportamos
module.exports = {
	pruebas,
	guardarUsuario,
	loginUsuario,
	actualizarUsuario,
	uploadImagen,
	getArchivoImagen
};
