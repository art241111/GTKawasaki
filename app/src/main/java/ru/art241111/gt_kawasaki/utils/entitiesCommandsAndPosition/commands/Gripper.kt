package ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands

import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.Position
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.enums.getTypeOfMovementToThePoint

class OpenGripper: RobotCommands(){
    override fun toString(): String = "OpenGripper"


    override fun getCommandText(): String =
        GTKawasakiApp.instance.resources.getText(R.string.command_gripper_open) as String

    override fun runCommand(robotApi: RepositoryForRobotApi) {
        robotApi.openGripper()
    }

    companion object{
        fun parse(commands: String): OpenGripper = OpenGripper()
    }
}

class CloseGripper: RobotCommands(){
    override fun toString(): String {
        return "CloseGripper"
    }

    override fun getCommandText(): String =
        GTKawasakiApp.instance.resources.getText(R.string.command_gripper_close) as String

    override fun runCommand(robotApi: RepositoryForRobotApi) {
        robotApi.closeGripper()
    }

    companion object{
        fun parse(commands: String): CloseGripper = CloseGripper()
    }
}