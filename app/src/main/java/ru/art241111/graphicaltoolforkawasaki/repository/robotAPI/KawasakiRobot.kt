package ru.art241111.graphicaltoolforkawasaki.repository.robotAPI

import kawasakiRobots.utils.Service
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.commands.moving.Moving
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.link.TelnetConnection
import kotlin.concurrent.thread

class KawasakiRobot(address: String = "127.0.0.1",
                     port: Int = 9105,
                     login: String = "as"){
    val specifications = RobotEntity()
    val moving = Moving(specifications)
    val service = Service(specifications)

    init {
        thread {
            specifications.client = TelnetConnection(address,port)
            specifications.writer.startSendCommands()
        }
    }

    fun disconnect(){
        specifications.writer.stopSendCommands()
        specifications.client.getSocket().close()
    }
}