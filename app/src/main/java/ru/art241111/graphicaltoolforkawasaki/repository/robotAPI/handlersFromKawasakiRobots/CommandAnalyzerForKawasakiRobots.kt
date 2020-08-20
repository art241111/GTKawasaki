package kawasakiRobots.handlersFromKawasakiRobots

import android.util.Log
import link.protocols.Analyzer
import ru.art241111.graphicaltoolforkawasaki.repository.robotAPI.RobotEntity

class CommandAnalyzerForKawasakiRobots(private var robotEntity: RobotEntity): Analyzer {

    override fun commandAnalysis(command: String){
        // Position processing
        PositionHandler(robotEntity).listener(command)
    }
}