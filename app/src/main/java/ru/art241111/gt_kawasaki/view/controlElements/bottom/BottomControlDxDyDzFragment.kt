package ru.art241111.gt_kawasaki.view.controlElements.bottom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.databinding.FragmentBottomControlDxdydzBinding
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots.MethodWorkWhenCommandReceived
import ru.art241111.gt_kawasaki.view.util.AmountOfMovement
import ru.art241111.gt_kawasaki.view.util.Buttons
import ru.art241111.gt_kawasaki.view.util.WhenButtonHold
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BottomControlDxDyDzFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomControlDxDyDzFragment : Fragment(), MethodWorkWhenCommandReceived {
    private lateinit var binding: FragmentBottomControlDxdydzBinding
    private lateinit var viewModel: RobotViewModel

    private lateinit var repositoryForRobotApi: RepositoryForRobotApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_bottom_control_dxdydz, container, false)
        binding.executePendingBindings()

        repositoryForRobotApi = viewModel.robot

        // Create buttonPressedListener and set it
        setClickListeners()

        setUpdateMethod()
        
        return binding.root
    }

    private fun setUpdateMethod() {
        viewModel.robot.addMethodAtPointHandler(this)
    }

    /**
     * Метод срабатывает, когда приходят новые координаты
     */
    override fun runMethodWhenHandlerWork() {
        Log.d("new_coordinate", "bottomControlXYZFragment - new value")
        TODO("Not yet implemented")
    }

    private fun setClickListeners() {
        val whenButtonPressed = WhenButtonHold(repositoryForRobotApi)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.buttonZUp, Buttons.UpDZ, AmountOfMovement.SLOW)
        whenButtonPressed.onTouchListener(binding.buttonZDown, Buttons.DownDZ, AmountOfMovement.SLOW)

        // Move by X
        whenButtonPressed.onTouchListener(binding.buttonXUp, Buttons.UpDX, AmountOfMovement.SLOW)
        whenButtonPressed.onTouchListener(binding.buttonXDown, Buttons.DownDX, AmountOfMovement.SLOW)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.buttonYUp, Buttons.UpDY, AmountOfMovement.SLOW)
        whenButtonPressed.onTouchListener(binding.buttonYDown, Buttons.DownDY, AmountOfMovement.SLOW)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BottomControlDxDyDzFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = BottomControlDxDyDzFragment().apply { }
    }
}