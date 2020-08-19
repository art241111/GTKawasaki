package ru.art241111.graphicaltoolforkawasaki.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentControlPanelBinding
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel


class ControlPanel : Fragment() {
    private lateinit var binding: FragmentControlPanelBinding
    private lateinit var viewModel: RobotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_control_panel, container, false)
        binding.executePendingBindings()

        binding.connectStatus = viewModel.robot.isConnect()
        setConnectButtonListener()
        setRunButtonListener()

        return binding.root

    }

    private fun setRunButtonListener() {
        binding.ibRunProgram.setOnClickListener {
            viewModel.robot.sendCommand(viewModel.programList.value!!)
        }
    }

    private fun setConnectButtonListener() {
        binding.ivConnection.setOnClickListener {
            if(!binding.connectStatus!!){
                findNavController().navigate(R.id.dataForLinkFragment)
            } else{
                viewModel.robot.disconnect()

                binding.connectStatus = viewModel.robot.isConnect()
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ControlPanel.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ControlPanel().apply { }
    }
}