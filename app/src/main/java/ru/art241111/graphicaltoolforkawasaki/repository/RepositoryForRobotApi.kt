package ru.art241111.graphicaltoolforkawasaki.repository

import ru.art241111.graphicaltoolforkawasaki.repository.enities.*
import ru.art241111.graphicaltoolforkawasaki.repository.enities.expansion.toStringForRobot
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.KawasakiRobot
import kotlin.concurrent.thread

class RepositoryForRobotApi {
    private val sendCommandsUtils = SendCommandsUtils(this)
    var robot = KawasakiRobot()

    fun cleanQueue(){
        robot.service.clearQueue()
    }

    fun connectToRobotTCP(address: String = "192.168.31.52",
                       port: Int = 49152){
        robot.connectTCP(address, port)
    }

    fun isConnect() =
        robot.specifications.client.socket.isConnected

    fun updateInfoAboutPosition() =
        robot.service.updateInfoAboutPosition()

    fun turnOnTheMotors() =
        robot.service.turnOnTheMotors()

    fun resetErrors() =
        robot.service.resetErrors()

    fun moveByX(position: Int) =
        robot.moving.moveByX(position)

    fun moveByY(position: Int) =
        robot.moving.moveByY(position)

    fun moveByZ(position: Int) =
        robot.moving.moveByZ(position)

    fun moveByDX(position: Int) =
        robot.moving.moveByDX(position)

    fun moveByDY(position: Int) =
        robot.moving.moveByDY(position)

    fun moveByDZ(position: Int) =
        robot.moving.moveByDZ(position)

    fun disconnect(){
        robot.disconnect()
    }

    fun moveToPoint(typeOfMovement: String, position: MutableList<Float>) =
        robot.moving.moveToPoint(typeOfMovement = typeOfMovement,
                                 position = position.toStringForRobot())

    fun openGripper() = robot.moving.openGripper()
    fun closeGripper() = robot.moving.closeGripper()

    fun sendCommand(commands: List<RobotCommands>){
        thread {
           sendCommandsUtils.sendCommands(commands)
        }
    }
}