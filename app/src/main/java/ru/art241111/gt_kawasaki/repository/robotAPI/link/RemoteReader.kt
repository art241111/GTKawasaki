package ru.art241111.gt_kawasaki.repository.robotAPI.link

import link.protocols.Analyzer
import ru.art241111.gt_kawasaki.repository.robotAPI.RobotEntity
import ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots.PositionHandler
import java.util.*
import kotlin.concurrent.thread

class RemoteReader(private val robotEntity: RobotEntity,
                   val positionHandler: PositionHandler = PositionHandler()) {
    private var connection = false

    /**
     * Start reading from InputStream
     */
    fun startReading(analyzer: Analyzer) {
        val reader = Scanner(robotEntity.client.socket.getInputStream())
        connection = true

        thread {
            startTrackingInputString(analyzer, reader)
        }
    }

    /**
     * Stop reading track
     */
    fun stopReading(){
        connection = false
    }


    private fun startTrackingInputString(analyzer: Analyzer, reader: Scanner){
        while (connection){
            try {
                analyzer.commandAnalysis(command = reader.nextLine().trim(),
                                         handlers = listOf(positionHandler))
            }catch (e: NoSuchElementException) {
                // TODO: Migrate to log
                println("Problem with reading")
            }
        }
    }
}