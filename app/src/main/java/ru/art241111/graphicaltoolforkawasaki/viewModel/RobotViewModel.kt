package ru.art241111.graphicaltoolforkawasaki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.art241111.graphicaltoolforkawasaki.repository.enities.Position
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import ru.art241111.graphicaltoolforkawasaki.repository.enities.RobotCommands

class RobotViewModel(application: Application)
    : AndroidViewModel(application) {

    var robot: RepositoryForRobotApi = RepositoryForRobotApi()

    val pointList = MutableLiveData<MutableList<Position>>()
    val programList = MutableLiveData<MutableList<RobotCommands>>()

    init {
        programList.value = arrayListOf()
        pointList.value = arrayListOf()
    }
}