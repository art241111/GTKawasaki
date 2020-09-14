package ru.art241111.gt_kawasaki.view.addElements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.databinding.FragmentAddMovingToPointBinding
import ru.art241111.gt_kawasaki.repository.enities.commands.MoveToPoint
import ru.art241111.gt_kawasaki.repository.enities.enums.TypesOfMovementToThePoint
import ru.art241111.gt_kawasaki.utils.hideKeyboard
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddMovingToPointFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMovingToPointFragment : Fragment() {
    private lateinit var binding: FragmentAddMovingToPointBinding
    private lateinit var viewModel: RobotViewModel

    // Edit flag. If equal to -1, then new created
    private var position = -1

    /**
     * Get position value
     */
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
                R.layout.fragment_add_moving_to_point, container, false)
        binding.executePendingBindings()

        setAddButtonListener()
        setClickListenerOnCancelButton()
        passingAnArrayToTheChoosePointSpinner()

        //Verification a new position is created or an old one is edit
        loadInformation()

        return binding.root
    }

    private fun passingAnArrayToTheChoosePointSpinner() {
        // Получаем экземпляр элемента Spinner
        val spinner: Spinner = binding.spChoosePoint

        val pointName = mutableListOf<String>()
        viewModel.pointList.value!!.forEach{
            pointName.add(it.name)
        }

        // Настраиваем адаптер и добавляем массив точек
        val adapter: ArrayAdapter<*> = ArrayAdapter(activity as MainActivity,
                android.R.layout.simple_spinner_item,pointName)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Вызываем адаптер
        spinner.adapter = adapter
    }

    private fun setClickListenerOnCancelButton() {
        binding.bCancel.setOnClickListener {
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    /**
     * Verification a new position is created
     * or an old one is edit
     */
    private fun loadInformation() {
        if(position != -1){
            val command = viewModel.programList.value?.get(position) as MoveToPoint

            changeTextAtAddButtonAndHeader()
            setChoosePointSpinner(command)
            setChooseTypeOfMovementSpinner(command)
        }
    }
        private fun changeTextAtAddButtonAndHeader(){
            binding.bSaveCommand.text = resources.getText(R.string.change_command)
            binding.tvMoveToPoint.text = getString(R.string.change_move_to_point_command)
        }

        private fun setChooseTypeOfMovementSpinner(command: MoveToPoint){
            binding.spChooseTypeOfMovement.setSelection(
                    when (command.type) {
                        TypesOfMovementToThePoint.JMOVE -> 0
                        TypesOfMovementToThePoint.LMOVE -> 1
                    }
            )
        }

        private fun setChoosePointSpinner(command: MoveToPoint){
            binding.spChoosePoint.setSelection(command.coordinate.name)
        }

    private fun setAddButtonListener() {
        binding.bSaveCommand.setOnClickListener {
            createMovingToPointCommand(getTypeOfMoment())
        }
    }
        private fun getTypeOfMoment(): TypesOfMovementToThePoint{
            val optionsTypesOfMovement = resources.getStringArray(R.array.typeOfMovement)
            return when(binding.spChooseTypeOfMovement.selectedItem.toString()){
                        optionsTypesOfMovement[0] -> TypesOfMovementToThePoint.JMOVE
                        optionsTypesOfMovement[1] -> TypesOfMovementToThePoint.LMOVE
                        else -> TypesOfMovementToThePoint.LMOVE
                    }
        }

        private fun createMovingToPointCommand(typeOfMovement: TypesOfMovementToThePoint){
            val spinnerPosition = binding.spChoosePoint.selectedItemPosition
            if(spinnerPosition == -1){
                Toast.makeText(activity as MainActivity,
                        R.string.create_point_if_you_want_use_move_to_point,
                        Toast.LENGTH_LONG).show()
            } else{
                val robotPosition = viewModel.pointList.value?.get(spinnerPosition)
                if(position == -1){
                    viewModel.programList.value?.add(MoveToPoint(typeOfMovement, robotPosition!!))
                } else{
                    viewModel.programList.value?.set(position, MoveToPoint(typeOfMovement, robotPosition!!))
                }

                hideKeyboard()
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