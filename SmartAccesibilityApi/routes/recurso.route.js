'use strict'

// Cargamos el módulo de express
var express = require('express');
// Cargamos el modulo de multiparty
var multipart = require('connect-multiparty');
// Cargamos el controlador de recurso
var RecursoControlador = require('../controllers/recurso.controller');
// Cargamos nuestro middleware de autenticación
var middlewareAuten = require('../middlewares/autenticado');
// Cargamos nuestro middleware de avatar
var middlewareUpload = multipart({uploadDir: './uploads/recurso'});

// Cargamos las rutas de nuestro módulo express
var api = express.Router();

// Creamos la ruta en el router de express

// GET
api.get('/probando-recurso', RecursoControlador.pruebas);
api.get('/get-recursos', RecursoControlador.getRecursos);
// POST
api.post('/registro-recurso', RecursoControlador.guardarRecurso);

// Exportamos api para poder usarlo fuera de este fichero
module.exports = api;