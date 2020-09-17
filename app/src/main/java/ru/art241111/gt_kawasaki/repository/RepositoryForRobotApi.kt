package ru.art241111.gt_kawasaki.repository

import ru.art241111.gt_kawasaki.utils.enitiesCommandsAndPosition.commands.RobotCommands
import ru.art241111.gt_kawasaki.utils.enitiesCommandsAndPosition.enums.TypesOfMovementToThePoint
import ru.art241111.gt_kawasaki.repository.robotAPI.KawasakiRobot
import ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots.MethodWorkWhenCommandReceived
import kotlin.concurrent.thread

class RepositoryForRobotApi {
    private val sendCommandsUtils = SendCommandsUtils(this)
    var robot = KawasakiRobot()

    /**
     * Connect to the robot via tcp
     *
     * @param address - robot IP
     * @param port - robot port (default = 49152)
     */
    fun connectToRobotTCP(address: String = "192.168.31.52",
                       port: Int = 49152){
        robot.connectTCP(address, port)
    }

    /**
     * Disconnecting connection with the robot
     */
    fun disconnect(){
        robot.disconnect()
    }

    /**
     * Cleaning the command queue
     */
    fun cleanQueue(){
        robot.service.clearQueue()
    }

    /**
     * Check the connection of the robot
     */
    fun isConnect() =
        robot.specifications.client.socket.isConnected

    /**
     * Moving the robot along the x coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByX(position: Double) =
        robot.moving.moveByX(position)

    /**
     * Moving the robot along the y coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByY(position: Double) =
        robot.moving.moveByY(position)

    /**
     * Moving the robot along the z coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByZ(position: Double) =
        robot.moving.moveByZ(position)

    /**
     * Moving the robot along the dx coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByDX(position: Double) =
        robot.moving.moveByDX(position)

    /**
     * Moving the robot along the dy coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByDY(position: Double) =
        robot.moving.moveByDY(position)

    /**
     * Moving the robot along the dz coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByDZ(position: Double) =
        robot.moving.moveByDZ(position)


    /**
     * Move to the desired point
     * @param position - the distance to be moved.
     */
    fun moveToPoint(typeOfMovement: TypesOfMovementToThePoint, position: MutableList<Double>) =
        robot.moving.moveToPoint(typeOfMovement = typeOfMovement.toString(),
                                 position = position.joinToString(";"))

    /**
     * Open the robot's grip
     */
    fun openGripper() = robot.moving.openGripper()

    /**
     * Closing the robot's grip
     */
    fun closeGripper() = robot.moving.closeGripper()

    /**
     * Sending a command array
     */
    fun sendCommands(commands: List<RobotCommands>){
        thread {
           sendCommandsUtils.sendCommands(commands)
        }
    }

    /**
     * Stop sending a command array
     */
    fun stopSendCommands(){
        thread {
           sendCommandsUtils.stopProgram()
        }
    }

    /**
     * Pause sending a command array
     */
    fun pauseSendCommands(){
        thread {
            sendCommandsUtils.pauseProgram()
        }
    }

    fun getProgramStatusValue() = sendCommandsUtils.isProgramRun

    fun addMethodAtPointHandler(method: MethodWorkWhenCommandReceived){
        robot.service.addMethodToPositionHandler(method)
    }

    fun removeMethodAtPointHandler(method: MethodWorkWhenCommandReceived){
        robot.service.removeMethodToPositionHandler(method)
    }
}