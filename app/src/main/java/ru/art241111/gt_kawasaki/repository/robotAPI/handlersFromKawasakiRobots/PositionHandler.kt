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
            val positions = command.substringAfter(";")

            Log.d("position_debug", positions)
            robotEntity.position = getFlatArrayFromString(positions)

            pointCommandCame.forEach {
                it.runMethodWhenHandlerWork()
            }
        }
    }

    private fun getFlatArrayFromString(position: String):MutableList<Float>{
        val returnPosition: MutableList<Float> = mutableListOf()
        position.split(";").forEach {
            try {
                returnPosition.add(String.format("%.2f",it.trim().toFloat()).toFloat())
            } catch (e: Exception){
            }
        }
        return returnPosition
    }
}