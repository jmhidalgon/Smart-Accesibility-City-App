'use strict'

// Cargamos el módulo de express
var express = require('express');
// Cargamos el modulo de multiparty
var multipart = require('connect-multiparty');
// Cargamos el controlador de usuario
var UsuarioControlador = require('../controllers/usuario.controller');
// Cargamos nuestro middleware de autenticación
var middlewareAuten = require('../middlewares/autenticado');
// Cargamos nuestro middleware de avatar
var middlewareUpload = multipart({uploadDir: './uploads/usuarios'});

// Cargamos las rutas de nuestro módulo express
var api = express.Router();

// Creamos la ruta en el router de express

// GET
api.get('/probando-usuario', middlewareAuten.ensureAut, UsuarioControlador.pruebas);
api.get('/get-imagen-usuario/:archivoImagen', UsuarioControlador.getArchivoImagen);
api.get('/loginUser', UsuarioControlador.loginUsuario);
// POST
api.post('/registro', UsuarioControlador.guardarUsuario);
api.post('/login', UsuarioControlador.loginUsuario);
api.post('/upload-imagen-usuario/:id', [middlewareAuten.ensureAut, middlewareUpload], UsuarioControlador.uploadImagen);
// PUT
api.put('/actualizar-usuario/:id', middlewareAuten.ensureAut, UsuarioControlador.actualizarUsuario);


// Exportamos api para poder usarlo fuera de este fichero
module.exports = api;