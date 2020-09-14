package ru.art241111.gt_kawasaki.view.addElements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.databinding.FragmentAddMoveActionBinding
import ru.art241111.gt_kawasaki.repository.enities.commands.Move
import ru.art241111.gt_kawasaki.repository.enities.enums.Coordinate
import ru.art241111.gt_kawasaki.utils.hideKeyboard
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddMoveActionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMoveActionFragment : Fragment() {
    private lateinit var binding: FragmentAddMoveActionBinding
    private lateinit var viewModel: RobotViewModel

    // Edit flag. If equal to -1, then new created
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            val key: String? = "position"
            position = it.getInt(key)
        }

        super.onCreate(savedInstanceState)
    }

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

        setAddCommandButtonListener()
        setClickListenerOnCancelButton()

        loadInformation()

        return binding.root
    }

    private fun setClickListenerOnCancelButton() {
        binding.bCancel.setOnClickListener {
            popStack()
        }
    }

    /**
     * Если при открытии фрагмента передана позиция команды, то
     * нужно загрузить данные и изменить текстовые поля
     */
    private fun loadInformation() {
        if(position != -1){
            val command = viewModel.programList.value?.get(position) as Move

            binding.tvMove.text = getString(R.string.change_move_command)
            binding.etTheShiftDistance.text?.append(command.sizeOfPlant.toString())
            binding.bAddMoveAction.text = resources.getText(R.string.change_command)

            setCoordinateSpinner(command.coordinate)
        }
    }

    private fun setCoordinateSpinner(coordinate: Coordinate){
        binding.spCoordinate.setSelection(when(coordinate){
            Coordinate.X -> 0
            Coordinate.Y -> 1
            Coordinate.Z -> 2
            Coordinate.DX -> 3
            Coordinate.DY -> 4
            Coordinate.DZ -> 5
        })
    }

    private fun setAddCommandButtonListener() {
        binding.bAddMoveAction.setOnClickListener {
            val value = binding.etTheShiftDistance.text.toString()

            val coordinate = getCoordinate()

            if (value == ""){
                Toast.makeText(activity, R.string.enter_distance_to_move, Toast.LENGTH_LONG).show()
            } else{
                addOrChangeValue(Move(coordinate, value.toDouble()))
                popStack()
            }
        }
    }

        private fun getCoordinate(): Coordinate =
                when(binding.spCoordinate.selectedItem.toString()){
                    "X"-> Coordinate.X
                    "Y"-> Coordinate.Y
                    "Z"-> Coordinate.Z
                    "DX"-> Coordinate.DX
                    "DY"-> Coordinate.DY
                    "DZ"-> Coordinate.DZ
                    else -> Coordinate.X
                }

        private fun addOrChangeValue(move: Move){
            if(position == -1){
                viewModel.programList.value?.add(move)
            } else{
                viewModel.programList.value?.set(position, move)
            }
        }

    private fun popStack(){
        hideKeyboard()
        findNavController().popBackStack()
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