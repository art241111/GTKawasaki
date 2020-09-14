package ru.art241111.gt_kawasaki.repository.enities.commands

import androidx.databinding.ObservableField
import ru.art241111.gt_kawasaki.GTKawasakiApp
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.repository.enities.Position
import ru.art241111.gt_kawasaki.repository.enities.enums.Coordinate
import ru.art241111.gt_kawasaki.repository.enities.enums.TypesOfMovementToThePoint

abstract class RobotCommands: Status(), GetCommandText, RunCommand

    open class Status{
        var status = ObservableField(0)
    }

    interface GetCommandText{
        fun getCommandText(): String
    }

    interface RunCommand{
        fun runCommand(robotApi: RepositoryForRobotApi)
    }







