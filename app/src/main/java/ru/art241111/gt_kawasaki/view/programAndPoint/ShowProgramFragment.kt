package ru.art241111.gt_kawasaki.view.programAndPoint

import android.content.SharedPreferences
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
import ru.art241111.gt_kawasaki.view.util.CustomizationCommandRecyclerView
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ShowProgramFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val APP_PREFERENCES = "Programs"
private const val APP_PREFERENCES_NAME = "programName"
class ShowProgramFragment : Fragment(), OnItemClickListener, OnDeleteButtonClick {
    private lateinit var binding: FragmentShowProgramBinding
    private lateinit var viewModel: RobotViewModel

    private lateinit var preferences: SharedPreferencesHelperForString
    private val jsonHelper = JsonHelper()
    private var oldCommands = mutableListOf<RobotCommands>()

    private lateinit var customizationCommandRecyclerView: CustomizationCommandRecyclerView

    override fun onDeleteButtonClick(position: Int) {
        viewModel.programList.value?.removeAt(position)
        customizationCommandRecyclerView.updateItems()
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)

        when(viewModel.programList.value?.get(position)){
            is MoveToPoint -> findNavController().navigate(R.id.addMovingToPointFragment, bundle)
            is Move -> findNavController().navigate(R.id.addMoveActionFragment, bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_show_program, container, false)
        binding.executePendingBindings()

        setButtonListener()

        // Customization RecycleView: set layoutManager, adapter, data.
        customizationCommandRecyclerView = CustomizationCommandRecyclerView(binding.rvShowProgram,
                activity as MainActivity,
                viewModel.programList,
                this,
                this)

        getProgramFromSharedPreferences()

        return binding.root
    }

    private fun getProgramFromSharedPreferences() {
        preferences = SharedPreferencesHelperForString(activity as MainActivity,APP_PREFERENCES)
        oldCommands = jsonHelper.jsonArrayToRobotCommands(preferences.load(APP_PREFERENCES_NAME))

        if(viewModel.programList.value!!.isEmpty()){
            viewModel.programList.value?.addAll(oldCommands)
        } else{
            updateValueAtSharedPreferences()
        }
    }

    override fun onDestroyView() {
        updateValueAtSharedPreferences()
        super.onDestroyView()
    }

    private fun updateValueAtSharedPreferences(){
        if(oldCommands.size != viewModel.programList.value!!.size){
            preferences.save(APP_PREFERENCES_NAME,jsonHelper.robotCommandsArrayToJsonString(viewModel.programList.value!!))
        }
    }

    private fun setButtonListener() {
        binding.ibAddProgram.setOnClickListener {
            showPopup(it)
        }
    }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ShowProgramFragment.
         */
        @JvmStatic
        fun newInstance() = ShowProgramFragment().apply {}
    }


}