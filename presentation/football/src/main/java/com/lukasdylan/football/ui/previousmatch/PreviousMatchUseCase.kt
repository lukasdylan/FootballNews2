package com.lukasdylan.football.ui.previousmatch

import com.lukasdylan.core.utility.ErrorWrapper
import com.lukasdylan.footballservice.data.database.DetailMatchDao
import com.lukasdylan.footballservice.data.entity.DetailMatch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

interface PreviousMatchUseCase {
    suspend fun checkIsFavoriteMatch(matchId: String): DetailMatch?
    suspend fun saveAsFavoriteMatch(
        detailMatch: DetailMatch,
        onSuccess: () -> Unit,
        onFailed: (ErrorWrapper) -> Unit
    )

    suspend fun deleteFavoriteMatch(
        detailMatch: DetailMatch,
        onSuccess: () -> Unit,
        onFailed: (ErrorWrapper) -> Unit
    )
}

class PreviousMatchUseCaseImpl(private val detailMatchDao: DetailMatchDao) : PreviousMatchUseCase {

    override suspend fun deleteFavoriteMatch(
        detailMatch: DetailMatch,
        onSuccess: () -> Unit,
        onFailed: (ErrorWrapper) -> Unit
    ) {
        try {
            detailMatchDao.deleteFavoriteMatch(detailMatch)
            onSuccess()
        } catch (ex: Exception) {
            AnkoLogger(this::class.java).error { ex.localizedMessage }
            onFailed(ErrorWrapper.invoke(ex))
        }
    }

    override suspend fun saveAsFavoriteMatch(
        detailMatch: DetailMatch,
        onSuccess: () -> Unit,
        onFailed: (ErrorWrapper) -> Unit
    ) {
        try {
            detailMatchDao.saveFavoriteMatch(detailMatch)
            onSuccess()
        } catch (ex: Exception) {
            AnkoLogger(this::class.java).error { ex.localizedMessage }
            onFailed(ErrorWrapper.invoke(ex))
        }
    }

    override suspend fun checkIsFavoriteMatch(matchId: String): DetailMatch? {
        return detailMatchDao.checkIsFavoriteMatch(matchId)
    }

}