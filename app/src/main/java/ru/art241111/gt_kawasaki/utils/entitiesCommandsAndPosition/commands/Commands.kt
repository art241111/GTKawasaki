package ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands

import androidx.databinding.ObservableField
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi

abstract class RobotCommands: Status(), GetCommandText, RunCommand, ParseCommand

    open class Status{
        var status = ObservableField(0)
    }

    interface GetCommandText{
        fun getCommandText(): String
    }

    interface ParseCommand{
        fun parse(stringParse: String = ""): RobotCommands
    }

    interface RunCommand{
        fun runCommand(robotApi: RepositoryForRobotApi)
    }







