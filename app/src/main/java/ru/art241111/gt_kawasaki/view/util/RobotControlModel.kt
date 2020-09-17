package ru.art241111.gt_kawasaki.view.util

import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.enums.TypesOfMovementToThePoint
import ru.art241111.gt_kawasaki.utils.Delay
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel
import kotlin.concurrent.thread

class RobotControlModel(private val viewModel: RobotViewModel) {
    private val typesOfMovement = mutableListOf(0.0, 0.0, 0.0, 0.0, 0.0,0.0)

    var isSending = false

    fun startSending(){
        isSending = true

        viewModel.robot.cleanQueue()
        thread {
            while (isSending){
//                sendCommandMoveByPosition()
                sendCommandMoveByAxis()
            }
        }
    }

    private fun sendCommandMoveByAxis(){
        if(typesOfMovement[0] != 0.0) viewModel.robot.moveByX(typesOfMovement[0])
        if(typesOfMovement[1] != 0.0) viewModel.robot.moveByY(typesOfMovement[1])
        if(typesOfMovement[2] != 0.0) viewModel.robot.moveByZ(typesOfMovement[2])
        if(typesOfMovement[3] != 0.0) viewModel.robot.moveByDX(typesOfMovement[3])
        if(typesOfMovement[4] != 0.0) viewModel.robot.moveByDY(typesOfMovement[4])
        if(typesOfMovement[5] != 0.0) viewModel.robot.moveByDZ(typesOfMovement[5])
        Delay.customDelay(20L)
    }

    private fun sendCommandMoveByPosition(){
        if((typesOfMovement[0] != 0.0)
        || (typesOfMovement[1] != 0.0)
                || (typesOfMovement[2] != 0.0)
                || (typesOfMovement[3] != 0.0)
                || (typesOfMovement[4] != 0.0)
                || (typesOfMovement[5] != 0.0)){
            val newPosition = mutableListOf<Double>()

            for(i in 0 until viewModel.robot.robot.specifications.position.size){
                newPosition.add(viewModel.robot.robot.specifications.position[i]
                        + typesOfMovement[i])
            }

            viewModel.robot.moveToPoint(TypesOfMovementToThePoint.LMOVE, newPosition)
        }
        Delay.customDelay(20L)
    }


    fun addMove(button:Buttons, coefficientAmountOfMovement: AmountOfMovement){
        settingTheValueOfTheDesiredButton(button,
            determiningTheCoefficient(coefficientAmountOfMovement))
    }

    fun deleteMove(button:Buttons){
        settingTheValueOfTheDesiredButton(button,0.0)
    }

    private fun settingTheValueOfTheDesiredButton(button:Buttons, value: Double){
        when(button){
            Buttons.UpX -> typesOfMovement[0] = value
            Buttons.DownX -> typesOfMovement[0] = -value
            Buttons.UpY -> typesOfMovement[1] = value
            Buttons.DownY -> typesOfMovement[1] = -value
            Buttons.UpZ -> typesOfMovement[2] = value
            Buttons.DownZ -> typesOfMovement[2] = -value

            Buttons.UpDX -> typesOfMovement[3] = value
            Buttons.DownDX -> typesOfMovement[3] = -value
            Buttons.UpDY -> typesOfMovement[4] = value
            Buttons.DownDY -> typesOfMovement[4] = -value
            Buttons.UpDZ -> typesOfMovement[5] = value
            Buttons.DownDZ -> typesOfMovement[5] = -value
        }
    }

    private fun determiningTheCoefficient(coefficient: AmountOfMovement):Double =
        when(coefficient){
            AmountOfMovement.FAST -> 10.0
            AmountOfMovement.SLOW -> 1.0
        }
}