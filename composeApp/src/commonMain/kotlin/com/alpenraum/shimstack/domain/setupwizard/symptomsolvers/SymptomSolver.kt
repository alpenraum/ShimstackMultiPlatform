package com.alpenraum.shimstack.domain.setupwizard.symptomsolvers

interface SymptomSolver<I, O> {
    fun solve(input: I): O
}