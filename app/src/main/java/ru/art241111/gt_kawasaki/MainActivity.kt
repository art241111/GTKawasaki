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

        // Reference to the displayed fragment
        // Here my_nav_host_fragment is an ID of the fragment tag in your
        // content_main.xml with android:name="androidx.navigation.fragment.NavHostFragment"
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
    }

    override fun onDestroy() {
        val viewModel = ViewModelProvider(this).get(RobotViewModel::class.java)
        viewModel.robot.disconnect()

        super.onDestroy()
    }
}