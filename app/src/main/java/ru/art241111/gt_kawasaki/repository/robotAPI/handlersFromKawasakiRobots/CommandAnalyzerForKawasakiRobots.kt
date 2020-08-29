package ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots

import link.protocols.Analyzer
import ru.art241111.gt_kawasaki.repository.robotAPI.RobotEntity

class CommandAnalyzerForKawasakiRobots(private var robotEntity: RobotEntity): Analyzer {
    /**
     * Class that tracks incoming commands
     */
    override fun commandAnalysis(command: String){
        // Position processing
        PositionHandler(robotEntity).listener(command)
    }
}