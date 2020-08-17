package ru.art241111.graphicaltoolforkawasaki.repository.robotAPI

import kawasakiRobots.utils.Service
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.commands.moving.Moving
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.link.TelnetConnection
import kotlin.concurrent.thread

class KawasakiRobot{
    val specifications = RobotEntity()
    val moving = Moving(specifications)
    val service = Service(specifications)

    fun connectTCP(address: String = "127.0.0.1",
                port: Int = 9105){
        thread {
            specifications.client.createTCPLink(address,port)

            if(specifications.client.socket.isConnected){
                specifications.writer.startSendCommands()
            }
        }
    }

    fun connectTelnet(address: String = "127.0.0.1",
                      port: Int = 9105,
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
        }
    }
}