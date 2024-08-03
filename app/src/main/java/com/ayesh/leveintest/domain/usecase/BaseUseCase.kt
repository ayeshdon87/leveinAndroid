package com.ayesh.leveintest.domain.usecase

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}
