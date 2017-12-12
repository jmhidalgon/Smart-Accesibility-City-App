// Middleware para controlar la autenticación de usuarios
'use strict'

// Módulo para generar el token
var jwt = require('jwt-simple');
// Módulo para gestión del token
var moment = require('moment');
// Clave para la codificacion de nuestro token
var secret = 'clave_secreta_crime';

exports.ensureAut = function(req, res, next){
	if(!(req.headers.autorizacion)){
		return res.status(403).send({message: 'La petición no tiene la cabecera de autenticación'})
	} 

	// Recogemos el token de usuario
	var token = req.headers.autorizacion.replace(/['"]+/g, '');
	console.log("TOKEN auth: " + token);
	try{ 
		// Decodificamos el token
		var payload = jwt.decode(token, secret);
		if(payload.ex <= moment.unix()){
			return res.status(401).send({message: 'Token ha expirado'});
		}
	
	} catch(ex){
		//console.log(ex);
		return res.status(404).send({message: 'Token no válido'});
	}
	
	// Añadimos a la request los datos del usuario
	req.user = payload;
	// Hacemos la llamada al siguiente método
	next();
}