package com.lukasdylan.news.module

import com.lukasdylan.news.ui.listnews.ListNewsUseCase
import com.lukasdylan.news.ui.listnews.ListNewsUseCaseImpl
import com.lukasdylan.news.ui.listnews.ListNewsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

private val useCaseModule = module {
    single<ListNewsUseCase> { ListNewsUseCaseImpl(get()) }
}

private val viewModelModule = module {
    viewModel { ListNewsViewModel(get(), get()) }
}

val newsModules = listOf(useCaseModule, viewModelModule)