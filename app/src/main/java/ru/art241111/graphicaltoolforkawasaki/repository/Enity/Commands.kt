package ru.art241111.graphicaltoolforkawasaki.repository.Enity

import ru.art241111.graphicaltoolforkawasaki.repository.Enity.enums.Coordinate
import ru.art241111.graphicaltoolforkawasaki.repository.Enity.enums.TypesOfMovementToThePoint

data class Move(val coordinate: Coordinate, val sizeOfPlant: Int):RobotCommands
data class MoveToPoint(val type: TypesOfMovementToThePoint, val coordinate: List<Int>):RobotCommands
class OpenGripper :RobotCommands
class CloseGripper:RobotCommands