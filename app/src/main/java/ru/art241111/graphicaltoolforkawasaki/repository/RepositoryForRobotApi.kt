package ru.art241111.graphicaltoolforkawasaki.repository

import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.KawasakiRobot
import kotlin.concurrent.thread

class RepositoryForRobotApi {
    private val robot = KawasakiRobot(address = "192.168.0.2",
                                      port = 49152)

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

    fun disconnect(){
        robot.disconnect()
    }
    fun moveByZ(position: Int) =
        robot.moving.moveByZ(position)

}