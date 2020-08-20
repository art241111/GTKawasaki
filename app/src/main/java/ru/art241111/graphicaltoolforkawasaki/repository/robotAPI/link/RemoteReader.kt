package ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.link

import link.protocols.Analyzer
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.RobotEntity
import ru.art241111.graphicaltoolforkawasaki.utils.Delay
import java.util.*
import kotlin.concurrent.thread

class RemoteReader(private val robotEntity: RobotEntity) {
    private var connection = false

    fun startReading(analyzer: Analyzer) {
        val reader = Scanner(robotEntity.client.socket.getInputStream())
        connection = true

        thread {
            while (connection){
                try {
                    analyzer.commandAnalysis(reader.nextLine().trim())
                }catch (e: NoSuchElementException) {
                    // TODO: Migrate to log
                    println("Problem with reading")
                }
            }
        }
    }

    fun stopReading(){
        connection = false
    }
}