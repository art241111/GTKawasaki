package ru.art241111.gt_kawasaki.view.addElements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.art241111.gt_kawasaki.MainActivity
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.databinding.FragmentAddForCommandBinding
import ru.art241111.gt_kawasaki.repository.enities.For
import ru.art241111.gt_kawasaki.repository.enities.ForEnd
import ru.art241111.gt_kawasaki.utils.hideKeyboard
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddForCommandFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddForCommandFragment : Fragment() {
    private lateinit var binding: FragmentAddForCommandBinding
    private lateinit var viewModel: RobotViewModel

    // Edit flag. If equal to -1, then new created
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            val key: String? = "position"
            position = it.getInt(key)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Create viewModel
        viewModel = ViewModelProvider(activity as MainActivity).get(RobotViewModel::class.java)

        // Connect to data binding
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_for_command, container, false)
        binding.executePendingBindings()

        setClickListenerOnCancelButton()
        loadInformation()
        setAddCommandButtonListener()

        return binding.root
    }

    private fun setClickListenerOnCancelButton() {
        binding.bCancel.setOnClickListener {
            popStack()
        }
    }

    private fun setAddCommandButtonListener() {
        binding.bAddForAction.setOnClickListener {
            val variableName = binding.etVariableName.text.toString()
            val from = binding.etFrom.text.toString().toIntWithDefault()
            val before = binding.etBefore.text.toString().toIntWithDefault()


            if (variableName == ""){
                Toast.makeText(activity, "Введите название переменной", Toast.LENGTH_LONG).show()
            } else{
                addOrChangeValue(For(variableName,from,before))
                popStack()
            }
        }
    }

    private fun addOrChangeValue(forCommand: For){
        if(position == -1){
            viewModel.programList.value?.add(forCommand)
            viewModel.programList.value?.add(ForEnd(forCommand.variable))
        } else{
            viewModel.programList.value?.set(position, forCommand)
        }
    }

    private fun String.toIntWithDefault(defaultValue: Int = -1): Int =
         toIntOrNull() ?: defaultValue




    private fun popStack(){
        hideKeyboard()
        findNavController().popBackStack()
    }

    /**
     * Если при открытии фрагмента передана позиция команды, то
     * нужно загрузить данные и изменить текстовые поля
     */
    private fun loadInformation() {
        if(position != -1){
            val command = viewModel.programList.value?.get(position) as For

            binding.tvFor.text = getString(R.string.change_for_command)
            binding.etVariableName.text?.append(command.variable)
            binding.etFrom.text?.append(command.from.toString())
            binding.etBefore.text?.append(command.before.toString())
            binding.bAddForAction.text = resources.getText(R.string.change_command)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AddForCommandFragment.
         */
        @JvmStatic
        fun newInstance() = AddForCommandFragment().apply {}
    }
}