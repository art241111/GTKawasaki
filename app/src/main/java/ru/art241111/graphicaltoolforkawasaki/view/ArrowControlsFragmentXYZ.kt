package ru.art241111.graphicaltoolforkawasaki.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentArrowControlsXyzBinding
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import ru.art241111.graphicaltoolforkawasaki.view.util.Buttons
import ru.art241111.graphicaltoolforkawasaki.view.util.WhenButtonPressed
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass.
 * Use the [ArrowControlsFragmentXYZ.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArrowControlsFragmentXYZ : Fragment() {
    private lateinit var binding: FragmentArrowControlsXyzBinding
    private lateinit var viewModel: RobotViewModel
    private lateinit var repositoryForRobotApi: RepositoryForRobotApi

    private lateinit var whenButtonPressed: WhenButtonPressed

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_arrow_controls_xyz, container, false)
        binding.executePendingBindings()

        repositoryForRobotApi = RepositoryForRobotApi()
        whenButtonPressed = WhenButtonPressed(repositoryForRobotApi)
        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        // Move by Z
        onTouchListener(binding.ibUpZ, Buttons.UpZ)
        onTouchListener(binding.ibDownZ, Buttons.DownZ)

        // Move by X
        onTouchListener(binding.ibRightX, Buttons.UpX)
        onTouchListener(binding.ibLeftX, Buttons.DownX)

        // Move by Z
        onTouchListener(binding.ibUpY, Buttons.UpY)
        onTouchListener(binding.ibDownY, Buttons.DownY)
    }

    private fun onButtonUpZClickListener() {
        onTouchListener(binding.ibUpZ, Buttons.UpZ)
    }

    private fun onTouchListener(view: View, button: Buttons){
        view.setOnTouchListener(View.OnTouchListener { v, event -> when(event.action){
            MotionEvent.ACTION_DOWN -> {
                whenButtonPressed.press = true
                whenButtonPressed.arrowPressed(button)
            }
            MotionEvent.ACTION_UP ->{
                whenButtonPressed.press = false
            }

        }
            return@OnTouchListener false

        })
    }

    override fun onDestroyView() {
        repositoryForRobotApi.disconnect()
        super.onDestroyView()
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
