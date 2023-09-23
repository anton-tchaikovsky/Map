package com.gb.map.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gb.map.R
import com.gb.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
            supportFragmentManager.apply {
                beginTransaction()
                    .add(R.id.container, MapFragment())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
    }
}