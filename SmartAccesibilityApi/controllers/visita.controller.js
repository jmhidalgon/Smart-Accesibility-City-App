// Intermediario entre el Visita y la base de datos (MVC)
'use strict'

// Cargamos nuestro modelo de Visita
var Visita = require('../models/visita.model');
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
		message: 'Probando una acción del controlador de visita del API REST con node y mongo'
	});
}

function guardarVisita(req, res){
	var visita = new Visita();
	var parametros = req.body;

	visita.idEntidad = parametros.idEntidad;
	visita.fecha = parametros.fecha;
	visita.idUsuario = parametros.idUsuario;

	console.log("Registrar visita");
	Visita.findOne({idEntidad: visita.idEntidad, nombreUsuario: visita.nombreUsuario, fecha: visita.fecha}, (err, u) =>{
		if(u != null){ 
			console.log("Usuario ya ha marcado para visitar esta empresa hoy");
			res.status(HttpStatus.METHOD_NOT_ALLOWED).send({message : 'El usuario ya ha marcado para visitar esta empresa hoy'});
		} else {
			if(visita.idEntidad != null && visita.idUsuario != null && visita.fecha != null){ // comprobamos que tenga datos
				// guardamos visita
				visita.save((err, visitaStored) => {
					if(err){ // Error al guardar
						console.log("0");

						res.status(HttpStatus.INTERNAL_SERVER_ERROR).send({message : 'Error al guardar el visita'});
					} else { // Guardado correctamente
						if(!visitaStored) { // Comprobamos que se ha guardado
							res.status(HttpStatus.BAD_REQUEST).send({message : 'No se ha resgistrado el visita'});
						} else {

							res.status(HttpStatus.OK).send(visitaStored);
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

function getVisitasPorEntidad(req, res){
	var idEntidad = req.params.idEntidad;
	console.log(idEntidad);
	if(!idEntidad){
		console.log("Id entidad nulo");
	} else {
		var find = Visita.find({idEntidad : idEntidad});
	}

	find.populate({path: 'idEntidad'}).exec((err, visitas) =>{
		if(err){
			console.log("Error en la peticion");
			res.status(500).send({message : 'Error en la peticion'});
		} else if(!visitas) {
			console.log("Nu hay");
			res.status(404).send({message : 'No hay visitas'});
		} else {
			console.log(visitas);
			res.status(200).send({visitas : visitas});
		}
	});

}

function getVisitasPorUsuario(req, res){
	var idUsuario = req.params.idUsuario;
	console.log(idUsuario);
	console.log("Visitas de usuario " + idUsuario);
	if(!idUsuario){
		console.log("Id entidad nulo");
	} else {
		var find = Visita.find({idUsuario : idUsuario});
	}

	find.populate({path: 'idUsuario'}).exec((err, visitas) =>{
		if(err){
			console.log("Error en la peticion");
			res.status(500).send({message : 'Error en la peticion'});
		} else if(!visitas) {
			console.log("Nu hay");
			res.status(404).send({message : 'No hay visitas'});
		} else {
			console.log(visitas);
			res.status(200).send({visitas : visitas});
		}
	});

}

// Exportamos
module.exports = {
	pruebas,
	guardarVisita,
	getVisitasPorUsuario,
	getVisitasPorEntidad
};