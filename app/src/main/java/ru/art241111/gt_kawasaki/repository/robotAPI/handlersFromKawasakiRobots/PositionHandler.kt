package ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots

import ru.art241111.gt_kawasaki.repository.robotAPI.RobotEntity

/**
 * Interface for position handler.
 * If you want to connect class with this handler,
 * you should implements this interface.
 */
interface PositionCommandReceived:MethodWorkWhenCommandReceived

/**
 * Class that tracks changes in position
 */
class PositionHandler: Handler {
    val pointCommandCame:MutableList<PositionCommandReceived> = mutableListOf()

    private var oldPositionValueStr = ""

    override fun listener(command: String, robotEntity: RobotEntity){
        if (command.substringBefore(";").trim() == "POINT"){
            positionListener(command, robotEntity)
        }
    }

    private fun positionListener(position: String, robotEntity: RobotEntity){
        // Если старой позиции еще нет или новая отличается от предыдущей
        if(oldPositionValueStr == "" || oldPositionValueStr != position){
            oldPositionValueStr = position
            upgradePosition(position, robotEntity)
        }
    }

    private fun upgradePosition(position: String, robotEntity: RobotEntity){
        val positions = position.substringAfter(";").substringBeforeLast(";")

        robotEntity.position = getFlatArrayFromString(positions)

        pointCommandCame.forEach {
            it.runMethodWhenHandlerWork()
        }
    }
    private fun getFlatArrayFromString(position: String):MutableList<Double>
            = position.split(";")
                       .map{value ->
                           String.format("%.2f",value.trim().toDouble())
                               .replace(",",".")
                               .toDouble()
                       } as MutableList<Double>
}