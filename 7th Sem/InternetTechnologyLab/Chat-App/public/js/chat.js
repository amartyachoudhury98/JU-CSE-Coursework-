const socket = io()

//elements
const $messageform = document.querySelector('#message-form')
const $messageformInput = $messageform.querySelector('input')
const $messageformButton = $messageform.querySelector('button')
const $sendLocationButton =document.querySelector('#send-location')
const $messages = document.querySelector('#past-messages')

//template
const messageTemplate = document.querySelector('#message-template').innerHTML
const locationTemplate = document.querySelector('#location-template').innerHTML
const sidebarTemplate = document.querySelector('#sidebar-template').innerHTML
const imageTemplate = document.querySelector('#image-template').innerHTML
const { username , room } = Qs.parse(location.search,{ignoreQueryPrefix:true})

const autoscroll = () =>{
    const $newMessage = $messages.lastElementChild
    const newMessageStyles = getComputedStyle($newMessage)
    const newMessageMargin = parseInt(newMessageStyles.marginBottom)
    const newMessageHeight = $newMessage.offsetHeight + newMessageMargin
    const visibleHeight = $messages.offsetHeight
    const containerHeight = $messages.scrollHeight
    const scrollOffset = $messages.scrollTop + visibleHeight
    if(containerHeight - newMessageHeight <= scrollOffset){
        $messages.scrollTop = $messages.scrollHeight
    }
}
socket.emit('join',{username,room},(error)=>{
    if(error){
        alert(error)
        location.href = '/'
    }
})
socket.on('message',(message)=>{
    console.log(message)
    const html =Mustache.render(messageTemplate,{
       username : message.username,
        message: message.text,
       createdAt: moment(message.createdAt).format('h:mm a') 
    })
    console.log(html)
    $messages.insertAdjacentHTML('beforeend',html)
    autoscroll()
})

socket.on('locationMessage',(message)=>{
    console.log(message)
    const html =Mustache.render(locationTemplate,{
       username:message.username,
        url: message.url,
       createdAt: moment(message.createdAt).format('h:mm a') 
    })
    console.log(html)
    $messages.insertAdjacentHTML('beforeend',html)
    autoscroll()
})

socket.on('imageMessage',(message) =>{
    console.log(message)
    const html = Mustache.render(imageTemplate,{
        username:message.username,
        url:message.dataUrl,
        createdAt : moment(message.createdAt).format('h:mm a')
    })
    console.log(html)
    $messages.insertAdjacentHTML('beforeend',html)
    autoscroll()
})
if($messageform){
    $messageform.addEventListener('submit',(e)=>{
        e.preventDefault()
        $messageformInput.setAttribute('disabled','disabled')
        message = e.target.elements.message.value
        socket.emit('sendMessage',message,(error) =>{
            $messageformInput.removeAttribute('disabled')
            $messageformInput.value = ''
            $messageformInput.focus()
            if(error){
                console.log(error)
                return
            }
            console.log('Message Delivered')
        })
    })
}

if($sendLocationButton){
    $sendLocationButton.addEventListener('click',()=>{
    if(!navigator.geolocation){
        console.log("geolocation is not supported by the browser")
    }
    
    else{
        $sendLocationButton.setAttribute('disabled','disabled')
        navigator.geolocation.getCurrentPosition((position)=>{
            console.log(position)
            socket.emit('sendLocation',{
                latitude:  position.coords.latitude,
                longitude: position.coords.longitude
            },()=>{
                $sendLocationButton.removeAttribute('disabled')
                console.log('Location shared')
            })
        })
    }
})

socket.on('roomData',({room,users}) =>{
    const html =Mustache.render(sidebarTemplate,{
        room,
        users
    })
    document.querySelector('#sidebar').innerHTML = html
})
}