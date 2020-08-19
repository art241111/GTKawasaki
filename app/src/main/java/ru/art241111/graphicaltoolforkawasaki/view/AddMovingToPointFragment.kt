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
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentAddMoveActionBinding
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentAddMovingToPointBinding
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentShowProgramBinding
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddMovingToPointFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMovingToPointFragment : Fragment() {
    private lateinit var binding: FragmentAddMovingToPointBinding
    private lateinit var viewModel: RobotViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_moving_to_point, container, false)
        binding.executePendingBindings()

        setButtonListener()

        return binding.root
    }

    private fun setButtonListener() {
        binding.bSaveCommand.setOnClickListener {
            viewModel.programList.value?.add("MOVE TO POINT")

            findNavController().popBackStack()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AddMovingToPointFragment.
         */
        @JvmStatic
        fun newInstance() = AddMovingToPointFragment().apply {}
    }
}