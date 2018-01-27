'use strict'

// Cargamos el módulo de express
var express = require('express');
var FCMControlador = require('../controllers/fcm.controller');
// Cargamos el modulo de multiparty
var multipart = require('connect-multiparty');

// Cargamos las rutas de nuestro módulo express
var api = express.Router();

// POST
api.post('/notify', FCMControlador.notify);

// Exportamos api para poder usarlo fuera de este fichero
module.exports = api;