const path = require('path')
const http = require('http')
const express = require('express')
const socketio = require('socket.io')
const { generateMessage , generateLocationMessage,generateImageMessage} = require('./utils/messages.js')
const {addUser,removeUser,getUser,getUsersInRoom} = require('./utils/users.js')
const app = express()
const server = http.createServer(app)
const io = socketio(server)

const port = process.env.PORT || 3000
const publicDirectoryPath = path.join(__dirname,'../public')

app.use(express.static(publicDirectoryPath))

io.on('connection',connect)
function connect(socket){
    console.log('New websocket connection')
    socket.on('join',(options,callback) =>{
        const {error,user} = addUser( {id:socket.id,...options})
        if(error){
            return callback(error)
        }
        socket.join(user.room)
        socket.emit('message',generateMessage('Admin','Welcome!'))
        socket.broadcast.to(user.room).emit('message',generateMessage('Admin',user.username + 'has joined'))    // send joining notifcation to all other users
        io.to(user.room).emit('roomData',{
            room :user.room,
            users:getUsersInRoom(user.room)
        })
        callback()
    })
    socket.on('sendMessage',(message,callback)=>{
        const user = getUser(socket.id)
        io.to(user.room).emit('message',generateMessage(user.username,message))
        callback('Delivered')
    })
    
    socket.on('sendLocation',(coords,callback) =>{
        const user = getUser(socket.id)
        io.to(user.room).emit('locationMessage',generateLocationMessage(user.username,'https://www.google.com/maps?q=' + coords.latitude + ',' + coords.longitude))
        callback()
    })

    socket.on('sendImage',(imageUrl,callback) =>{
        const user = getUser(socket.id)
        io.to(user.room).emit('imageMessage',generateImageMessage(user.username,imageUrl))
        callback()
    })
    socket.on('disconnect',() =>{
        const user = removeUser(socket.id)
        if(user){
            io.to(user.room).emit('message',generateMessage(user.username + 'A user has left'))
            io.to(user.room).emit('roomData',{
                room :user.room,
                users:getUsersInRoom(user.room)
            })
        }
    })

}

server.listen(port,() =>{
    console.log('Server is up and running on port '+ port)
})
