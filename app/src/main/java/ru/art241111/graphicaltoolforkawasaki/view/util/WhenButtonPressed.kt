package ru.art241111.graphicaltoolforkawasaki.view.util

import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import kotlin.concurrent.thread

class WhenButtonPressed(private val robot: RepositoryForRobotApi) {
    private val fastCoefficient = 10
    private val slowCoefficient = 1

    var press = false

    fun arrowPressed(button:Buttons){
        thread {
            while (press){
                when(button){
                    Buttons.UpZ -> robot.moveByZ(fastCoefficient)
                    Buttons.DownZ -> robot.moveByZ(-fastCoefficient)
                    Buttons.UpX -> robot.moveByX(fastCoefficient)
                    Buttons.DownX -> robot.moveByX(-fastCoefficient)
                    Buttons.UpY -> robot.moveByY(fastCoefficient)
                    Buttons.DownY -> robot.moveByY(-fastCoefficient)

                    Buttons.UpDZ -> robot.moveByDZ(fastCoefficient)
                    Buttons.DownDZ -> robot.moveByDZ(-fastCoefficient)
                    Buttons.UpDX -> robot.moveByDX(fastCoefficient)
                    Buttons.DownDX -> robot.moveByDX(-fastCoefficient)
                    Buttons.UpDY -> robot.moveByDY(fastCoefficient)
                    Buttons.DownDY -> robot.moveByDY(-fastCoefficient)
                }

                try {
                    Thread.sleep(50L)
                } catch (e: java.lang.Exception) {
                }
            }
        }
    }
}