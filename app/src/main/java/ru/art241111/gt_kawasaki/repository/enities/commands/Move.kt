package ru.art241111.gt_kawasaki.repository.enities.commands

import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.enities.enums.Coordinate

// TODO: Check Float!!!
data class Move(val coordinate: Coordinate, val sizeOfPlant: Float): RobotCommands() {
    override fun getCommandText(): String =
        "${GTKawasakiApp.instance.resources.getText(R.string.command_move) as String} $coordinate " +
                "${GTKawasakiApp.instance.resources.getText(R.string.command_move_value) as String} $sizeOfPlant"
}