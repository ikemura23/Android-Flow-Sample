package com.ikemura.android_flow_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFlow()
    }

    private fun startFlow() {
        lifecycleScope.launch {
            val myFlow = flow<String> {
                delay(1000)
                emit("hello flow")
            }

            myFlow.collect { println(it) }
        }
    }
}
