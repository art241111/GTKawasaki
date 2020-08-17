package ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.link

import java.io.IOException
import java.io.PrintStream
import java.net.Socket
import java.net.UnknownHostException

class TelnetConnection(){
    private var socket: Socket = Socket()

    constructor(server: String,
                port: Int,
                user: String): this() {
        try {
            socket= Socket(server, port)

            if(socket.isConnected){
                authorization(user)
            }
        } catch (e: UnknownHostException){
            // TODO: Migrate to log
            print("Problem with create socket. \n $e")
        } catch (e: IOException){
            // TODO: Migrate to log
            print("Problem with create socket. \n $e")
        }
    }

    constructor(server: String,
                port: Int): this() {
        try {
            socket= Socket(server, port)
        } catch (e: UnknownHostException){
            // TODO: Migrate to log
            print("Problem with create socket. \n $e")
        } catch (e: IOException){
            // TODO: Migrate to log
            print("Problem with create socket. \n $e")
        }
    }

    fun getSocket(): Socket {
        return socket
    }

    fun disconnect() {
        try {
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun authorization(user: String){
        val writer =  PrintStream(socket.getOutputStream())

        //TODO: Think about how to do it differently
        writer.println(user)
        writer.flush()
    }
}