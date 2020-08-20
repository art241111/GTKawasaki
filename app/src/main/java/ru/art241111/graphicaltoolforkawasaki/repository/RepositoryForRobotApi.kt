package ru.art241111.graphicaltoolforkawasaki.repository


import ru.art241111.graphicaltoolforkawasaki.repository.enity.*
import ru.art241111.graphicaltoolforkawasaki.repository.enity.enums.Coordinate
import ru.art241111.graphicaltoolforkawasaki.repository.enity.expansion.toStringForRobot
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.KawasakiRobot
import ru.art241111.graphicaltoolforkawasaki.utils.Delay
import kotlin.concurrent.thread


class RepositoryForRobotApi {
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

    fun sendCommand(commands: List<RobotCommands>){
        thread {
            commands.map {
                when(it){
                    is Move ->{
                        when(it.coordinate){
                            Coordinate.X -> moveByX(it.sizeOfPlant)
                            Coordinate.Y -> moveByY(it.sizeOfPlant)
                            Coordinate.Z -> moveByZ(it.sizeOfPlant)
                            Coordinate.DX -> moveByDX(it.sizeOfPlant)
                            Coordinate.DY -> moveByDY(it.sizeOfPlant)
                            Coordinate.DZ -> moveByDZ(it.sizeOfPlant)
                        }
                    }
                    is MoveToPoint -> {
                        robot.moving.moveToPoint("${it.type};${it.coordinate.toStringForRobot()}")
                    }
                    is OpenGripper ->{
                        robot.moving.openGripper()
                    }
                    is CloseGripper ->{
                        robot.moving.closeGripper()
                    }
                }
                Delay.customDelay(1000L)
            }

        }

    }
}