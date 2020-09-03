package ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots

import android.util.Log
import ru.art241111.gt_kawasaki.repository.robotAPI.RobotEntity
import java.lang.Exception

/**
 * Class that tracks changes in position
 */
class PositionHandler(): Handler {
    val pointCommandCame:MutableList<MethodWorkWhenCommandReceived> = mutableListOf()

    override fun listener(command: String, robotEntity: RobotEntity){
        if (command.substringBefore(";").trim() == "POINT"){
            val positions = command.substringAfter(";").substringBeforeLast(";")

            robotEntity.position = getFlatArrayFromString(positions)

            pointCommandCame.forEach {
                it.runMethodWhenHandlerWork()
            }
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