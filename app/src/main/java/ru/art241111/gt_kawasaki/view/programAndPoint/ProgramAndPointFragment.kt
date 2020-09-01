package ru.art241111.gt_kawasaki.view.programAndPoint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.databinding.FragmentProgramAndPointBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ProgramAndPointFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProgramAndPointFragment : Fragment() {
    private lateinit var binding: FragmentProgramAndPointBinding

    private var tabHostPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        tabHostPosition = savedInstanceState?.getInt("position") ?: 0

        super.onCreate(savedInstanceState)
    }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()

        bundle.putInt("position", tabHostPosition)

        outState.putInt("position", tabHostPosition)
    }

    private fun setTabHost() {
        val tabHost = binding.tabHostFromProgram

        tabHost.setup()

        var tabSpec =  tabHost.newTabSpec("tabProgramming")
        tabSpec.setContent(binding.tabProgramming.id)
        tabSpec.setIndicator(resources.getText(R.string.programming_tab))
        tabHost.addTab(tabSpec)

        tabSpec = tabHost.newTabSpec("tabCreatePoints")
        tabSpec.setContent(binding.tabCreatePoints.id)
        tabSpec.setIndicator(resources.getText(R.string.create_points_tab))
        tabHost.addTab(tabSpec)

        tabHost.currentTab = tabHostPosition

        // Save tabHost position
        tabHost.setOnTabChangedListener {
            when(it){
                "tabProgramming" -> tabHostPosition = 0
                "tabCreatePoints" -> tabHostPosition = 1
            }
        }
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