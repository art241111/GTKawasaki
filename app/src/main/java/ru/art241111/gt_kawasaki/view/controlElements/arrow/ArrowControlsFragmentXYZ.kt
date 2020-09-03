package ru.art241111.gt_kawasaki.view.controlElements.arrow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.databinding.FragmentArrowControlsXyzBinding
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.view.util.AmountOfMovement
import ru.art241111.gt_kawasaki.view.util.Buttons
import ru.art241111.gt_kawasaki.view.util.WhenButtonHold
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ArrowControlsFragmentXYZ.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArrowControlsFragmentXYZ : Fragment() {
    private lateinit var binding: FragmentArrowControlsXyzBinding
    private lateinit var viewModel: RobotViewModel

    private lateinit var repositoryForRobotApi: RepositoryForRobotApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_arrow_controls_xyz, container, false)
        binding.executePendingBindings()

        repositoryForRobotApi = viewModel.robot

        // Create buttonPressedListener and set it
        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        val whenButtonPressed = WhenButtonHold(viewModel)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.ibUpZ, Buttons.UpZ,AmountOfMovement.FAST)
        whenButtonPressed.onTouchListener(binding.ibDownZ, Buttons.DownZ,AmountOfMovement.FAST)

        // Move by X
        whenButtonPressed.onTouchListener(binding.ibRightX, Buttons.UpX,AmountOfMovement.FAST)
        whenButtonPressed.onTouchListener(binding.ibLeftX, Buttons.DownX,AmountOfMovement.FAST)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.ibUpY, Buttons.UpY,AmountOfMovement.FAST)
        whenButtonPressed.onTouchListener(binding.ibDownY, Buttons.DownY,AmountOfMovement.FAST)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ArrowControlsFragment.
         */
        @JvmStatic
        fun newInstance() = ArrowControlsFragmentXYZ()
    }
}
