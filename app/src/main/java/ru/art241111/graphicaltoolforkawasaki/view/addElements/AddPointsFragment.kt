package ru.art241111.graphicaltoolforkawasaki.view.addElements

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
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentAddPointsBinding
import ru.art241111.graphicaltoolforkawasaki.repository.enities.Position
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddPointsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPointsFragment : Fragment() {
    private lateinit var binding: FragmentAddPointsBinding
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
            R.layout.fragment_add_points, container, false)
        binding.executePendingBindings()

        // Create buttonPressedListener and set it
        setClickListeners()

        loadInformation()

        return binding.root
    }

    private fun loadInformation() {
        position = -1

        arguments?.let {
            val key: String? = "position"
            position = it.getInt(key)
        }

        if(position != -1){
            binding.etPointName.text?.append((viewModel.pointList.value?.get(position)?.name))
        }
    }

    private fun setClickListeners() {
        binding.bAddPoint.setOnClickListener {
            val name = binding.etPointName.text.toString()
            val coordinate = viewModel.robot.robot.specifications.position

            if(name != "" ){
                if (position == -1){
                    viewModel.pointList.value?.add(Position(name,coordinate))
                } else{
                    viewModel.pointList.value?.set(position, Position(name,coordinate))
                }

                findNavController().popBackStack()
            } else{
                Toast.makeText(activity,"Вы не ввели значения", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AddPointsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =  AddPointsFragment().apply {}
    }
}