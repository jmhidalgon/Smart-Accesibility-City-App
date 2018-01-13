'use strict'

// Cargamos el módulo de express
var express = require('express');
// Cargamos el modulo de multiparty
var multipart = require('connect-multiparty');
// Cargamos el controlador de comentario
var ComentarioControlador = require('../controllers/comentario.controller');
// Cargamos nuestro middleware de autenticación
var middlewareAuten = require('../middlewares/autenticado');
// Cargamos nuestro middleware de avatar
var middlewareUpload = multipart({uploadDir: './uploads/comentario'});

// Cargamos las rutas de nuestro módulo express
var api = express.Router();

// GET
api.get('/probando-comentario', ComentarioControlador.pruebas);
api.get('/get-comentarios/:idEntidad', ComentarioControlador.getComentarios);
// POST
api.post('/registro-comentario', ComentarioControlador.guardarComentario);

// Exportamos api para poder usarlo fuera de este fichero
module.exports = api;