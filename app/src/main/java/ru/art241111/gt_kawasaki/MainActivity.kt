package ru.art241111.gt_kawasaki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import ru.art241111.gt_kawasaki.viewModel.RobotViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
    }

    /**
     * Disconnect from robot, when application destroy
     */
    override fun onDestroy() {
        val viewModel = ViewModelProvider(this).get(RobotViewModel::class.java)
        viewModel.disconnect()

        super.onDestroy()
    }
}