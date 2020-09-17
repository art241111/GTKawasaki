package ru.art241111.gt_kawasaki.utils.enitiesCommandsAndPosition.commands

import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.utils.enitiesCommandsAndPosition.enums.Coordinate

data class Move(val coordinate: Coordinate, val sizeOfPlant: Double): RobotCommands() {
    override fun getCommandText(): String =
        "${GTKawasakiApp.instance.resources.getText(R.string.command_move) as String} $coordinate " +
                "${GTKawasakiApp.instance.resources.getText(R.string.command_move_value) as String} $sizeOfPlant"

    override fun runCommand(robotApi: RepositoryForRobotApi) {
        when(coordinate){
            Coordinate.X -> robotApi.moveByX(sizeOfPlant)
            Coordinate.Y -> robotApi.moveByY(sizeOfPlant)
            Coordinate.Z -> robotApi.moveByZ(sizeOfPlant)
            Coordinate.DX -> robotApi.moveByDX(sizeOfPlant)
            Coordinate.DY -> robotApi.moveByDY(sizeOfPlant)
            Coordinate.DZ -> robotApi.moveByDZ(sizeOfPlant)
        }
    }

    override fun parse(stringParse: String): Move = this
}