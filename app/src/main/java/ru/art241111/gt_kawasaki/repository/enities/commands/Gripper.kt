package ru.art241111.gt_kawasaki.repository.enities.commands

import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R

class OpenGripper: RobotCommands(){
    override fun toString(): String {
        return "OpenGripper"
    }

    override fun getCommandText(): String =
        GTKawasakiApp.instance.resources.getText(R.string.command_gripper_open) as String
}

class CloseGripper: RobotCommands(){
    override fun toString(): String {
        return "CloseGripper"
    }

    override fun getCommandText(): String =
        GTKawasakiApp.instance.resources.getText(R.string.command_gripper_close) as String
}