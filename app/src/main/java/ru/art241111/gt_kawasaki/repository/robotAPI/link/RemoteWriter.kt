package ru.art241111.gt_kawasaki.repository.robotAPI.link

import android.util.Log
import link.State
import ru.art241111.gt_kawasaki.repository.robotAPI.RobotEntity
import ru.art241111.gt_kawasaki.utils.Delay
import java.io.PrintStream
import java.net.Socket
import java.util.*
import kotlin.concurrent.thread

class RemoteWriter(private val robotEntity: RobotEntity) {
    private val commandsQueue: Queue<String> = LinkedList<String>()
    private var connection = false

    private lateinit var socket: Socket
    private lateinit var out: PrintStream

    /**
     * Send commands that are important to expect a response from
     */
    fun sendCommandWithChangeStatus(command:String): Boolean{
        Log.d("send","Command with change status: $command")
        return commandsQueue.add(command)
    }

    /**
     * Send commands that are not important to expect a response from
     */
    fun sendCommand(command:String): Boolean{
        Log.d("send","Command: $command")
        return commandsQueue.add(command)
    }

    /**
     * Clear command queue
     */
    fun cleanQueue(){
        commandsQueue.clear()
    }

    /**
     * Stop sending commands
     */
    fun stopSendCommands(){
        sendCommand("q")
        Delay.middle()
        connection = false
    }

    /**
     * The method tracks the queue and sends command from it
     */
    fun startSendCommands(){
       socket = robotEntity.client.socket
       out = PrintStream(socket.getOutputStream())
       connection = socket.isConnected

       thread {
           startTrackTheQueueAndSendCommands()
       }
    }

    private fun startTrackTheQueueAndSendCommands(){
        while (connection){
            if(robotEntity.state == State.ERROR){
                cleanQueue()
            } else if((robotEntity.state == State.WAITING_COMMAND) and (!commandsQueue.isEmpty())){
                val comm = commandsQueue.poll()
                if( comm != null){
                    write(comm.trim())
                }
            }
        }
    }

    /**
     * Sends commands to the robot
     */
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