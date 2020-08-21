package ru.art241111.graphicaltoolforkawasaki.view.programAndPoint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentProgramAndPointBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ProgramAndPointFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProgramAndPointFragment : Fragment() {
    private lateinit var binding: FragmentProgramAndPointBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_program_and_point, container, false)
        binding.executePendingBindings()

        setTabHost()
        return binding.root
    }

    private fun setTabHost() {
        val tabHost = binding.tabHostFromProgram

        tabHost.setup()

        var tabSpec =  tabHost.newTabSpec("tabControlXYZ")

        tabSpec.setContent(binding.tabProgramming.id)
        tabSpec.setIndicator("Программирование")
        tabHost.addTab(tabSpec)

        tabSpec = tabHost.newTabSpec("tabControlDxDyDz")
        tabSpec.setContent(binding.tabCreatePoints.id)
        tabSpec.setIndicator("Задание точек")
        tabHost.addTab(tabSpec)

        tabHost.currentTab = 0
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ProgrammAndPointFragment.
         */
        @JvmStatic
        fun newInstance() = ProgramAndPointFragment().apply {}
    }
}