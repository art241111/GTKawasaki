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
import ru.art241111.gt_kawasaki.databinding.FragmentAddPointsBinding
import ru.art241111.gt_kawasaki.repository.enities.Position
import ru.art241111.gt_kawasaki.repository.enities.enums.TypesOfMovementToThePoint
import ru.art241111.gt_kawasaki.utils.hideKeyboard
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddPointsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPointsFragment : Fragment() {
    private lateinit var binding: FragmentAddPointsBinding
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
            R.layout.fragment_add_points, container, false)
        binding.executePendingBindings()

        // Create buttonPressedListener and set it
        setClickListeners()

        loadInformation()

        return binding.root
    }

    private fun loadInformation() {
        if(position != -1){
            val position = viewModel.pointList.value?.get(position)

            if(position != null){
                binding.etPointName.text?.append(position.name)
                viewModel.robot.moveToPoint(TypesOfMovementToThePoint.LMOVE, position.position)
            }

            binding.bAddPoint.text = resources.getText(R.string.change_point)
            binding.textView.text = resources.getText(R.string.change_point_header)
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

                hideKeyboard()
                findNavController().popBackStack()
            } else{
                Toast.makeText(activity,R.string.do_not_enter_value, Toast.LENGTH_LONG).show()
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