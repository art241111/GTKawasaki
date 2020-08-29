package ru.art241111.gt_kawasaki.repository.robotAPI.link

import java.io.IOException
import java.io.PrintStream
import java.net.Socket
import java.net.UnknownHostException

class TelnetConnection{
    var socket: Socket = Socket()
    private set

    /**
     * Create TCP link with robot
     */
    fun createTCPLink(server: String, port: Int){
        createSocket(server, port)
    }

    /**
     * Create Telnet link with robot
     */
    fun createTelnetLink(server: String,
                         port: Int,
                         user: String){
        createSocket(server, port)
        authorization(user)
    }

    /**
     * Disconnect from robot
     */
    fun disconnect() {
        try {
            socket.close()
            socket = Socket()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createSocket(server: String,
                             port: Int){
        try {
            socket= Socket(server, port)
        } catch (e: UnknownHostException){
            // TODO: Migrate to log
            print("Problem with create socket. \n $e")
        } catch (e: IOException){
            // TODO: Migrate to log
            print("Problem with create socket. \n $e")
        } catch (e: java.lang.Exception){
            // TODO: Migrate to log
            print("Problem with create socket. \n $e")
        }
    }

    private fun authorization(user: String){
        if(socket.isConnected){
            val writer =  PrintStream(socket.getOutputStream())

            //TODO: Think about how to do it differently
            writer.println(user)
            writer.flush()
        }
    }
}