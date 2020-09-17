package ru.art241111.gt_kawasaki.repository

import androidx.databinding.ObservableInt
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands.*
import ru.art241111.gt_kawasaki.utils.Delay

class SendCommandsUtils(private val robotApi: RepositoryForRobotApi) {
    val isProgramRun: ObservableInt = ObservableInt(0)
    private val defaultCommands: MutableList<RobotCommands> = mutableListOf()

    fun sendCommands(commands: List<RobotCommands>){
        when(isProgramRun.get()){
            2 -> {
                val defaultCommandsCopy = mutableListOf<RobotCommands>()
                defaultCommandsCopy.addAll(defaultCommands)

                sendCommandsFromList(defaultCommandsCopy)}

            0 ->{
                setDefaultStatus(commands)
                sendCommandsFromList(commands)}
        }
    }

    fun stopProgram(){
        isProgramRun.set(0)
        defaultCommands.clear()
    }

    fun pauseProgram(){
        isProgramRun.set(2)
    }

    private fun sendCommandsFromList(commands: List<RobotCommands>){
        isProgramRun.set(1)
        defaultCommands.clear()

        commands.forEach{
            sendCommand(it)
        }

        if (isProgramRun.get() != 2) isProgramRun.set(0)
    }

    private fun setDefaultStatus(commands: List<RobotCommands>){
        commands.forEach {
            it.status.set(0)
        }
    }

    private fun sendCommand(command: RobotCommands){
        if(isProgramRun.get() == 1){
            command.status.set(1)
            command.runCommand(robotApi)

            Delay.customDelay(1000L)
        } else if(isProgramRun.get() == 2){
            defaultCommands.add(command)
        }
    }
}