package ru.art241111.graphicaltoolforkawasaki.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentArrowControlsDxdydzBinding
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentArrowControlsXyzBinding
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel
import java.lang.Exception
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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


        repositoryForRobotApi = RepositoryForRobotApi()
        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        // Move by Z
        onButtonUpZClickListener()
        onButtonDownZClickListener()

        // Move by X
        onButtonRightXClickListener()
        onButtonLeftXClickListener()

        // Move by Z
        onButtonUpYClickListener()
        onButtonDownYClickListener()
    }

    private fun onButtonUpZClickListener() {
        binding.ibUpZ.setOnTouchListener(View.OnTouchListener { v, event -> when(event.action){
            MotionEvent.ACTION_DOWN -> {
                press = true
                pressZUp()
            }
            MotionEvent.ACTION_UP ->{
                press = false
            }

        }
            return@OnTouchListener false
        })
    }

    var press = false
    private fun pressZUp(){
        thread {
            while (press){
                repositoryForRobotApi.moveByZ(1)
                try {
                    Thread.sleep(50L)
                } catch (e: Exception) {
                }
            }
        }
    }


    private fun onButtonDownZClickListener() {
        binding.ibDownZ.setOnClickListener{
            repositoryForRobotApi.moveByZ(-1)
        }
    }

    private fun onButtonRightXClickListener() {
        binding.ibRightX.setOnClickListener{
            repositoryForRobotApi.moveByX(1)
        }
    }

    private fun onButtonLeftXClickListener() {
        binding.ibLeftX.setOnClickListener{
            repositoryForRobotApi.moveByX(-1)
        }
    }

    private fun onButtonUpYClickListener() {
        binding.ibUpY.setOnClickListener {
            repositoryForRobotApi.moveByY(1)
        }
    }

    private fun onButtonDownYClickListener() {
        binding.ibDownY.setOnClickListener{
            repositoryForRobotApi.moveByY(-1)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_arrow_controls_dxdydz.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ArrowControlsDxDyDzFragment()
                    .apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}