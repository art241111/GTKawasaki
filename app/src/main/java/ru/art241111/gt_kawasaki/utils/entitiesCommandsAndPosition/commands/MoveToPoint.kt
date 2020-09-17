package ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands

import android.util.Log
import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.Position
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.enums.TypesOfMovementToThePoint
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.enums.getTypeOfMovementToThePoint
import ru.art241111.gt_kawasaki.view.addElements.AddForCommandFragment


data class MoveToPoint(val type: TypesOfMovementToThePoint, val coordinate: Position): RobotCommands() {
    override fun getCommandText(): String =
        "${GTKawasakiApp.instance.resources.getText(R.string.command_move_to_point) as String}: ${coordinate.name} \n"+
                "${GTKawasakiApp.instance.resources.getText(R.string.command_move_to_point_type) as String} $type"

    override fun runCommand(robotApi: RepositoryForRobotApi) {
        val positions = coordinate.position
        if(positions.isNotEmpty()){
            robotApi.moveToPoint(type, positions)
        } else{
            Log.e("MoveToPoint", "Не введены координаты")
        }
    }

    companion object{
        fun parse(commands: String): MoveToPoint {
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
    }
}