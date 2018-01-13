// Fichero de carga central
'use strict'

// Cargamos módulo de express
var express = require('express');
// Cargamos módulo de body parser
var bodyParser = require('body-parser');

// Creamos el objeto de express
var app = express();

// Cargamos rutas
var usuarioRouter = require('./routes/usuario.route');
var entidadRouter = require('./routes/entidad.route');
var recursoRouter = require('./routes/recurso.route');
var comentarioRouter = require('./routes/comentario.route');

// Configuramos el bodyparser
app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json());

// Configurar cabeceras http
app.use((req, res, next) =>{
	// Cabeceras necesarias para que la API funcione a nivel de AJAX
	res.header('Access-Control-Allow-Origin', '*');
	res.header('Access-Control-Allow-Headers', 'Autorithation, X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Allow-Request-Method');
	res.header('Access-Control-Allow-Method', 'GET, POST, OPTIONS, PUT, DELETE');
	res.header('Allow', 'GET, POST, OPTIONS, PUT, DELETE');
	
	next();
});

// Cargas de rutas base
app.use('/api', usuarioRouter); 
app.use('/api', entidadRouter); 
app.use('/api', recursoRouter); 
app.use('/api', comentarioRouter); 

// Exportamos el modulo
module.exports = app;