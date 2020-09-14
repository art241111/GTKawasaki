package ru.art241111.gt_kawasaki.repository

import android.util.Log
import androidx.databinding.ObservableField
import ru.art241111.gt_kawasaki.repository.enities.*
import ru.art241111.gt_kawasaki.repository.enities.enums.Coordinate
import ru.art241111.gt_kawasaki.repository.enities.enums.TypesOfMovementToThePoint
import ru.art241111.gt_kawasaki.utils.Delay

class SendCommandsUtils(private val robotApi: RepositoryForRobotApi) {
    val isProgramRun: ObservableField<Int> = ObservableField(0)

    private val defaultCommands: MutableList<RobotCommands> = mutableListOf()

    fun sendCommands(commands: List<RobotCommands>){
        if(defaultCommands.isNotEmpty()){
            val defaultCommandsCopy = mutableListOf<RobotCommands>()
            defaultCommandsCopy.addAll(defaultCommands)

            sendCommandsFromList(defaultCommandsCopy)
        } else if(isProgramRun.get() == 0){
            setDefaultStatus(commands)
            sendCommandsFromList(commands)
        }
    }

    private fun sendCommandsFromList(commands: List<RobotCommands>){
        isProgramRun.set(1)
        defaultCommands.clear()

        commands.forEach{
            if(isProgramRun.get() == 1){
                sendCommand(it)
                Delay.customDelay(1000L)
            } else if(isProgramRun.get() == 2){
                defaultCommands.add(it)
                Log.d("debug_default_list", defaultCommands.toString())
            }
        }

        isProgramRun.set(0)
    }

    fun stopProgram(){
        isProgramRun.set(0)
        defaultCommands.clear()
    }

    fun pauseProgram(){
        isProgramRun.set(2)
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