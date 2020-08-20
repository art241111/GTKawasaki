package kawasakiRobots.handlersFromKawasakiRobots

import android.util.Log
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.RobotEntity
import java.lang.Exception


class PositionHandler(private val robotEntity: RobotEntity) {
    private var position: MutableList<Float> = mutableListOf()

    fun listener(command: String){
        if (command.substringBefore(";").trim() == "POINT"){
            position.clear()
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