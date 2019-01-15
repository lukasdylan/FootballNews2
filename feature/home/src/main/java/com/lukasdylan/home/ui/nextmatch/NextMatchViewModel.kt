package com.lukasdylan.home.ui.nextmatch

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.core.utility.SingleLiveEvent
import com.lukasdylan.core.utility.StringUtils
import com.lukasdylan.core.utility.StringUtils.calendarFromString
import com.lukasdylan.footballservice.data.entity.DetailMatch
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class NextMatchViewModel(dispatcherProvider: DispatcherProviders) : BaseViewModel(dispatcherProvider) {

    private val _selectedDetailMatch = MutableLiveData<DetailMatch>()
    val homeTeamName: LiveData<String> = Transformations.map(_selectedDetailMatch) {
        it?.homeTeamName.orEmpty()
    }
    val awayTeamName: LiveData<String> = Transformations.map(_selectedDetailMatch) {
        it?.awayTeamName.orEmpty()
    }
    val matchDateTime: LiveData<String> = Transformations.map(_selectedDetailMatch) {
        val calendar = StringUtils.calendarFromString(it?.date.orEmpty(), it?.time.orEmpty())
        return@map StringUtils.formatAsDate(calendar.time)
    }
    val matchName: LiveData<String> = Transformations.map(_selectedDetailMatch) {
        it?.matchName.orEmpty()
    }
    val leagueName: LiveData<String> = Transformations.map(_selectedDetailMatch) {
        it?.leagueName.orEmpty()
    }

    private val _homeTeamImageUrl = MutableLiveData<String>()
    val homeTeamImageUrl: LiveData<String> = Transformations.map(_homeTeamImageUrl) {
        it.orEmpty()
    }

    private val _awayTeamImageUrl = MutableLiveData<String>()
    val awayTeamImageUrl: LiveData<String> = Transformations.map(_awayTeamImageUrl) {
        it.orEmpty()
    }

    private val _switchChecked = MutableLiveData<Boolean>().apply { value = false }
    val switchChecked: LiveData<Boolean> = _switchChecked

    private val _reminderStatusVisibility = MutableLiveData<Int>().apply { value = View.GONE }
    val reminderStatusVisibility: LiveData<Int> = _reminderStatusVisibility

    private val _checkReminderStatusEvent = SingleLiveEvent<String>()
    val checkReminderStatusEvent: LiveData<String> = _checkReminderStatusEvent

    private val _openCalendarEvent = SingleLiveEvent<Intent>()
    val openCalendarEvent: LiveData<Intent> = _openCalendarEvent

    private val _removeReminderEvent = SingleLiveEvent<String>()
    val removeReminderEvent: LiveData<String> = _removeReminderEvent

    fun loadData(bundle: Bundle) {
        with(bundle) {
            _homeTeamImageUrl.value = getString("home_image_url")
            _awayTeamImageUrl.value = getString("away_image_url")
            _selectedDetailMatch.value = getParcelable("detail_match")
            _checkReminderStatusEvent.value = _selectedDetailMatch.value?.matchName
        }
    }

    fun checkReminderMatch() {
        _checkReminderStatusEvent.value = _selectedDetailMatch.value?.matchName
    }

    fun onReminderSwitchClick() {
        val isReminderMatch = _switchChecked.value ?: false
        if (!isReminderMatch) {
            val beginTime = calendarFromString(
                _selectedDetailMatch.value?.date.orEmpty(),
                _selectedDetailMatch.value?.time.orEmpty()
            )
            val endTime = beginTime.clone() as? Calendar
            endTime?.add(Calendar.MINUTE, 90)

            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime?.timeInMillis)
                .putExtra(CalendarContract.Events.CAL_SYNC1, _selectedDetailMatch.value?.idEvent.orEmpty())
                .putExtra(CalendarContract.Events.TITLE, _selectedDetailMatch.value?.matchName.orEmpty())
                .putExtra(CalendarContract.Events.DESCRIPTION, _selectedDetailMatch.value?.matchDescription.orEmpty())
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE)

            _openCalendarEvent.value = intent
        } else {
            _removeReminderEvent.value = _selectedDetailMatch.value?.matchName
        }
    }

    fun markAsReminderMatch(isReminderMatch: Boolean) {
        launch {
            delay(250)
            _switchChecked.value = isReminderMatch
            _reminderStatusVisibility.value = if (isReminderMatch) View.VISIBLE else View.GONE
        }
    }
}