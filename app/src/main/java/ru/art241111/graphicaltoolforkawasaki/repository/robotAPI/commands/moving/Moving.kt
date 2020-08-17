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
}