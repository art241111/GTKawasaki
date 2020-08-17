package ru.art241111.graphicaltoolforkawasaki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set toolbar
        //setSupportActionBar(toolbar)

        // Reference to the displayed fragment
        // Here my_nav_host_fragment is an ID of the fragment tag in your
        // content_main.xml with android:name="androidx.navigation.fragment.NavHostFragment"
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

    }
}