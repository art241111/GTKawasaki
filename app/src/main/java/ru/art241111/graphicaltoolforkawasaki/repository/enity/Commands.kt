package ru.art241111.graphicaltoolforkawasaki.repository.enity

import ru.art241111.graphicaltoolforkawasaki.repository.enity.enums.Coordinate
import ru.art241111.graphicaltoolforkawasaki.repository.enity.enums.TypesOfMovementToThePoint

data class Move(val coordinate: Coordinate, val sizeOfPlant: Int):RobotCommands
data class MoveToPoint(val type: TypesOfMovementToThePoint,val coordinate: List<Float>):RobotCommands
class OpenGripper :RobotCommands
class CloseGripper:RobotCommands