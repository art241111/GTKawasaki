package ru.art241111.gt_kawasaki.repository.enities

import ru.art241111.gt_kawasaki.repository.enities.enums.Coordinate
import ru.art241111.gt_kawasaki.repository.enities.enums.TypesOfMovementToThePoint

interface RobotCommands


// TODO: Check Float!!!
data class Move(val coordinate: Coordinate, val sizeOfPlant: Float):RobotCommands

data class MoveToPoint(val type: TypesOfMovementToThePoint,val coordinate: Position):RobotCommands

class OpenGripper:RobotCommands{
    override fun toString(): String {
        return "OpenGripper"
    }
}

class CloseGripper:RobotCommands{
    override fun toString(): String {
        return "CloseGripper"
    }
}