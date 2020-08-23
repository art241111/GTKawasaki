package ru.art241111.graphicaltoolforkawasaki.view.programAndPoint

import android.content.Context
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
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnDeleteButtonClick
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentShowProgramBinding
import ru.art241111.graphicaltoolforkawasaki.repository.enities.*
import ru.art241111.graphicaltoolforkawasaki.view.util.CustomizationCommandRecyclerView
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel


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


    private var preferences: SharedPreferences? = null
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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
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
        preferences = this.activity
                ?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        if(viewModel.programList.value!!.isEmpty()){
            if(preferences != null){
                viewModel.programList.value = getFromSharedPreferences(APP_PREFERENCES_NAME, viewModel.programList.value!!)
            }
        }
    }

    private fun getFromSharedPreferences(key: String,
                                         defaultValue: MutableList<RobotCommands>): MutableList<RobotCommands>? {
        if(preferences!!.contains(key)) {
            preferences!!.getStringSet(key, mutableSetOf())?.toMutableList() ?: defaultValue

        } else {
            updateSharedPreferences(key, defaultValue)

            defaultValue
        }
        return defaultValue
    }

    private fun updateSharedPreferences(preferencesKey: String, newValue: MutableList<RobotCommands>) {
        if(preferences != null){
            val editor: SharedPreferences.Editor = preferences!!.edit()
//            editor.putStringSet(preferencesKey, newValue.toSet())
            editor.apply()
        }
    }

    override fun onDestroyView() {
        updateSharedPreferences(APP_PREFERENCES_NAME, viewModel.programList.value!!)
        super.onDestroyView()
    }

    private fun setButtonListener() {
        binding.ibAddProgram.setOnClickListener {
            if(viewModel.programList.value == null)
                viewModel.programList.value = arrayListOf()

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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ShowProgramFragment().apply {}
    }


}