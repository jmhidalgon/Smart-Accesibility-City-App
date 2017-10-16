'use strict'

// Datos generales
var puertoMongodb = 27017;
var direccionMongodb = 'mongodb://localhost:';
var nombreBaseDatos = '/sacappdb'


// Cargamos el modulo de conexion con la base de datos
var mongoose = require('mongoose');
// Cargamos el objeto app con la configuración de express, rutas, cabeceras, etc (fichero de carga central)
var app = require('./app');
// Puerto para el api
var puerto = process.env.PORT || 3977; 

// Realizamos la conexión con la base de datos
mongoose.Promise = global.Promise;
mongoose.connect(direccionMongodb + puertoMongodb + nombreBaseDatos, (err, res) => {
	if(err){
		throw err;
	} else {
		console.log("La conexion con la base de datos " + direccionMongodb + puertoMongodb + nombreBaseDatos + " esta funcionando correctamente");
		app.listen(puerto, function(){
			console.log("Servidor de api rest de SACAPP escuchando en http://localhost:"+puerto);
		});
	}
});

