var socketio = require('socket.io')
, express = require('express')
, http= require('http')
, fs = require('fs');

var app = express();
app.set('port', 3000);

var server = http.createServer(app).listen(app.get('port'), function() {
        console.log("express server listening on port " + app.get('port'));
});

app.get('/', function(req, res) {
        fs.readFile('/var/www/html/client.html', function(error, data) {
                res.writeHead(200, {'Content-Type': 'text/html'});
                res.end(data);
        });
});

var io = socketio.listen(server);

io.sockets.on('connection', function(socket) {
        socket.on('join', function(userid, roomname) {
                socket.join(roomname);
                //socket.set('room', data.roomname);
                socket.room = roomname;
                //socket.get('room', function(error, room) {
                //      io.sockets.in(room).emit('join', data.userid);
                //});
                var uroom = socket.room;
                io.sockets.in(uroom).emit('join', userid);
        });

        socket.on('message', function(name, message) {
                //socket.get('room', function(error, room) {
                //      io.sockets.in(room).emit('message', message);
                //});
                var uroom = socket.room;
                io.sockets.in(uroom).emit('join', userid);
        });

        socket.on('message', function(name, message) {
                //socket.get('room', function(error, room) {
                //      io.sockets.in(room).emit('message', message);
                //});
                var uroom = socket.room;
                var text = name + ' : ' + message;
                io.sockets.in(uroom).emit('message',text);
        });
        socket.on('disconnect', function() {});
});
