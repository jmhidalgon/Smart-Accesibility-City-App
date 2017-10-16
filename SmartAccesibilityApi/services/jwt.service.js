// Fichero de servicio de JWT
'use strict'

// Módulo para generar el token
var jwt = require('jwt-simple');
// Módulo para gestión del token
var moment = require('moment');
// Clave para la codificacion de nuestro token
var secret = 'clave_secreta_crime';

// Creacion del token
exports.createToken = function(usuario){
	var payload = {
		sub: usuario._id,
		nombre: usuario.nombre,
		apellidos: usuario.apellidos,
		email: usuario.email,
		rol: usuario.rol,
		imagen: usuario.imagen,
		iat: moment().unix(),
		exp: moment().add(30, 'days').unix
	};

	return jwt.encode(payload, secret);
};
