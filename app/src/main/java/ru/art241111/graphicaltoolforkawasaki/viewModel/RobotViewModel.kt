package ru.art241111.graphicaltoolforkawasaki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi

class RobotViewModel(application: Application)
    : AndroidViewModel(application) {

    var robot: RepositoryForRobotApi = RepositoryForRobotApi()

    val pointList = MutableLiveData<MutableList<String>>()
    val programList = MutableLiveData<MutableList<String>>()
}