package ru.art241111.gt_kawasaki.viewModel

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.art241111.gt_kawasaki.utils.enitiesCommandsAndPosition.Position
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.utils.enitiesCommandsAndPosition.commands.RobotCommands
import ru.art241111.gt_kawasaki.utils.Delay
import ru.art241111.gt_kawasaki.view.util.RobotControlModel

class RobotViewModel(application: Application)
    : AndroidViewModel(application) {
    // Создание объекта, хранящего коннект с роботом
    var robot: RepositoryForRobotApi = RepositoryForRobotApi()
    var isProgramRun = robot.getProgramStatusValue()
    var robotPosition = ObservableArrayList<Double>()

    // Массив для хранения всех точек
    val pointList = MutableLiveData<MutableList<Position>>()
    // Массив для хранения всех комманд
    val programList = MutableLiveData<MutableList<RobotCommands>>()

    // Объект, который определяет реакцию на удержание кнопок
    val controlModel = RobotControlModel(this)

    /**
     * Инициализация массивов пустыми массивами
     */
    init {
        programList.value = arrayListOf()
        pointList.value = arrayListOf()
        robotPosition.addAll(listOf(0.0,0.0,0.0,0.0,0.0,0.0))
    }

    /**
     * Отключаемся от робота, останавливаем все потоки
     */
    fun disconnect(){
        robot.disconnect()
        controlModel.isSending = false
    }

    /**
     * Создаем новый коннект с роботом. Если
     * программа соединилась с роботом, то коннект
     * сохраняется во view model
     */
    fun createConnection(ip: String, port: Int?): Boolean =
        if(ip != "" && port != null){
            val repositoryForRobotApi = RepositoryForRobotApi()
            repositoryForRobotApi.connectToRobotTCP(address = ip, port = port)

            Delay.customDelay(500L)
            if(repositoryForRobotApi.isConnect()){
                robot = repositoryForRobotApi
                controlModel.startSending()
                true
            } else{
                repositoryForRobotApi.disconnect()
                false
            }
        } else{
            false
        }
}