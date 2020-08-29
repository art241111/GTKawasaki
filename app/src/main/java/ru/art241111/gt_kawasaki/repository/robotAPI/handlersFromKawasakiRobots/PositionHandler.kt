package ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots

import ru.art241111.gt_kawasaki.repository.robotAPI.RobotEntity
import java.lang.Exception

/**
 * Class that tracks changes in position
 */
class PositionHandler(private val robotEntity: RobotEntity) {
    fun listener(command: String){
        if (command.substringBefore(";").trim() == "POINT"){
            val position: MutableList<Float> = mutableListOf()
            val positions = command.substringAfter(";")

            positions.split(";").map {
                try {
                    position.add(it.trim().toFloat())
                } catch (e: Exception){
                }
            }
            robotEntity.position = position
        }
    }
}