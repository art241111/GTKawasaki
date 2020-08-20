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
import ru.art241111.graphicaltoolforkawasaki.repository.enity.Move
import ru.art241111.graphicaltoolforkawasaki.repository.enity.enums.Coordinate
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddMoveActionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMoveActionFragment : Fragment() {
    private lateinit var binding: FragmentAddMoveActionBinding
    private lateinit var viewModel: RobotViewModel

    private var position = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_move_action, container, false)
        binding.executePendingBindings()

        setButtonListener()

        loadInformation()


        return binding.root
    }

    // TODO: Add editing
    private fun loadInformation() {
        position = -1
        arguments?.let {
            val key: String? = "position"
            position = it.getInt(key)
        }
        if(position != -1){
            val command = viewModel.programList.value?.get(position) as Move
            binding.etTheShiftDistance.text?.append(command.sizeOfPlant.toString())
            binding.spinner.setSelection(when(command.coordinate){
                Coordinate.X -> 0
                Coordinate.Y -> 1
                Coordinate.Z -> 2
                Coordinate.DX -> 3
                Coordinate.DY -> 4
                Coordinate.DZ -> 5
            })
        }
    }

    private fun setButtonListener() {
        binding.bAddMoveAction.setOnClickListener {
            val value = binding.etTheShiftDistance.text.toString()
            val coordinate1 = binding.spinner.selectedItem.toString()

            var coordinate = Coordinate.X
            when(coordinate1){
                    "X"-> coordinate = Coordinate.X
                    "Y"-> coordinate = Coordinate.Y
                    "Z"-> coordinate = Coordinate.Z
                    "DX"-> coordinate = Coordinate.DX
                    "DY"-> coordinate = Coordinate.DY
                    "DZ"-> coordinate = Coordinate.DZ
            }

            if(position == -1){
                viewModel.programList.value?.add(Move(coordinate, value.toInt()))
            } else{
                viewModel.programList.value?.set(position, Move(coordinate, value.toInt()))
            }

            findNavController().popBackStack()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AddMoveActionFragment.
         */
        @JvmStatic
        fun newInstance() = AddMoveActionFragment().apply { }
    }
}