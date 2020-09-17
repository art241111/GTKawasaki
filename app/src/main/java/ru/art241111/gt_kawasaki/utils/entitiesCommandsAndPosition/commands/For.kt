package ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.commands

import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi


data class For(val variable: String, val from:Int, val before: Int): RobotCommands(){
    override fun getCommandText(): String = "$variable FOR from $from to $before"
    override fun runCommand(robotApi: RepositoryForRobotApi) {}
    override fun parse(stringParse: String): For = this
}

data class ForEnd(val variable: String): RobotCommands(){
    override fun getCommandText(): String = "$variable FOR"
    override fun runCommand(robotApi: RepositoryForRobotApi) {}
    override fun parse(stringParse: String): ForEnd = this
}