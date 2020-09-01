package ru.art241111.gt_kawasaki.utils

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import ru.art241111.gt_kawasaki.repository.enities.*
import ru.art241111.gt_kawasaki.repository.enities.enums.getCoordinate
import ru.art241111.gt_kawasaki.repository.enities.enums.getTypeOfMovementToThePoint

class JsonHelper {
    fun robotCommandsArrayToJsonString(commands: List<RobotCommands>):  String = commands.joinToString(separator = ";")

    fun positionArrayToJsonString(positions: List<Position>):  String =
        Gson().toJson(positions)

    // TODO: Implementation json library and do this class
    fun jsonArrayToRobotCommands(commands: String): MutableList<RobotCommands>{

        val command = commands.split(";")
        Log.d("debugCommands", commands)
        Log.d("debugCommands", command.toString())

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

    private fun parseMoveCommand(commands: String): Move{
        val coordinate = commands.substringBefore(",").substringAfter("coordinate=")
        val sizeOfPlant = commands.substringBefore(")").substringAfter("sizeOfPlant=")
        return Move(coordinate.getCoordinate(), sizeOfPlant.toFloat())
    }

    private fun parseMoveToPointCommand(commands: String): MoveToPoint{
        val typesOfMovement = commands.substringBefore(",").substringAfter("type=")
        val coordinate = commands.substringBefore(")").substringAfter("coordinate=Position(")
        return MoveToPoint(typesOfMovement.getTypeOfMovementToThePoint(), parsePosition(coordinate))
    }

    private fun parsePosition(commands: String): Position{
        val name = commands.substringBefore(",").substringAfter("name=")
        val position = commands.substringBefore(")").substringAfter("position=")
        return Position(name, parsePositionForMutable(position))
    }

    private fun parsePositionForMutable(position: String): MutableList<Float>{
        val pos = position.substringAfter("[").substringBefore("]")
        return if(pos.isEmpty()){
            mutableListOf()
        } else{
            pos.split(",").map { it.toFloat()} as MutableList<Float>
        }
    }

    fun jsonArrayToPosition(position: String): List<Position>{
//        GsonBuilder() gson. commands.getJSONArray(0)
        return listOf()
    }
}