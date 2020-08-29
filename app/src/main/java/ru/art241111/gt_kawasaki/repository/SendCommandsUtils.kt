package ru.art241111.gt_kawasaki.repository

import android.util.Log
import ru.art241111.gt_kawasaki.repository.enities.*
import ru.art241111.gt_kawasaki.repository.enities.enums.Coordinate
import ru.art241111.gt_kawasaki.utils.Delay

class SendCommandsUtils(private val robotApi: RepositoryForRobotApi) {
    private var isProgramRun = false
    fun sendCommands(commands: List<RobotCommands>){
        if(!isProgramRun){
            isProgramRun = true

            commands.forEach{
                sendCommand(it)
                Delay.customDelay(1000L)
            }
            isProgramRun = false
        }
    }

    private fun sendCommand(command: RobotCommands){
        when(command){
            //TODO: TOINT!!!
            is Move -> move(command.coordinate, command.sizeOfPlant.toInt())
            is MoveToPoint -> moveToPoint(command.type.toString(), command.coordinate.position)
            is OpenGripper -> robotApi.openGripper()
            is CloseGripper -> robotApi.closeGripper()
        }
    }

    private fun moveToPoint(type:String, positions: MutableList<Float>){
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