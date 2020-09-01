package ru.art241111.gt_kawasaki.view.linkFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.databinding.FragmentDataForLinkBinding
import ru.art241111.gt_kawasaki.utils.hideKeyboard
import ru.art241111.gt_kawasaki.utils.sharedPreferences.SharedPreferencesHelperForString
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DataForLinkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val APP_PREFERENCES = "CharacteristicsOfTheConnection"
private const val APP_PREFERENCES_IP = "IP"
private const val APP_PREFERENCES_PORT = "PORT"

class DataForLinkFragment : Fragment() {
    private lateinit var binding: FragmentDataForLinkBinding
    private lateinit var viewModel: RobotViewModel

    // Shared preferences
    private lateinit var preferences: SharedPreferencesHelperForString

    private var ip = "192.168.31.52"
    private var port = "49152"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_data_for_link, container, false)
        binding.executePendingBindings()

        loadIpAndPortFromSharedPreferences()
        setButtonListener()

        return binding.root
    }

    private fun loadIpAndPortFromSharedPreferences() {
        preferences = SharedPreferencesHelperForString(activity as MainActivity,
                                                                      APP_PREFERENCES)
        val ipFromSP = preferences.load(APP_PREFERENCES_IP)
        if(ipFromSP == ""){
            binding.defaultIp = ip
        } else binding.defaultIp = ipFromSP


        val portFromSP = preferences.load(APP_PREFERENCES_PORT)
        if(portFromSP == ""){
            binding.defaultPort  = port
        } else binding.defaultPort  = portFromSP
    }

    private fun setButtonListener() {
        binding.bConnect.setOnClickListener {
            ip = preferences.update(ip, binding.etIp.text.toString(), APP_PREFERENCES_IP)
            port = preferences.update(port, binding.etPort.text.toString(), APP_PREFERENCES_PORT)
            clearFocus()

            if(viewModel.createConnection(ip, port.toInt())){
                hideKeyboard()
                findNavController().popBackStack()
            } else{
                Toast.makeText(activity as MainActivity, R.string.connection_is_failed, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun clearFocus() {
        binding.etIp.clearFocus()
        binding.etPort.clearFocus()

        this.requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DataForLinkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = DataForLinkFragment().apply {}
    }
}