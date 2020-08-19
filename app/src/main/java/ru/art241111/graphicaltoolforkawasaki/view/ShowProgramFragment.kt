package ru.art241111.graphicaltoolforkawasaki.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.adapters.PointsRecyclerViewAdapter
import ru.art241111.graphicaltoolforkawasaki.adapters.ProgramRecyclerViewAdapter
import ru.art241111.graphicaltoolforkawasaki.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentShowPointsBinding
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentShowProgramBinding
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ShowProgramFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowProgramFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: FragmentShowProgramBinding
    private lateinit var viewModel: RobotViewModel

    private lateinit var programRecyclerView: ProgramRecyclerViewAdapter

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

        return binding.root
    }

    private fun customizationRecycleView() {
        programRecyclerView = ProgramRecyclerViewAdapter(arrayListOf(), this)

        binding.rvShowProgram.layoutManager = LinearLayoutManager(activity)
        binding.rvShowProgram.adapter = programRecyclerView

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

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.moveAction ->
                    findNavController().navigate(R.id.addMoveActionFragment)
                R.id.openGripper ->
                    viewModel.programList.value?.add(item.title.toString())
                R.id.closeGripper ->
                    viewModel.programList.value?.add(item.title.toString())

            }
            updateItems()
            true
        })

        popup.show()
    }

    private fun updateItems(){
        viewModel.programList.observe(activity as MainActivity,
            Observer{
                it?.let{ programRecyclerView.replaceData(it.toList())}
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