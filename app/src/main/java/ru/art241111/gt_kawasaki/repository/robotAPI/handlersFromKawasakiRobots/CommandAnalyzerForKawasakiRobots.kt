package ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots

import link.protocols.Analyzer
import ru.art241111.gt_kawasaki.repository.robotAPI.RobotEntity

class CommandAnalyzerForKawasakiRobots(private var robotEntity: RobotEntity): Analyzer {
    /**
     * Class that tracks incoming commands
     */
    override fun commandAnalysis(command: String, handlers: List<Handler>){
        // Position processing
        handlers.forEach {
            it.listener(command = command,
                        robotEntity = robotEntity)
        }
    }
}