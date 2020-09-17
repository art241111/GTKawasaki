package ru.art241111.gt_kawasaki.utils.sharedPreferences

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.*
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands.CloseGripper
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands.Move
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands.MoveToPoint
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands.RobotCommands

class JsonHelper {
    fun robotCommandsArrayToJsonString(commands: List<RobotCommands>):  String = commands.joinToString(separator = ";")

    fun jsonArrayToRobotCommands(commands: String): MutableList<RobotCommands>{
        val command = commands.split(";")
        val returnCommands = mutableListOf<RobotCommands>()

        command.forEach {
            when{
                it == "CloseGripper" -> returnCommands.add(CloseGripper.parse(it))
                it == "OpenGripper" -> returnCommands.add(CloseGripper.parse(it))
                it.contains("MoveToPoint", ignoreCase = true)-> returnCommands.add(MoveToPoint.parse(it))
                it.contains("MOVE", ignoreCase = true)-> returnCommands.add(Move.parse(it))
            }
        }
        return returnCommands
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