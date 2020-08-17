package ru.art241111.graphicaltoolforkawasaki.repository

import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.KawasakiRobot


class RepositoryForRobotApi {
    var robot = KawasakiRobot()

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
        robot.moving.moveByX(position)

    fun moveByDY(position: Int) =
        robot.moving.moveByY(position)

    fun moveByDZ(position: Int) =
        robot.moving.moveByZ(position)

    fun disconnect(){
        robot.disconnect()
    }
}