// Intermediario entre el entidad y la base de datos (MVC)
'use strict'

var express = require('express');
var gcm = require('node-gcm');
//init express
var app = express();
var HttpStatus = require('http-status-codes');

function notify(req, res) {
    /*var message = new gcm.Message({
        data: { key1: 'hello' },
        notification: {
            title: 'SPECOZ Offers1',
            body: 'body_data'
        }
    });
            console.error(message);

    // Set up the sender with you API key, prepare your recipients' registration tokens.
    var sender = new gcm.Sender('AIzaSyB4HOcCnja7KulxA4AxcjjXIVIULwryUiI');
    sender.send(message, 'eP36mqKAHXc:APA91bE0TrImFcF_vq73hV_bYMwUBwN-8pJlYRgWwYAZfYgafzqf9JcXqeMNiiFnxcO0R2t_tOETiQksMWrKjqSoiBy5zXXrmopDdl79Dac26_mSn9za6PnukbL4Nxfjkn_K5W2hExs2', function (err, response) {
        if (err) {
            console.error("Error:", err);
        }
        else console.log("Response:", response);
        res.send(response);
    });*/
    /*let sender = new gcm.Sender("key=AAAApMJXET0:APA91bEdEvlRf5r4XwrKPUlQSX022kBcSf0mUVxhEzAQF-al9rnp6ByovyNq7yTz3rDGmV7vhKctmNSbn1eigb1HzNjD8t88uocqGRfP10revVsp8Ya44wQongcLil9JmQ_qdnqbXw7C");

    let message = new gcm.Message({
        notification: {
            title: "Hello World! ",
            icon: "your_icon_name",
            body: "Here is a notification's body."
        },
    });
    sender.sendNoRetry(message, ["eP36mqKAHXc:APA91bE0TrImFcF_vq73hV_bYMwUBwN-8pJlYRgWwYAZfYgafzqf9JcXqeMNiiFnxcO0R2t_tOETiQksMWrKjqSoiBy5zXXrmopDdl79Dac26_mSn9za6PnukbL4Nxfjkn_K5W2hExs2"], (err, response) => {
        if (err) console.error(err);
        else console.log(response);
    });*/
    var gcm = require('node-gcm');
    var sender = new gcm.Sender('AAAApMJXET0:APA91bEdEvlRf5r4XwrKPUlQSX022kBcSf0mUVxhEzAQF-al9rnp6ByovyNq7yTz3rDGmV7vhKctmNSbn1eigb1HzNjD8t88uocqGRfP10revVsp8Ya44wQongcLil9JmQ_qdnqbXw7C');

    var notify_message = new gcm.Message({
        collapseKey: 'demo',
        priority: 'high',
        contentAvailable: true,
        delayWhileIdle: true,
        timeToLive: 3,
        dryRun: false,
        data: {
            key1: 'message1',
            key2: 'message2'
        },
        notification: {
            title: "Hello, World",
            icon: "ic_launcher",
            body: "This is a notification that will be displayed ASAP."
        }
    });

    var registrationTokens = ['eP36mqKAHXc:APA91bE0TrImFcF_vq73hV_bYMwUBwN-8pJlYRgWwYAZfYgafzqf9JcXqeMNiiFnxcO0R2t_tOETiQksMWrKjqSoiBy5zXXrmopDdl79Dac26_mSn9za6PnukbL4Nxfjkn_K5W2hExs2'];
    console.log('Sending notification', sender);
    sender.send(notify_message, { registrationTokens: registrationTokens }, 10, function (err, response) {
      if(err) {
            console.error('notification error: ', err);
            res.status(HttpStatus.METHOD_NOT_ALLOWED).send("Error al enviar el mensaje");
        }else {   
            console.log('notification response: ', response);
            res.status(HttpStatus.OK).send("OK");
        }
    });
}

// Exportamos
module.exports = {
	notify
};
