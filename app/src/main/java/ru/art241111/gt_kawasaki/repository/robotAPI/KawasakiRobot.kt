package ru.art241111.gt_kawasaki.repository.robotAPI

import ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots.CommandAnalyzerForKawasakiRobots
import kawasakiRobots.utils.Service
import ru.art241111.gt_kawasaki.repository.robotAPI.commands.moving.Moving
import kotlin.concurrent.thread
private const val DEFAULT_ADDRESS = "127.0.0.1"
private const val DEFAULT_PORT = 9105

class KawasakiRobot{
    val specifications = RobotEntity()
    val moving = Moving(specifications)
    val service = Service(specifications)

    /**
     * Create robot connection by TCP
     */
    fun connectTCP(address: String = DEFAULT_ADDRESS,
                   port: Int = DEFAULT_PORT){
        thread {
            specifications.client.createTCPLink(address,port)

            if(specifications.client.socket.isConnected){
                specifications.writer.startSendCommands()
                specifications.reader.startReading(CommandAnalyzerForKawasakiRobots(specifications))
            }
        }
    }

    /**
     * Create robot connection by Telnet
     */
    fun connectTelnet(address: String = DEFAULT_ADDRESS,
                      port: Int = DEFAULT_PORT,
                      login: String = "as"){
        thread {
            specifications.client.createTelnetLink(address,port, login)
            specifications.writer.startSendCommands()
        }
    }

    fun disconnect(){
        if(specifications.client.socket.isConnected){
            specifications.writer.stopSendCommands()
            specifications.client.disconnect()
            specifications.reader.stopReading()
        }
    }
}