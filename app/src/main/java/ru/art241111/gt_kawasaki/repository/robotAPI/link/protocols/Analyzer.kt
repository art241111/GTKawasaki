package link.protocols

import ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots.Handler

interface Analyzer {
    fun commandAnalysis(command:String, handlers: List<Handler> = listOf())
}