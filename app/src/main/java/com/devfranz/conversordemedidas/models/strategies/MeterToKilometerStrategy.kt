package com.devfranz.conversordemedidas.models.strategies

class MeterToKilometerStrategy : CalculationStrategy {
    override fun calculate(value: Double): Double {
        return value / 1_000
    }

    override fun getResultLabel(isPlural: Boolean): String =
        if (isPlural) "quilômetros" else "quilômetro"
}