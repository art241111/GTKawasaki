package ru.art241111.gt_kawasaki.repository

import android.util.Log
import androidx.databinding.ObservableField
import ru.art241111.gt_kawasaki.repository.enities.*
import ru.art241111.gt_kawasaki.repository.enities.enums.Coordinate
import ru.art241111.gt_kawasaki.repository.enities.enums.TypesOfMovementToThePoint
import ru.art241111.gt_kawasaki.utils.Delay

class SendCommandsUtils(private val robotApi: RepositoryForRobotApi) {
    var isProgramRun = ObservableField(false)

    fun sendCommands(commands: List<RobotCommands>){
        if(!isProgramRun.get()!!){
            isProgramRun.set(true)

            setDefaultStatus(commands)

            commands.forEach{
                if(isProgramRun.get()!!){
                    sendCommand(it)
                    Delay.customDelay(1000L)
                }
            }
            isProgramRun.set(false)
        }
    }

    fun stopProgram(){
        isProgramRun.set(false)
    }
    private fun setDefaultStatus(commands: List<RobotCommands>){
        commands.forEach {
            it.status.set(0)
        }
    }

    private fun sendCommand(command: RobotCommands){
        command.status.set(1)
        when(command){
            //TODO: TOINT!!!
            is Move -> move(command.coordinate, command.sizeOfPlant.toInt())
            is MoveToPoint -> moveToPoint(command.type, command.coordinate.position)
            is OpenGripper -> robotApi.openGripper()
            is CloseGripper -> robotApi.closeGripper()
        }
    }

    private fun moveToPoint(type:TypesOfMovementToThePoint, positions: MutableList<Double>){
        if(positions.isNotEmpty()){
            robotApi.moveToPoint(type, positions)
        } else{
            Log.e("MoveToPoint", "Не введены координаты")
        }
    }
    private fun move(coordinate: Coordinate, sizeOfPlant:Int){
        when(coordinate){
            Coordinate.X -> robotApi.moveByX(sizeOfPlant)
            Coordinate.Y -> robotApi.moveByY(sizeOfPlant)
            Coordinate.Z -> robotApi.moveByZ(sizeOfPlant)
            Coordinate.DX -> robotApi.moveByDX(sizeOfPlant)
            Coordinate.DY -> robotApi.moveByDY(sizeOfPlant)
            Coordinate.DZ -> robotApi.moveByDZ(sizeOfPlant)
        }
    }
}