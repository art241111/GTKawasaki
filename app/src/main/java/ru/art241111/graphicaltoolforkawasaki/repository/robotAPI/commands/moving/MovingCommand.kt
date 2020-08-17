package kawasakiRobots.commands.moving


enum class MovingCommand(val command: String) {
    MOVE_BY_X( "SHIFT;X;"),
    MOVE_BY_Y( "SHIFT;Y;"),
    MOVE_BY_Z( "SHIFT;Z;");
}