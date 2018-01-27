// Intermediario entre el entidad y la base de datos (MVC)
'use strict'

var express = require('express');
var gcm = require('node-gcm');
//init express
var app = express();
var HttpStatus = require('http-status-codes');

var AUTHORIZATION = 'AAAApMJXET0:APA91bEdEvlRf5r4XwrKPUlQSX022kBcSf0mUVxhEzAQF-al9rnp6ByovyNq7yTz3rDGmV7vhKctmNSbn1eigb1HzNjD8t88uocqGRfP10revVsp8Ya44wQongcLil9JmQ_qdnqbXw7C';

function notify(req, res) {
    var params = req.body;
    console.log(params.userId);
    console.log(params.entityTokenKey);

    var gcm = require('node-gcm');
    var sender = new gcm.Sender(AUTHORIZATION);

    if(!params.userId){
        res.status(HttpStatus.INTERNAL_SERVER_ERROR).send("Error: usuario no identificado");
    }

    if(!params.entityTokenKey){
        res.status(HttpStatus.INTERNAL_SERVER_ERROR).send("Error: no hay token de entidad");
    }

    var entityTokenKey = params.entityTokenKey;
    var userId = params.idUsuario;

    var notify_message = new gcm.Message({
        collapseKey: 'Usuario cercano',
        priority: 'high',
        contentAvailable: true,
        delayWhileIdle: true,
        timeToLive: 3,
        dryRun: false,
        data: {
            "title": '¡Usuario cercano!',
            "userId": userId,
            "notificationContent": "Un usuario con movilidad reducida esta por su zona y se dirige a visitarlo"
        },
        notification: {
            title: "Hello, World",
            icon: "ic_launcher",
            body: "This is a notification that will be displayed ASAP."
        }
    });

    var registrationTokens = [entityTokenKey];
    //console.log('Sending notification', sender);
    sender.send(notify_message, { registrationTokens: registrationTokens }, 10, function (err, response) {
      if(err) {
            //console.error('notification error: ', err);
            res.status(HttpStatus.METHOD_NOT_ALLOWED).send("Error al enviar el mensaje");
        }else {   
            //console.log('notification response: ', response);
            res.status(HttpStatus.OK).send("OK");
        }
    });
}

// Exportamos
module.exports = {
	notify
};
