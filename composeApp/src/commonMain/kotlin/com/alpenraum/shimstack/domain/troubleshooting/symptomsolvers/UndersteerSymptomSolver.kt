package com.alpenraum.shimstack.domain.troubleshooting.symptomsolvers

import com.alpenraum.shimstack.domain.model.tire.Tire
import com.alpenraum.shimstack.domain.troubleshooting.tire.CalculateTirePressureOffsetForSymptomUseCase
import org.koin.core.annotation.Single

@Single
class UndersteerSymptomSolver(
    val calculateTirePressureOffsetForSymptomUseCase: CalculateTirePressureOffsetForSymptomUseCase
) : SymptomSolver<Tire, Double> {
    override fun solve(input: Tire): Double = calculateTirePressureOffsetForSymptomUseCase(input)
}