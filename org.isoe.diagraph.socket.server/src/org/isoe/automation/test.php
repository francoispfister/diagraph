<?php

//TODO loop until the server response to keep the connection

if (!extension_loaded('sockets')) {
    die('The sockets extension is not loaded.');
}
error_reporting(E_ALL);
print("TCP/IP Connection with Diagraph\n");
$socket = socket_create(AF_INET, SOCK_STREAM, 0);
if (!socket_connect($socket, 'localhost', 8261)) {
   die('no connect');
}
if ($socket === false) {
    $errorcode = socket_last_error();
    $errormsg = socket_strerror($errorcode);
    die("Could not connect: [$errorcode] $errormsg \n");
} 
$req = "$argv[1] $argv[2];\r\n";
print ("req=$req");
if( ! socket_write ( $socket , $req , strlen($req))){
    $errorcode = socket_last_error();
    $errormsg = socket_strerror($errorcode);
    die("Could not send data: [$errorcode] $errormsg \n");
}
print ("\r\nwait response\r\n");
$buf = '';
if(socket_recv ( $socket , $buf , 2045 , MSG_WAITALL ) === FALSE){
    $errorcode = socket_last_error();
    $errormsg = socket_strerror($errorcode);
    die("Could not receive data: [$errorcode] $errormsg \n");
}
print($buf);
socket_close($socket);
?>