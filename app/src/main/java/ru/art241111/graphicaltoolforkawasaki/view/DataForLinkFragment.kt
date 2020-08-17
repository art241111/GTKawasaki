package ru.art241111.graphicaltoolforkawasaki.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentControlPanelBinding
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentDataForLinkBinding
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel
import kotlin.concurrent.thread


/**
 * A simple [Fragment] subclass.
 * Use the [DataForLinkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataForLinkFragment : Fragment() {
    private lateinit var binding: FragmentDataForLinkBinding
    private lateinit var viewModel: RobotViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_data_for_link, container, false)
        binding.executePendingBindings()

        // TODO: Get from shared preferences
        binding.defaultIp = "192.168.31.52"
        binding.defaultPort = "49152"

        setButtonListener()
        return binding.root
    }

    private fun setButtonListener() {
        binding.bConnect.setOnClickListener {
            val repositoryForRobotApi = RepositoryForRobotApi()
            repositoryForRobotApi.connectToRobotTCP()

            try {
                Thread.sleep(500L)
            } catch (e: java.lang.Exception) {
            }

            if (!repositoryForRobotApi.isConnect()){
                repositoryForRobotApi.disconnect()
                Toast.makeText(activity as MainActivity,"Подключение не удалось", Toast.LENGTH_LONG).show()
            } else{
                viewModel.robot = repositoryForRobotApi
                findNavController().navigate(R.id.arrowControlsFragment)
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DataForLinkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = DataForLinkFragment().apply {}
    }
}