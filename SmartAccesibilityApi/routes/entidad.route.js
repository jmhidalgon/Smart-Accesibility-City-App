'use strict'

// Cargamos el módulo de express
var express = require('express');
// Cargamos el modulo de multiparty
var multipart = require('connect-multiparty');
// Cargamos el controlador de entidad
var EntidadControlador = require('../controllers/entidad.controller');
// Cargamos nuestro middleware de autenticación
var middlewareAuten = require('../middlewares/autenticado');
// Cargamos nuestro middleware de avatar
var middlewareUpload = multipart({uploadDir: './uploads/entidad'});

// Cargamos las rutas de nuestro módulo express
var api = express.Router();

// Creamos la ruta en el router de express

// GET
//api.get('/probando-controlador', middlewareAuten.ensureAut, EntidadControlador.pruebas);
api.get('/probando-entidad', middlewareAuten.ensureAut, EntidadControlador.pruebas);
api.get('/loginEntidad', EntidadControlador.loginEntidad);
api.get('/get-entidades', EntidadControlador.getEntidades);
// POST
api.post('/registro-entidad', EntidadControlador.guardarEntidad);
api.post('/upload-imagen-entidad/:id', [middlewareAuten.ensureAut, middlewareUpload], EntidadControlador.uploadImagen);
// PUT
api.put('/actualizar-entidad/:id', middlewareAuten.ensureAut, EntidadControlador.actualizarEntidad);

// Exportamos api para poder usarlo fuera de este fichero
module.exports = api;