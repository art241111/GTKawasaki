package ru.art241111.graphicaltoolforkawasaki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentArrowControlsBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ArrowControlsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArrowControlsFragment : Fragment() {
    private lateinit var binding: FragmentArrowControlsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_arrow_controls, container, false)
        binding.executePendingBindings()

        setTabHost()
        return binding.root
    }

    private fun setTabHost() {
        val tabHost = binding.tabHost

        if(tabHost != null){
            tabHost.setup()

            var tabSpec =  tabHost.newTabSpec("tabControlXYZ")

            tabSpec.setContent(binding.tabControlXYZ!!.id)
            tabSpec.setIndicator("Перемещение по x,y,z")
            tabHost.addTab(tabSpec)

            tabSpec = tabHost.newTabSpec("tabControlDxDyDz")
            tabSpec.setContent(binding.tabControlDxDyDz!!.id)
            tabSpec.setIndicator("Перемещение по dx,dy,dz")
            tabHost.addTab(tabSpec)

            tabHost.currentTab = 0
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentArrowControls.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ArrowControlsFragment().apply {}
    }
}