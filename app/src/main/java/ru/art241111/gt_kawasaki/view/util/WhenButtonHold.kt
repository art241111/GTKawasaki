package ru.art241111.gt_kawasaki.view.util

import android.view.MotionEvent
import android.view.View
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * Tracking class button hold.
 *
 * @author Artem Gerasimov
 */
class WhenButtonHold(private val viewModel: RobotViewModel) {
    fun onTouchListener(view: View , button: Buttons, coefficient: AmountOfMovement){
        view.setOnTouchListener { v, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.performClick()
                    viewModel.controlModel.addMove(button,coefficient)
                }
                MotionEvent.ACTION_UP -> {
                    viewModel.controlModel.deleteMove(button)
                }
            }
            false
        }
    }
}