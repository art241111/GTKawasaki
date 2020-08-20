package ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.commands.moving

import kawasakiRobots.commands.moving.MovingCommand
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.RobotEntity

class Moving(private val robotEntity: RobotEntity) {
    fun moveByX(position: Int) =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_X.command + position)

    fun moveByY(position: Int) =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_Y.command + position)

    fun moveByZ(position: Int) =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_Z.command + position)
    fun moveByDX(position: Int) =
        robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_DX.command + position)

    fun moveByDY(position: Int) =
        robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_DY.command + position)

    fun moveByDZ(position: Int) =
        robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_BY_DZ.command + position)

    fun closeGripper() =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.CLOSE_GRIPPER.command)

    fun openGripper() =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.OPEN_GRIPPER.command)

    fun moveToPoint(command: String) =
            robotEntity.writer.sendCommandWithChangeStatus(MovingCommand.MOVE_TO_POINT.command + command)
}