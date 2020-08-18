package ru.art241111.graphicaltoolforkawasaki.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.adapters.PointsRecyclerViewAdapter
import ru.art241111.graphicaltoolforkawasaki.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentShowPointsBinding
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel
import androidx.lifecycle.Observer

/**
 * A simple [Fragment] subclass.
 * Use the [ShowPointsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowPointsFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: FragmentShowPointsBinding
    private lateinit var viewModel: RobotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_show_points, container, false)
        binding.executePendingBindings()

        setButtonListener()

        // Customization RecycleView: set layoutManager, adapter, data.
        customizationRecycleView()

        return binding.root
    }

    private fun customizationRecycleView() {
        val pointRecyclerView = PointsRecyclerViewAdapter(arrayListOf(), this)

        binding.rvShowPoints.layoutManager = LinearLayoutManager(activity)
        binding.rvShowPoints.adapter = pointRecyclerView

        viewModel.pointList.observe(activity as MainActivity,
            Observer{
                it?.let{ pointRecyclerView.replaceData(it.toList())}
            })
    }

    private fun setButtonListener() {
        binding.ibAddNewPoint.setOnClickListener {
            findNavController().navigate(R.id.addPointsFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment ShowPointsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ShowPointsFragment().apply {}
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}