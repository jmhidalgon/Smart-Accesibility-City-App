'use strict'

// Cargamos el componente mongoose para la conexión con la base de datos
var mongoose = require('mongoose');
// Creamos un objeto schema que nos permitirá creanos un esquema para poder guardar los documentos en nuestra colección
var Schema = mongoose.Schema;
var bcrypt = require('bcrypt-nodejs');


// Definimos el esquema o modelo de datos
var UsuarioSchema = Schema({
	nombre: String,
	apellidos: String,
	email: String,
	pass: String,
	nombreUsuario: String,
	rol: String,
	imagen: String
});

// Exportamos el esquema para poder crear objetos que trabajen bajo este modelo de datos
module.exports = mongoose.model('Usuario', UsuarioSchema);