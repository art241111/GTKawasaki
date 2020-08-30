package ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots

import ru.art241111.gt_kawasaki.repository.robotAPI.RobotEntity

interface Handler {
    fun listener(command: String, robotEntity: RobotEntity)
}