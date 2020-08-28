package ru.art241111.graphicaltoolforkawasaki.view.programAndPoint

import android.content.Context
import android.content.SharedPreferences
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
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.PointsRecyclerViewAdapter
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentShowPointsBinding
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel
import androidx.lifecycle.Observer
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnDeleteButtonClick

/**
 * A simple [Fragment] subclass.
 * Use the [ShowPointsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val APP_PREFERENCES = "Points"
private const val APP_PREFERENCES_NAME = "pointsName"
class ShowPointsFragment : Fragment(), OnItemClickListener, OnDeleteButtonClick {
    private lateinit var binding: FragmentShowPointsBinding
    private lateinit var viewModel: RobotViewModel

    private var preferences: SharedPreferences? = null

    private lateinit var pointRecyclerView: PointsRecyclerViewAdapter

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)

        findNavController().navigate(R.id.addPointsFragment, bundle)
    }

    override fun onDeleteButtonClick(position: Int) {
        viewModel.pointList.value?.removeAt(position)
        updateItems()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_show_points, container, false)
        binding.executePendingBindings()

        setAddPointButtonListener()

        // Customization RecycleView: set layoutManager, adapter, data.
        customizationRecycleView()

        getPointsFromSharedPreferences()

        return binding.root
    }

    private fun getPointsFromSharedPreferences() {
        preferences = this.activity
                ?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        if(viewModel.pointList.value!!.isEmpty()){
            if(preferences != null){
//                viewModel.pointList.value = getFromSharedPreferences(APP_PREFERENCES_NAME, viewModel.pointList.value!!)
            }
        }
    }

    private fun customizationRecycleView() {
        pointRecyclerView = PointsRecyclerViewAdapter(arrayListOf(), this, this)

        binding.rvShowPoints.layoutManager = LinearLayoutManager(activity)
        binding.rvShowPoints.adapter = pointRecyclerView

        updateItems()
    }

    private fun setAddPointButtonListener() {
        binding.ibAddNewPoint.setOnClickListener {
            findNavController().navigate(R.id.addPointsFragment)
        }
    }

    private fun updateItems(){
        viewModel.pointList.observe(activity as MainActivity,
            Observer{
                it?.let{ pointRecyclerView.replaceData(it.toList())}
            })
    }

    override fun onDestroyView() {
//        updateSharedPreferences(APP_PREFERENCES_NAME, viewModel.pointList.value!!)
        super.onDestroyView()
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




}