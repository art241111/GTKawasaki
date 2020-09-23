package ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands

import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.Position
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.enums.Coordinate
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.enums.getCoordinate
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.enums.getTypeOfMovementToThePoint

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

    companion object{
        fun parse(commands: String): Move {
            val coordinate = commands.substringBefore(",").substringAfter("coordinate=")
            val sizeOfPlant = commands.substringBefore(")").substringAfter("sizeOfPlant=")
            return Move(coordinate.getCoordinate(), sizeOfPlant.toDouble())
        }
    }
}