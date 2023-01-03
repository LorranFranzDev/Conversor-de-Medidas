package com.devfranz.conversordemedidas

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.devfranz.conversordemedidas.databinding.ActivityMainBinding
import com.devfranz.conversordemedidas.models.CalculationStrategyHolder
import com.devfranz.conversordemedidas.models.Calculator
import com.devfranz.conversordemedidas.models.strategies.KilometerToCentimeters
import com.devfranz.conversordemedidas.models.strategies.KilometersToMeterStrategy
import com.devfranz.conversordemedidas.models.strategies.MeterToKilometerStrategy
import com.devfranz.conversordemedidas.models.strategies.MetersToCentimeters

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val supportedCalculationStrategies = arrayOf(
        CalculationStrategyHolder("Quilômetro para centímetros", KilometerToCentimeters()),
        CalculationStrategyHolder("Quilômetro para metros", KilometersToMeterStrategy()),
        CalculationStrategyHolder("Metros para centímetros", MetersToCentimeters()),
        CalculationStrategyHolder("Metros para quilômetros", MeterToKilometerStrategy())

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            false
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var value = 0.0
        var position = 0

        savedInstanceState?.let {
            value = it.getDouble("VALUE")
            position = it.getInt("POSITION")
        }


        setUi(value, position)
        setActions()

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        try {
            outState.putDouble("VALUE", binding.edtValue.text.toString().toDouble())
        }catch (_: java.lang.NumberFormatException){

        }
        outState.putInt("POSITION", binding.spConversions.selectedItemPosition)
    }




    private fun setActions() {

        binding.btnConvert.setOnClickListener {

            try {
                val value = binding.edtValue.text.toString().toDouble()
                val calculationStrategy = supportedCalculationStrategies[
                        binding.spConversions.selectedItemPosition
                ].calculationStrategy

                Calculator.setCalculationStrategy(
                    calculationStrategy
                )

                val result = Calculator.calculate(value)
                val label = calculationStrategy.getResultLabel(result != 1.toDouble())

                showResult(result, label)

            } catch (e: java.lang.NumberFormatException) {
                binding.edtValue.error = "Valor inválido"
                binding.edtValue.requestFocus()
            }


        }

        binding.btnClean.setOnClickListener {
            clean()
        }

    }

    private fun clean() {
        binding.edtValue.setText("")
        binding.edtValue.error = null

        binding.spConversions.setSelection(0)
    }

    private fun showResult(result: Double, label: String) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("RESULT", result)
        intent.putExtra("LABEL", label)

        startActivity(intent)
    }

    private fun setUi(value : Double, position: Int) {

        binding.edtValue.setText(value.toString())

        val spAdapter = ArrayAdapter(this, R.layout.res_spinner_item, getSpinnerData())
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spConversions.adapter = spAdapter
        binding.spConversions.setSelection(position)

    }

    private fun getSpinnerData(): MutableList<String> {
        val spinnerData = mutableListOf<String>()
        supportedCalculationStrategies.forEach {
            spinnerData.add(it.name)
        }
        return spinnerData
    }

}