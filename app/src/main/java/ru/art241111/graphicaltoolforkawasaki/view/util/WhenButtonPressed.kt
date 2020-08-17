package ru.art241111.graphicaltoolforkawasaki.view.util

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import kotlin.concurrent.thread

class WhenButtonPressed(private val robot: RepositoryForRobotApi) {
    private var press = false

    @SuppressLint("ClickableViewAccessibility")
    fun onTouchListener(view: View, button: Buttons, coefficient: AmountOfMovement){
        view.setOnTouchListener(View.OnTouchListener { _, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    press = true
                    arrowPressed(button,determiningTheCoefficient(coefficient))
                }
                MotionEvent.ACTION_UP ->{
                    press = false
                }
            }
            return@OnTouchListener false
        })
    }

    private fun determiningTheCoefficient(coefficient: AmountOfMovement):Int =
        when(coefficient){
            AmountOfMovement.FAST -> 10
            AmountOfMovement.SLOW -> 1
        }

    private fun arrowPressed(button:Buttons, coefficient: Int){
        thread {
            while (press){
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

                try {
                    Thread.sleep(50L)
                } catch (e: java.lang.Exception) {
                }
            }
        }
    }
}