package ru.art241111.graphicaltoolforkawasaki.view

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.ProgramRecyclerViewAdapter
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.configuringRv.helpers.SimpleItemTouchHelperCallback
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentShowProgramBinding
import ru.art241111.graphicaltoolforkawasaki.view.util.ItemTouchHelperAdapterImp
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel



/**
 * A simple [Fragment] subclass.
 * Use the [ShowProgramFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val APP_PREFERENCES = "Programs"
private const val APP_PREFERENCES_NAME = "programName"
class ShowProgramFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: FragmentShowProgramBinding
    private lateinit var viewModel: RobotViewModel

    private lateinit var programRecyclerView: ProgramRecyclerViewAdapter
    private var preferences: SharedPreferences? = null

    override fun onItemClick(position: Int) {
        viewModel.programList.value?.removeAt(position)
        updateItems()
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
        customizationRecycleView()

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
                                         defaultValue: MutableList<String>): MutableList<String>? {
        return if(preferences!!.contains(key)) {
            preferences!!.getStringSet(key, mutableSetOf())?.toMutableList() ?: defaultValue

        } else {
            updateSharedPreferences(key, defaultValue)

            defaultValue
        }
    }

    private fun updateSharedPreferences(preferencesKey: String, newValue: MutableList<String>) {
        if(preferences != null){
            val editor: SharedPreferences.Editor = preferences!!.edit()
            editor.putStringSet(preferencesKey, newValue.toSet())
            editor.apply()
        }
    }

    override fun onDestroyView() {
        updateSharedPreferences(APP_PREFERENCES_NAME, viewModel.programList.value!!)
        super.onDestroyView()
    }

    private fun customizationRecycleView() {
        programRecyclerView = ProgramRecyclerViewAdapter(arrayListOf(),
                this,
                ItemTouchHelperAdapterImp(viewModel.programList.value!!))

        binding.rvShowProgram.layoutManager = LinearLayoutManager(activity)
        binding.rvShowProgram.adapter = programRecyclerView

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(programRecyclerView)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvShowProgram)

        updateItems()
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
                    viewModel.programList.value?.add(item.title.toString())
                R.id.closeGripper ->
                    viewModel.programList.value?.add(item.title.toString())
                R.id.moveToPointAction ->
                    findNavController().navigate(R.id.addMovingToPointFragment)
            }
            updateItems()
            true
        }

        popup.show()
    }

    private fun updateItems(){
        viewModel.programList.observe(activity as MainActivity,
                Observer {
                    it?.let { programRecyclerView.replaceData(it.toList()) }
                })
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