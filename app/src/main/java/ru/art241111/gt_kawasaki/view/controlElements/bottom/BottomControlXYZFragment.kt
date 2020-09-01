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
import ru.art241111.gt_kawasaki.databinding.FragmentBottomControlXyzBinding
import ru.art241111.gt_kawasaki.repository.RepositoryForRobotApi
import ru.art241111.gt_kawasaki.repository.robotAPI.handlersFromKawasakiRobots.MethodWorkWhenCommandReceived
import ru.art241111.gt_kawasaki.view.util.AmountOfMovement
import ru.art241111.gt_kawasaki.view.util.Buttons
import ru.art241111.gt_kawasaki.view.util.WhenButtonHold
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BottomControlXYZFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomControlXYZFragment : Fragment(), MethodWorkWhenCommandReceived {
    private lateinit var binding: FragmentBottomControlXyzBinding
    private lateinit var viewModel: RobotViewModel

    private lateinit var repositoryForRobotApi: RepositoryForRobotApi

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_bottom_control_xyz, container, false)
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

        (activity as MainActivity).runOnUiThread(Runnable {
            binding.etXCoordinate.setText(viewModel.robot.robot.specifications.position[0].toString())
            binding.etYCoordinate.setText(viewModel.robot.robot.specifications.position[1].toString())
            binding.etZCoordinate.setText(viewModel.robot.robot.specifications.position[2].toString())
        })

    }

    override fun onStop() {
        viewModel.robot.removeMethodAtPointHandler(this)
        super.onStop()
    }

    private fun setClickListeners() {
        val whenButtonPressed = WhenButtonHold(repositoryForRobotApi)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.buttonZUp, Buttons.UpZ, AmountOfMovement.SLOW)
        whenButtonPressed.onTouchListener(binding.buttonZDown, Buttons.DownZ, AmountOfMovement.SLOW)

        // Move by X
        whenButtonPressed.onTouchListener(binding.buttonXUp, Buttons.UpX, AmountOfMovement.SLOW)
        whenButtonPressed.onTouchListener(binding.buttonXDown, Buttons.DownX, AmountOfMovement.SLOW)

        // Move by Z
        whenButtonPressed.onTouchListener(binding.buttonYUp, Buttons.UpY, AmountOfMovement.SLOW)
        whenButtonPressed.onTouchListener(binding.buttonYDown, Buttons.DownY, AmountOfMovement.SLOW)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BottomControlXYZFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = BottomControlXYZFragment().apply { }
    }
}