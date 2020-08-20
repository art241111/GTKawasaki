package ru.art241111.graphicaltoolforkawasaki.view

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
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentAddMovingToPointBinding
import ru.art241111.graphicaltoolforkawasaki.repository.enity.MoveToPoint
import ru.art241111.graphicaltoolforkawasaki.repository.enity.enums.TypesOfMovementToThePoint
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [AddMovingToPointFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMovingToPointFragment : Fragment() {
    private lateinit var binding: FragmentAddMovingToPointBinding
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
                R.layout.fragment_add_moving_to_point, container, false)
        binding.executePendingBindings()

        setButtonListener()
        setSpinner()
        loadInformation()

        return binding.root
    }

    private fun setSpinner() {
        // Получаем экземпляр элемента Spinner
        val spinner: Spinner = binding.spChoosePoint

        // Настраиваем адаптер
        val adapter: ArrayAdapter<*> = ArrayAdapter(activity as MainActivity,
                android.R.layout.simple_spinner_item, viewModel.pointList.value!!)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Вызываем адаптер
        spinner.adapter = adapter
    }

    // TODO: Add editing
    private fun loadInformation() {
        position = -1
        arguments?.let {
            val key: String? = "position"
            position = it.getInt(key)
        }

        if(position != -1){
            val command = viewModel.programList.value?.get(position) as MoveToPoint
            binding.spChooseTypeOfMovement.setSelection(
                    when (command.type) {
                        TypesOfMovementToThePoint.JMOVE -> 0
                        TypesOfMovementToThePoint.LMOVE -> 1
                    }
            )

        }
    }

    private fun setButtonListener() {
        binding.bSaveCommand.setOnClickListener {

            val typeOfMovement =
                    when(binding.spChooseTypeOfMovement.selectedItem.toString()){
                        "Линейно" -> TypesOfMovementToThePoint.LMOVE
                        "По осям" -> TypesOfMovementToThePoint.JMOVE
                        else -> TypesOfMovementToThePoint.JMOVE
                    }


            val spinnerPosition = binding.spChoosePoint.selectedItemPosition
            if(spinnerPosition == -1){
                Toast.makeText(activity as MainActivity,
                        "Чтобы использовать данную команду - нужно создать точку",
                        Toast.LENGTH_LONG).show()
            } else{
                val robotPosition = viewModel.pointList.value?.get(spinnerPosition)
                if(position == -1){
                    viewModel.programList.value?.add(MoveToPoint(typeOfMovement, robotPosition!!))
                } else{
                    viewModel.programList.value?.set(position, MoveToPoint(typeOfMovement, robotPosition!!))
                }

                findNavController().popBackStack()
            }
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