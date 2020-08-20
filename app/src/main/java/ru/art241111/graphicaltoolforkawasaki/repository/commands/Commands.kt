package ru.art241111.graphicaltoolforkawasaki.repository.commands

import ru.art241111.graphicaltoolforkawasaki.repository.commands.enums.Coordinate
import ru.art241111.graphicaltoolforkawasaki.repository.commands.enums.TypesOfMovementToThePoint

data class Move(val coordinate: Coordinate, val sizeOfPlant: Int):RobotCommands
data class MoveToPoint(val type: TypesOfMovementToThePoint, val coordinate: List<Int>):RobotCommands
class OpenGripper :RobotCommands
class CloseGripper:RobotCommands