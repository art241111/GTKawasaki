package ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands

import android.util.Log
import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.Position
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.enums.TypesOfMovementToThePoint


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

    override fun parse(stringParse: String): MoveToPoint = this
}