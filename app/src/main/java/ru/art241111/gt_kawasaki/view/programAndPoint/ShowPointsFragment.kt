package ru.art241111.gt_kawasaki.view.programAndPoint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.configuringRv.adapters.PointsRecyclerViewAdapter
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.gt_kawasaki.databinding.FragmentShowPointsBinding
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnDeleteButtonClick
import ru.art241111.gt_kawasaki.utils.JsonHelper
import ru.art241111.gt_kawasaki.utils.sharedPreferences.SharedPreferencesHelperForString

/**
 * Фрагмент отвечает за отображения всех сохраненых точек
 * @author Artem Gerasimov
 */
private const val APP_PREFERENCES = "Points"
private const val APP_PREFERENCES_NAME = "pointsName"
class ShowPointsFragment : Fragment(), OnItemClickListener, OnDeleteButtonClick {
    private lateinit var binding: FragmentShowPointsBinding
    private lateinit var viewModel: RobotViewModel

    private lateinit var preferences: SharedPreferencesHelperForString
    private val jsonHelper = JsonHelper()

    private lateinit var pointRecyclerView: PointsRecyclerViewAdapter

    /**
     * При нажатии на item в recyclerview происходит
     * переход во фрагмент, который изменяет позицию.
     */
    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)

        viewModel.robot.stopSendCommands()
        findNavController().navigate(R.id.addPointsFragment, bundle)
    }

    /**
     * При нажатии на кнопку delete в item в recyclerview -
     * удаление позиции из массива
     */
    override fun onDeleteButtonClick(position: Int) {
        viewModel.pointList.value?.removeAt(position)
        updateItems()
    }

    /**
     * При создании фрагмента: настройка dataBinding,
     *                         поключение viewModel,
     *                         настройка кнопки добавления точки,
     *                         настройка recyclerview,
     *                         получение сохраненных точек из sharedPreferences.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_show_points, container, false)
        binding.executePendingBindings()

        setAddPointButtonListener()

        // Customization RecycleView: set layoutManager, adapter, data.
        customizationRecycleView()

        getPointsFromSharedPreferences()

        return binding.root
    }

    /**
     * Получение старых точек из sharedPreferences
     */
    private fun getPointsFromSharedPreferences() {
        preferences = SharedPreferencesHelperForString(activity as MainActivity,APP_PREFERENCES)
        val oldCommands = jsonHelper.jsonArrayToPosition(preferences.load(APP_PREFERENCES_NAME))

        if(viewModel.pointList.value!!.isEmpty()){
            viewModel.pointList.value?.addAll(oldCommands)
        } else{
            if(oldCommands.size != viewModel.pointList.value!!.size){
                preferences.save(APP_PREFERENCES_NAME,jsonHelper.positionArrayToJsonString(viewModel.pointList.value!!))
            }
        }
    }

    /**
     * При закрытии фрагмента - сохранение измененных точек
     */
    override fun onStop() {
        preferences.save(APP_PREFERENCES_NAME,
                         jsonHelper.positionArrayToJsonString(viewModel.pointList.value!!))
        super.onStop()
    }

    /**
     * Настройка RecyclerView
     */
    private fun customizationRecycleView() {
        pointRecyclerView = PointsRecyclerViewAdapter(arrayListOf(), this,
                                           this, resources)

        binding.rvShowPoints.layoutManager = LinearLayoutManager(activity)
        binding.rvShowPoints.adapter = pointRecyclerView

        updateItems()
    }

    /**
     * Добавление onClickListener, который при нажатии будет
     * открывать фрагмент для создания новой точки
     */
    private fun setAddPointButtonListener() {
        binding.ibAddNewPoint.setOnClickListener {
            findNavController().navigate(R.id.addPointsFragment)
        }
    }

    /**
     * Обновление значение в recyclerView
     */
    private fun updateItems(){
        viewModel.pointList.observe(activity as MainActivity,
                {
                    it?.let{ pointRecyclerView.replaceData(it.toList())}
                })
    }
}