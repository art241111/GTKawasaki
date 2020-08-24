package ru.art241111.graphicaltoolforkawasaki.repository.robotAPI

import link.State
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.link.RemoteReader
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.link.RemoteWriter
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.link.TelnetConnection
import java.util.*

class RobotEntity {
    var client = TelnetConnection()

    val writer = RemoteWriter(this)
    val reader = RemoteReader(this)

    val errors: Queue<String> = LinkedList<String>()
    var state: State = State.WAITING_COMMAND

    var position: MutableList<Float> = mutableListOf()
}