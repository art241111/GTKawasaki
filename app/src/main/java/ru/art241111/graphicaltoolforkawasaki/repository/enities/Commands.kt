package ru.art241111.graphicaltoolforkawasaki.repository.enities

import ru.art241111.graphicaltoolforkawasaki.repository.enities.enums.Coordinate
import ru.art241111.graphicaltoolforkawasaki.repository.enities.enums.TypesOfMovementToThePoint

interface RobotCommands

data class Move(val coordinate: Coordinate, val sizeOfPlant: Int):RobotCommands
data class MoveToPoint(val type: TypesOfMovementToThePoint,val coordinate: Position):RobotCommands
class OpenGripper :RobotCommands
class CloseGripper:RobotCommands