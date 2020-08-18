package ru.art241111.graphicaltoolforkawasaki.view

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
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
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.R
import ru.art241111.graphicaltoolforkawasaki.databinding.FragmentDataForLinkBinding
import ru.art241111.graphicaltoolforkawasaki.repository.RepositoryForRobotApi
import ru.art241111.graphicaltoolforkawasaki.utils.Delay
import ru.art241111.graphicaltoolforkawasaki.viewModel.RobotViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [DataForLinkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataForLinkFragment : Fragment() {
    private lateinit var binding: FragmentDataForLinkBinding
    private lateinit var viewModel: RobotViewModel

    // Shared preferences
    private var preferences: SharedPreferences? = null

    private var ip = "192.168.31.52"
    private val APP_PREFERENCES_IP = "IP"

    private var port = "49152"
    private val APP_PREFERENCES_PORT = "PORT"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_data_for_link, container, false)
        binding.executePendingBindings()

        updateDate()
        setButtonListener()

        return binding.root
    }

    private fun updateDate() {
        getIpAndPortFromSharedPreferences()
        binding.defaultIp = ip
        binding.defaultPort = port
    }

    private fun getIpAndPortFromSharedPreferences() {
        preferences = this.activity
                ?.getSharedPreferences("CharacteristicsOfTheConnection", Context.MODE_PRIVATE)

        if(preferences != null){
            ip = getFromSharedPreferences(APP_PREFERENCES_IP, ip)
            port = getFromSharedPreferences(APP_PREFERENCES_PORT, port)
        }
    }

    private fun getFromSharedPreferences(key:String,
                                         defaultValue: String): String{
        return if(preferences!!.contains(key)) {
            preferences!!.getString(key, "") ?: defaultValue

        } else {
            updateSharedPreferences(key,defaultValue)

            defaultValue
        }
    }

    private fun updateSharedPreferences(preferencesKey: String, newValue: String) {
        if(preferences != null){
            val editor: Editor = preferences!!.edit()
            editor.putString(preferencesKey, newValue)
            editor.apply()
        }
    }

    private fun setButtonListener() {
        binding.bConnect.setOnClickListener {
            updateIpAndPort()
            clearFocus()

            if (createConnection()){
                // TODO: Check
                findNavController().navigate(R.id.mainScreenFragment)
            } else{
                Toast.makeText(activity as MainActivity, "Подключение не удалось", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun clearFocus() {
        binding.etIp.clearFocus()
        binding.etPort.clearFocus()

        this.requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun updateIpAndPort(){
        ip = updateValue(ip, binding.etIp.text.toString(), APP_PREFERENCES_IP)
        port = updateValue(port, binding.etPort.text.toString(), APP_PREFERENCES_PORT)
    }

        private fun updateValue(oldValue:String,newValue: String, Key: String): String{
            return if(oldValue != newValue){
                updateSharedPreferences(Key, newValue)
                newValue
            } else{
                oldValue
            }
        }

    private fun createConnection(): Boolean{
        val repositoryForRobotApi = RepositoryForRobotApi()
        repositoryForRobotApi.connectToRobotTCP(address = ip, port = port.toInt())

        Delay.customDelay(500L)

        return if(repositoryForRobotApi.isConnect()){
            viewModel.robot = repositoryForRobotApi
            true
        } else{
            repositoryForRobotApi.disconnect()
            false
        }
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