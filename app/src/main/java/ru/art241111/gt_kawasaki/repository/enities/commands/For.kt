package ru.art241111.gt_kawasaki.repository.enities.commands


data class For(val variable: String, val from:Int, val before: Int): RobotCommands(){
    override fun getCommandText(): String = "$variable FOR from $from to $before"
}

data class ForEnd(val variable: String): RobotCommands(){
    override fun getCommandText(): String = "$variable FOR"
}