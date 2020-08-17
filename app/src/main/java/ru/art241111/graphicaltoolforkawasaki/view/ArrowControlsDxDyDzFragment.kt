package ru.art241111.graphicaltoolforkawasaki.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentArrowControlsDxdydzBinding
import ru.art241111.graphicaltoolforkawasaki.view.util.Buttons
import ru.art241111.graphicaltoolforkawasaki.view.util.WhenButtonPressed
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ArrowControlsDxDyDzFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArrowControlsDxDyDzFragment : Fragment() {
    private lateinit var binding: FragmentArrowControlsDxdydzBinding
    private lateinit var viewModel: RobotViewModel

    private var repositoryForRobotApi = viewModel.robot

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_arrow_controls_dxdydz, container, false)
        binding.executePendingBindings()

        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        val whenButtonPressed = WhenButtonPressed(repositoryForRobotApi)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.ibUpZ, Buttons.UpDZ)
        whenButtonPressed.onTouchListener(binding.ibDownZ, Buttons.DownDZ)

        // Move by X
        whenButtonPressed.onTouchListener(binding.ibRightX, Buttons.UpDX)
        whenButtonPressed.onTouchListener(binding.ibLeftX, Buttons.DownDX)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.ibUpY, Buttons.UpDY)
        whenButtonPressed.onTouchListener(binding.ibDownY, Buttons.DownDY)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment fragment_arrow_controls_dxdydz.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =ArrowControlsDxDyDzFragment().apply{}
    }
}