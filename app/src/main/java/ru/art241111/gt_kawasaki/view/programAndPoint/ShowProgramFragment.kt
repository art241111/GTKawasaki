package ru.art241111.gt_kawasaki.view.programAndPoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnDeleteButtonClick
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.gt_kawasaki.databinding.FragmentShowProgramBinding
import ru.art241111.gt_kawasaki.repository.enities.*
import ru.art241111.gt_kawasaki.utils.JsonHelper
import ru.art241111.gt_kawasaki.utils.sharedPreferences.SharedPreferencesHelperForString
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * Фрагмент отвечает за отображения всех сохраненых точек
 * @author Artem Gerasimov
 */
private const val APP_PREFERENCES = "Programs"
private const val APP_PREFERENCES_NAME = "programName"
class ShowProgramFragment : Fragment(), OnItemClickListener, OnDeleteButtonClick {
    private lateinit var binding: FragmentShowProgramBinding
    private lateinit var viewModel: RobotViewModel

    private lateinit var preferences: SharedPreferencesHelperForString
    private val jsonHelper = JsonHelper()

    private lateinit var customizationCommandRecyclerView: CustomizationCommandRecyclerView

    /**
     * При нажатии на кнопку delete в item в recyclerview -
     * удаление позиции из массива
     */
    override fun onDeleteButtonClick(position: Int) {
        viewModel.programList.value?.removeAt(position)
        customizationCommandRecyclerView.updateItems()
    }

    /**
     * При нажатии на item в recyclerview происходит
     * переход во фрагмент, который изменяет команду.
     */
    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)

        when(viewModel.programList.value?.get(position)){
            is MoveToPoint -> findNavController().navigate(R.id.addMovingToPointFragment, bundle)
            is Move -> findNavController().navigate(R.id.addMoveActionFragment, bundle)
        }
    }

    /**
     * При создании фрагмента: настройка dataBinding,
     *                         поключение viewModel,
     *                         настройка кнопки добавления точки,
     *                         настройка recyclerview,
     *                         получение сохраненных точек из sharedPreferences.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_show_program, container, false)
        binding.executePendingBindings()

        setAddButtonListener()

        // Customization RecycleView: set layoutManager, adapter, data.
        customizationCommandRecyclerView = CustomizationCommandRecyclerView(binding.rvShowProgram,
                activity as MainActivity,
                viewModel.programList,
                this,
                this, resources)

        getProgramFromSharedPreferences()

        return binding.root
    }

    /**
     * Получение старых точек из sharedPreferences
     */
    private fun getProgramFromSharedPreferences() {
        preferences = SharedPreferencesHelperForString(activity as MainActivity,APP_PREFERENCES)
        val oldCommands = jsonHelper.jsonArrayToRobotCommands(preferences.load(APP_PREFERENCES_NAME))

        if(viewModel.programList.value!!.isEmpty()){
            viewModel.programList.value?.addAll(oldCommands)
        } else{
            if(oldCommands.size != viewModel.programList.value!!.size){
                preferences.save(APP_PREFERENCES_NAME,jsonHelper.robotCommandsArrayToJsonString(viewModel.programList.value!!))
            }
        }
    }

    /**
     * При закрытии фрагмента - сохранение измененных точек
     */
    override fun onStop() {
        preferences.save(APP_PREFERENCES_NAME,jsonHelper.robotCommandsArrayToJsonString(viewModel.programList.value!!))
        super.onStop()
    }

    /**
     * При нажатии на кнопку Add появляется меню с
     * выбором команд
     */
    private fun setAddButtonListener() {
        binding.ibAddProgram.setOnClickListener {
            showPopup(it)
        }
    }

    /**
     * Создается меню для создания команд
     */
    private fun showPopup(view: View) {
        val popup = PopupMenu(activity, view)
        popup.inflate(R.menu.management_options_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.moveAction ->
                    findNavController().navigate(R.id.addMoveActionFragment)
                R.id.openGripper ->
                    viewModel.programList.value?.add(OpenGripper())
                R.id.closeGripper ->
                    viewModel.programList.value?.add(CloseGripper())
                R.id.moveToPointAction ->
                    findNavController().navigate(R.id.addMovingToPointFragment)
            }
            customizationCommandRecyclerView.updateItems()
            true
        }
        popup.show()
    }
}