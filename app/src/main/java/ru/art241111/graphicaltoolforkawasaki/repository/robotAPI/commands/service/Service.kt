package kawasakiRobots.utils

import kawasakiRobots.commands.service.ServiceCommand.*
import link.State
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.RobotEntity

class Service(private var robotEntity: RobotEntity) {
    /**
     * Turn on robot motors
     * TODO: Add
     */
    fun turnOnTheMotors() =
            robotEntity.writer.sendCommand(TURN_ON_THE_MOTORS.command)

    /**
     * Turn off robot motors
     * TODO: Add
     */
    fun turnOffTheMotors() =
            robotEntity.writer.sendCommand(TURN_OFF_THE_MOTORS.command)

    /**
     * Reset errors
     * TODO: Add
     */
    fun resetErrors(): Boolean{
        robotEntity.errors.clear()
        robotEntity.state = State.WAITING_COMMAND
        return robotEntity.writer.sendCommand(DELETE_ERRORS.command)
    }

    /**
     * Clean command queue
     */
    fun clearQueue() = robotEntity.writer.cleanQueue()

    /**
     * Update position
     * TODO: DO it
     */
    fun updateInfoAboutPosition() = robotEntity.writer
            .sendCommand(ROBOT_POSITION.command)
}