package ru.art241111.graphicaltoolforkawasaki.view.controlElements.arrow

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
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import ru.art241111.graphicaltoolforkawasaki.view.util.AmountOfMovement
import ru.art241111.graphicaltoolforkawasaki.view.util.Buttons
import ru.art241111.graphicaltoolforkawasaki.view.util.WhenButtonHold
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ArrowControlsDxDyDzFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArrowControlsDxDyDzFragment : Fragment() {
    private lateinit var binding: FragmentArrowControlsDxdydzBinding
    private lateinit var viewModel: RobotViewModel

    private lateinit var repositoryForRobotApi: RepositoryForRobotApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_arrow_controls_dxdydz, container, false)
        binding.executePendingBindings()

        repositoryForRobotApi = viewModel.robot

        // Create buttonPressedListener and set it
        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        val whenButtonPressed = WhenButtonHold(repositoryForRobotApi)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.ibUpZ, Buttons.UpDZ, AmountOfMovement.FAST)
        whenButtonPressed.onTouchListener(binding.ibDownZ, Buttons.DownDZ, AmountOfMovement.FAST)

        // Move by X
        whenButtonPressed.onTouchListener(binding.ibRightX, Buttons.UpDX, AmountOfMovement.FAST)
        whenButtonPressed.onTouchListener(binding.ibLeftX, Buttons.DownDX, AmountOfMovement.FAST)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.ibUpY, Buttons.UpDY, AmountOfMovement.FAST)
        whenButtonPressed.onTouchListener(binding.ibDownY, Buttons.DownDY, AmountOfMovement.FAST)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment fragment_arrow_controls_dxdydz.
         */
        @JvmStatic
        fun newInstance() = ArrowControlsDxDyDzFragment().apply{}
    }
}