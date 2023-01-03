package com.devfranz.conversordemedidas

import android.app.Activity
import android.os.Bundle
import com.devfranz.conversordemedidas.databinding.ActivityResultBinding

class ResultActivity : Activity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getDoubleExtra("RESULT", 0.0)
        val label = intent.getStringExtra("LABEL")

        binding.tvValue.text = result.toString()

        binding.tvValueLabel.text = label.toString()

        binding.btnClose.setOnClickListener{
            finish()
        }

    }
}