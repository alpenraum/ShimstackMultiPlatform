package com.alpenraum.shimstack.domain.troubleshooting.symptomsolvers

interface SymptomSolver<I, O> {
    fun solve(input: I): O
}