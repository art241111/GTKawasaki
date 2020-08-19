package ru.art241111.graphicaltoolforkawasaki.repository

import android.util.Log
import android.widget.Toast
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.KawasakiRobot
import ru.art241111.graphicaltoolforkawasaki.utils.Delay
import kotlin.concurrent.thread


class RepositoryForRobotApi {
    private var robot = KawasakiRobot()

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

    fun sendCommand(commands: List<String>){
        thread {
            commands.map {
                val command = it.split("@")

                when(command[0]){
                    "MOVE" -> {
                        when(command[1]){
                            "X" -> moveByX(command[2].toInt())
                            "Y" -> moveByY(command[2].toInt())
                            "Z" -> moveByZ(command[2].toInt())
                            "DX" -> moveByDX(command[2].toInt())
                            "DY" -> moveByDY(command[2].toInt())
                            "DZ" -> moveByDZ(command[2].toInt())
                            else -> print("error")
                        }
                    }
                    else -> print("error")
                }
                Delay.customDelay(1000L)
            }

        }

    }
}