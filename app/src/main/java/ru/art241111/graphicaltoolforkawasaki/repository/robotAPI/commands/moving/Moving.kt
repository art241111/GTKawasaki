package ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.commands.moving

import kawasakiRobots.commands.moving.MovingCommand
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.RobotEntity

class Moving(private val robotEntity: RobotEntity) {
    /**
     * Moving the robot along the x coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByX(position: Int) =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_X.command + position)

    /**
     * Moving the robot along the y coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByY(position: Int) =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_Y.command + position)

    /**
     * Moving the robot along the z coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByZ(position: Int) =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_Z.command + position)

    /**
     * Moving the robot along the dx coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByDX(position: Int) =
        robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_DX.command + position)

    /**
     * Moving the robot along the dy coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByDY(position: Int) =
        robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_DY.command + position)

    /**
     * Moving the robot along the dz coordinate.
     * @param position - the distance to be moved.
     */
    fun moveByDZ(position: Int) =
        robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_DZ.command + position)

    /**
     * Closing the robot's grip
     */
    fun closeGripper() =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.CLOSE_GRIPPER.command)

    /**
     * Open the robot's grip
     */
    fun openGripper() =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.OPEN_GRIPPER.command)

    /**
     * Move to the desired point
     * @param position - the distance to be moved.
     */
    fun moveToPoint(typeOfMovement: String, position: String) =
            robotEntity.writer.sendCommandWithChangeStatus("${MovingCommand.MOVE_TO_POINT.command}$typeOfMovement;$position")
}