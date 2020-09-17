package ru.art241111.gt_kawasaki.utils.enitiesCommandsAndPosition.commands

import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi

class OpenGripper: RobotCommands(){
    override fun toString(): String = "OpenGripper"


    override fun getCommandText(): String =
        GTKawasakiApp.instance.resources.getText(R.string.command_gripper_open) as String

    override fun runCommand(robotApi: RepositoryForRobotApi) {
        robotApi.openGripper()
    }

    override fun parse(stringParse: String): OpenGripper = this
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

    override fun parse(stringParse: String): CloseGripper = this
}