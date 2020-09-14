package ru.art241111.gt_kawasaki.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.art241111.gt_kawasaki.repository.enities.*
import ru.art241111.gt_kawasaki.repository.enities.commands.CloseGripper
import ru.art241111.gt_kawasaki.repository.enities.commands.Move
import ru.art241111.gt_kawasaki.repository.enities.commands.MoveToPoint
import ru.art241111.gt_kawasaki.repository.enities.commands.RobotCommands
import ru.art241111.gt_kawasaki.repository.enities.enums.getCoordinate
import ru.art241111.gt_kawasaki.repository.enities.enums.getTypeOfMovementToThePoint

class JsonHelper {
    fun robotCommandsArrayToJsonString(commands: List<RobotCommands>):  String = commands.joinToString(separator = ";")

    // TODO: Implementation json library and do this class
    fun jsonArrayToRobotCommands(commands: String): MutableList<RobotCommands>{

        val command = commands.split(";")
        val returnCommands = mutableListOf<RobotCommands>()

        command.forEach {
            when{
                it == "CloseGripper" -> returnCommands.add(CloseGripper())
                it == "OpenGripper" -> returnCommands.add(CloseGripper())
                it.contains("MoveToPoint", ignoreCase = true)-> returnCommands.add(parseMoveToPointCommand(it))
                it.contains("MOVE", ignoreCase = true)-> returnCommands.add(parseMoveCommand(it))
            }

        }

        return returnCommands
    }

    private fun parseMoveCommand(commands: String): Move {
        val coordinate = commands.substringBefore(",").substringAfter("coordinate=")
        val sizeOfPlant = commands.substringBefore(")").substringAfter("sizeOfPlant=")
        return Move(coordinate.getCoordinate(), sizeOfPlant.toDouble())
    }

    private fun parseMoveToPointCommand(commands: String): MoveToPoint {
        val typesOfMovement = commands.substringBefore(",").substringAfter("type=")
        val coordinate = commands.substringBefore(")").substringAfter("coordinate=Position(")
        return MoveToPoint(typesOfMovement.getTypeOfMovementToThePoint(), parsePosition(coordinate))
    }

    private fun parsePosition(commands: String): Position{
        val name = commands.substringBefore(",").substringAfter("name=")
        val position = commands.substringBefore(")").substringAfter("position=")
        return Position(name, parsePositionForMutable(position))
    }

    private fun parsePositionForMutable(position: String): MutableList<Double>{
        val pos = position.substringAfter("[").substringBefore("]")
        return if(pos.isEmpty()){
            mutableListOf()
        } else{
            pos.split(",").map { it.toDouble()} as MutableList<Double>
        }
    }

    fun jsonArrayToPosition(position: String): List<Position>{
// Converting json to array.
        var point: MutableList<Position>? = Gson().fromJson(position,
            object : TypeToken<MutableList<Position>>() {}.type)

        // Processing a null array.
        if (point == null){
            point = mutableListOf()
        }

        return point
    }

    fun positionArrayToJsonString(positions: List<Position>):  String =
        Gson().toJson(positions)
}