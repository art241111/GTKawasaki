package ru.art241111.gt_kawasaki.view.util

import android.view.MotionEvent
import android.view.View
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.utils.Delay
import kotlin.concurrent.thread

/**
 * Tracking class button hold.
 *
 * @author Artem Gerasimov
 */
class WhenButtonHold(private val robot: RepositoryForRobotApi) {
    private var isPress = false

    fun onTouchListener(view: View , button: Buttons, coefficient: AmountOfMovement){
        view.setOnTouchListener { v, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.performClick()
                    isPress = true
                    arrowPressed(button, determiningTheCoefficient(coefficient))
                }
                MotionEvent.ACTION_UP -> {
                    robot.cleanQueue()
                    isPress = false
                }
            }
            false
        }
    }

    private fun determiningTheCoefficient(coefficient: AmountOfMovement):Int =
        when(coefficient){
            AmountOfMovement.FAST -> 20
            AmountOfMovement.SLOW -> 1
        }

    private fun arrowPressed(button:Buttons, coefficient: Int){
        thread {
            robot.cleanQueue()
            while (isPress){
                when(button){
                    Buttons.UpZ -> robot.moveByZ(coefficient)
                    Buttons.DownZ -> robot.moveByZ(-coefficient)
                    Buttons.UpX -> robot.moveByX(coefficient)
                    Buttons.DownX -> robot.moveByX(-coefficient)
                    Buttons.UpY -> robot.moveByY(coefficient)
                    Buttons.DownY -> robot.moveByY(-coefficient)

                    Buttons.UpDZ -> robot.moveByDZ(coefficient)
                    Buttons.DownDZ -> robot.moveByDZ(-coefficient)
                    Buttons.UpDX -> robot.moveByDX(coefficient)
                    Buttons.DownDX -> robot.moveByDX(-coefficient)
                    Buttons.UpDY -> robot.moveByDY(coefficient)
                    Buttons.DownDY -> robot.moveByDY(-coefficient)
                }

                Delay.customDelay(500L)
            }
        }
    }
}