package ru.art241111.gt_kawasaki.repository.enities.commands

import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.enities.Position
import ru.art241111.gt_kawasaki.repository.enities.enums.TypesOfMovementToThePoint


data class MoveToPoint(val type: TypesOfMovementToThePoint, val coordinate: Position): RobotCommands() {
    override fun getCommandText(): String =
        "${GTKawasakiApp.instance.resources.getText(R.string.command_move_to_point) as String}: ${coordinate.name} \n"+
                "${GTKawasakiApp.instance.resources.getText(R.string.command_move_to_point_type) as String} $type"
}