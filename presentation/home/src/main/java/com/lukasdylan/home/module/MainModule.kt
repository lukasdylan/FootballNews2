package com.lukasdylan.home.module

import com.lukasdylan.home.ui.home.HomeUseCase
import com.lukasdylan.home.ui.home.HomeUseCaseImpl
import com.lukasdylan.home.ui.home.HomeViewModel
import com.lukasdylan.home.ui.nextmatch.NextMatchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val useCaseModule = module {
    single<HomeUseCase> { HomeUseCaseImpl(get(), get(), get()) }
}

private val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { NextMatchViewModel(get()) }
}

val homeModules = listOf(useCaseModule, viewModelModule)