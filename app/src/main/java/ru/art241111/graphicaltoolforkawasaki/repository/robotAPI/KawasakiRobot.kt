package ru.art241111.graphicaltoolforkawasaki.repository.robotAPI

import kawasakiRobots.handlersFromKawasakiRobots.CommandAnalyzerForKawasakiRobots
import kawasakiRobots.utils.Service
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.commands.moving.Moving
import kotlin.concurrent.thread
private const val defaultAddress = "127.0.0.1"
private const val defaultPort = 9105

class KawasakiRobot{
    val specifications = RobotEntity()
    val moving = Moving(specifications)
    val service = Service(specifications)

    fun connectTCP(address: String = defaultAddress,
                port: Int = defaultPort){
        thread {
            specifications.client.createTCPLink(address,port)

            if(specifications.client.socket.isConnected){
                specifications.writer.startSendCommands()
                specifications.reader.startReading(CommandAnalyzerForKawasakiRobots(specifications))
            }
        }
    }

    fun connectTelnet(address: String = defaultAddress,
                      port: Int = defaultPort,
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