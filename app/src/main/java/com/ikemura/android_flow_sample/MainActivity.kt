package com.ikemura.android_flow_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ikemura.android_flow_sample.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startFlow()
    }

    private fun startFlow() {
        lifecycleScope.launch {
            val myFlow = flow<String> {
                delay(1000)
                emit("hello flow")
            }

            myFlow.collect { binding.textView.text = it }
        }
    }
}
