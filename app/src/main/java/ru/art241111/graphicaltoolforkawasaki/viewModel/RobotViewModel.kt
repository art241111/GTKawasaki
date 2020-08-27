package ru.art241111.graphicaltoolforkawasaki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.art241111.graphicaltoolforkawasaki.repository.enities.Position
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import ru.art241111.graphicaltoolforkawasaki.repository.enities.RobotCommands
import ru.art241111.graphicaltoolforkawasaki.utils.Delay

class RobotViewModel(application: Application)
    : AndroidViewModel(application) {

    var robot: RepositoryForRobotApi = RepositoryForRobotApi()

    val pointList = MutableLiveData<MutableList<Position>>()
    val programList = MutableLiveData<MutableList<RobotCommands>>()

    init {
        programList.value = arrayListOf()
        pointList.value = arrayListOf()
    }

    fun createConnection(ip: String, port: Int): Boolean{
        val repositoryForRobotApi = RepositoryForRobotApi()
        repositoryForRobotApi.connectToRobotTCP(address = ip, port = port)

        Delay.customDelay(500L)

        return if(repositoryForRobotApi.isConnect()){
            robot = repositoryForRobotApi
            true
        } else{
            repositoryForRobotApi.disconnect()
            false
        }
    }

}