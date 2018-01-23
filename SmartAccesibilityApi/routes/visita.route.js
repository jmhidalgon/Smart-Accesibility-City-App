'use strict'

// Cargamos el módulo de express
var express = require('express');
// Cargamos el modulo de multiparty
var multipart = require('connect-multiparty');
// Cargamos el controlador de Visita
var VisitaControlador = require('../controllers/visita.controller');
// Cargamos nuestro middleware de autenticación
var middlewareAuten = require('../middlewares/autenticado');
// Cargamos nuestro middleware de avatar
var middlewareUpload = multipart({uploadDir: './uploads/visita'});

// Cargamos las rutas de nuestro módulo express
var api = express.Router();

// GET
api.get('/probando-visita', VisitaControlador.pruebas);
api.get('/get-visita-usuario/:idUsuario', VisitaControlador.getVisitasPorUsuario);
api.get('/get-visita-entidad/:idEntidad', VisitaControlador.getVisitasPorEntidad);
// POST
api.post('/registro-visita', VisitaControlador.guardarVisita);
api.post('/actualizar-token-visita/:idVisita', VisitaControlador.actualizarToken);

module.exports = api;