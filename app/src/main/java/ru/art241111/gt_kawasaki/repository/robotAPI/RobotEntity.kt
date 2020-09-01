package ru.art241111.gt_kawasaki.repository.robotAPI

import link.State
import ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots.PositionHandler
import ru.art241111.gt_kawasaki.repository.robotAPI.link.RemoteReader
import ru.art241111.gt_kawasaki.repository.robotAPI.link.RemoteWriter
import ru.art241111.gt_kawasaki.repository.robotAPI.link.TelnetConnection
import java.util.*

class RobotEntity {
    var client = TelnetConnection()

    val positionHandler = PositionHandler()

    val writer = RemoteWriter(this)
    val reader = RemoteReader(this, positionHandler)

    val errors: Queue<String> = LinkedList<String>()
    var state: State = State.WAITING_COMMAND

    var position: MutableList<Float> = mutableListOf()
}