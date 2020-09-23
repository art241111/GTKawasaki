package ru.art241111.gt_kawasaki.view.linkFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.databinding.FragmentDataForLinkBinding
import ru.art241111.gt_kawasaki.view.util.hideKeyboard
import ru.art241111.gt_kawasaki.utils.sharedPreferences.SharedPreferencesHelperForString
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * Фрагмент, который позволяет созавать новое соединение
 * @author Artem Gerasimov
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
        setButtonListenerOnConnectionButton()
        setClickListenerOnCancelButton()

        return binding.root
    }

    private fun loadIpAndPortFromSharedPreferences() {
        preferences = SharedPreferencesHelperForString(activity as MainActivity,
                                                                      APP_PREFERENCES)
        loadIp()
        loadPort()
    }
        private fun loadIp(){
            ip = preferences.load(APP_PREFERENCES_IP, defaultValue =  ip)
            binding.defaultIp = ip
        }

        private fun loadPort(){
            port = preferences.load(APP_PREFERENCES_PORT, defaultValue = port)
            binding.defaultPort  = port
        }

    private fun setButtonListenerOnConnectionButton() {
        binding.bConnect.setOnClickListener {
            ip = preferences.update(ip, binding.etIp.text.toString(), APP_PREFERENCES_IP)
            port = preferences.update(port, binding.etPort.text.toString(), APP_PREFERENCES_PORT)

            if(viewModel.createConnection(ip, port.toIntOrNull())){
                popBackStack()
            } else{
                Toast.makeText(activity as MainActivity, R.string.connection_is_failed, Toast.LENGTH_LONG)
                     .show()
            }
        }
    }

    private fun setClickListenerOnCancelButton() {
        binding.bCancel.setOnClickListener {
            popBackStack()
        }
    }

    private fun popBackStack(){
        hideKeyboard()
        findNavController().popBackStack()
    }
}