package ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.link

import android.util.Log
import link.State
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.RobotEntity
import java.io.PrintStream
import java.net.Socket
import java.util.*
import kotlin.concurrent.thread

class RemoteWriter(private val robotEntity: RobotEntity) {
    private val commandsQueue: Queue<String> = LinkedList<String>()
    private var connection = false

    private lateinit var socket: Socket
    private lateinit var out: PrintStream

    fun sendCommandWithChangeStatus(command:String): Boolean{
        Log.d("Send","Command with change status: $command")
        return commandsQueue.add(command)
    }

    fun sendCommand(command:String): Boolean{
        Log.d("Send","Command: $command")
        return commandsQueue.add(command)
    }

   fun startSendCommands(){
         val  client = robotEntity.client
         socket = client.socket
         out = PrintStream(socket.getOutputStream())

         connection = socket.isConnected

         thread {
             while (connection){
                 if(robotEntity.state == State.ERROR){
                     commandsQueue.clear()
                 } else if((robotEntity.state == State.WAITING_COMMAND) and (!commandsQueue.isEmpty())){
                     val comm = commandsQueue.poll()
                     if( comm != null){
                         write(comm.trim())
                     }
                 }
//                 Delay.little()
             }
          }
    }

    fun stopSendCommands(){
        sendCommand("q")
        connection = false
    }

    private fun write(message: String): Boolean {
        if(socket.isConnected){
            try {
                out.println(message.trim())
                out.flush()
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else{
            // TODO: Migrate to log
            println("Writer: no connection to the robot")
        }
        return false
    }
}