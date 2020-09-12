package ru.art241111.gt_kawasaki.repository.enities

import androidx.databinding.ObservableField
import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.enities.enums.Coordinate
import ru.art241111.gt_kawasaki.repository.enities.enums.TypesOfMovementToThePoint

open class Status{
    var status = ObservableField(0)
}

interface GetCommandText{
    fun getCommandText(): String
}
abstract class RobotCommands: Status(), GetCommandText

// TODO: Check Float!!!
data class Move(val coordinate: Coordinate, val sizeOfPlant: Float):RobotCommands() {
    override fun getCommandText(): String =
        "${GTKawasakiApp.instance.resources.getText(R.string.command_move) as String} $coordinate " +
                "${GTKawasakiApp.instance.resources.getText(R.string.command_move_value) as String} $sizeOfPlant"
}

data class MoveToPoint(val type: TypesOfMovementToThePoint,val coordinate: Position):RobotCommands() {
    override fun getCommandText(): String =
        "${GTKawasakiApp.instance.resources.getText(R.string.command_move_to_point) as String}: ${coordinate.name} \n"+
                "${GTKawasakiApp.instance.resources.getText(R.string.command_move_to_point_type) as String} $type"
}

class OpenGripper:RobotCommands(){
    override fun toString(): String {
        return "OpenGripper"
    }

    override fun getCommandText(): String =
        GTKawasakiApp.instance.resources.getText(R.string.command_gripper_open) as String
}

class CloseGripper:RobotCommands(){
    override fun toString(): String {
        return "CloseGripper"
    }

    override fun getCommandText(): String =
        GTKawasakiApp.instance.resources.getText(R.string.command_gripper_close) as String
}

data class For(val variable: String, val from:Int, val before: Int): RobotCommands(){
    override fun getCommandText(): String = "$variable FOR from $from to $before"
}

data class ForEnd(val variable: String): RobotCommands(){
    override fun getCommandText(): String = "$variable FOR"
}