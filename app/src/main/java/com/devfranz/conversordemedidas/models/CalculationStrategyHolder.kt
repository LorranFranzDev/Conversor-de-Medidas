package com.devfranz.conversordemedidas.models

import com.devfranz.conversordemedidas.models.strategies.CalculationStrategy

data class CalculationStrategyHolder (
    val name: String,
    val calculationStrategy: CalculationStrategy
)


