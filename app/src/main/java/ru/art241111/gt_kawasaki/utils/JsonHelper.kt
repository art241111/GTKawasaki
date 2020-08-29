package ru.art241111.gt_kawasaki.utils

import org.json.JSONArray
import ru.art241111.gt_kawasaki.repository.enities.Position
import ru.art241111.gt_kawasaki.repository.enities.RobotCommands

class JsonHelper {
    fun robotCommandsArrayToJsonArray(commandsArray: List<RobotCommands>):  JSONArray =
        JSONArray(commandsArray)

    fun positionArrayToJsonArray(positionsArray: List<Position>):  JSONArray =
            JSONArray(positionsArray)

    // TODO: Implementation json library and do this class
    fun jsonArrayToRobotCommands(commands: JSONArray): List<RobotCommands>{
//        GsonBuilder() gson. commands.getJSONArray(0)
        return listOf()
    }

    fun jsonArrayToPosition(position: JSONArray): List<Position>{
//        GsonBuilder() gson. commands.getJSONArray(0)
        return listOf()
    }

}