package com.lukasdylan.football.module

import com.lukasdylan.football.ui.matchlist.MatchListViewModel
import com.lukasdylan.football.ui.previousmatch.PreviousMatchUseCase
import com.lukasdylan.football.ui.previousmatch.PreviousMatchUseCaseImpl
import com.lukasdylan.football.ui.previousmatch.PreviousMatchViewModel
import com.lukasdylan.football.ui.standings.StandingsViewModel
import com.lukasdylan.football.ui.team.DetailTeamUseCase
import com.lukasdylan.football.ui.team.DetailTeamUseCaseImpl
import com.lukasdylan.football.ui.team.DetailTeamViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

private val useCaseModule = module {
    single<PreviousMatchUseCase> { PreviousMatchUseCaseImpl(get()) }
    single<DetailTeamUseCase> { DetailTeamUseCaseImpl(get()) }
}

private val viewModelModule = module {
    viewModel { StandingsViewModel(get()) }
    viewModel { PreviousMatchViewModel(get(), get()) }
    viewModel { MatchListViewModel(get()) }
    viewModel { DetailTeamViewModel(get(), get()) }
}

val footballModules = listOf(useCaseModule, viewModelModule)