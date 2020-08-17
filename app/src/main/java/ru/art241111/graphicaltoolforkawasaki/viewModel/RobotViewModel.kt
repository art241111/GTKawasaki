package ru.art241111.graphicaltoolforkawasaki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi

class RobotViewModel(application: Application)
    : AndroidViewModel(application) {

    lateinit var robot: RepositoryForRobotApi
}