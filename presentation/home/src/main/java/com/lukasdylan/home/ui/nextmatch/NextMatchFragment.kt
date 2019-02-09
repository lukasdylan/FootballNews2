package com.lukasdylan.home.ui.nextmatch

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lukasdylan.core.extension.checkPermissions
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.core.extension.observeValue
import com.lukasdylan.core.widget.RoundedBottomSheetFragment
import com.lukasdylan.home.R
import com.lukasdylan.home.databinding.FragmentNextMatchBinding
import kotlinx.coroutines.delay
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel

class NextMatchFragment : RoundedBottomSheetFragment() {

    companion object {
        private const val CALENDAR_ACCESS_PERMISSION_CODE = 101
        private const val CALENDAR_REQUEST_CODE = 201
    }

    private lateinit var binding: FragmentNextMatchBinding

    private val viewModel: NextMatchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_next_match, container, false)
        with(binding) {
            lifecycleOwner = this@NextMatchFragment
            ivClose.onClick {
                delay(250)
                dismiss()
            }
            switchReminder.onClick {
                viewModel.onReminderSwitchClick()
            }

            vm = viewModel.also { it ->
                observeValue(it.homeTeamImageUrl) { ivHomeTeamIcon.loadImageByUrl(it) }
                observeValue(it.awayTeamImageUrl) { ivAwayTeamIcon.loadImageByUrl(it) }
                observeValue(it.checkReminderStatusEvent) { title ->
                    checkPermissions(
                        arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR),
                        CALENDAR_ACCESS_PERMISSION_CODE
                    ) {
                        onCheckCalendar(title)
                    }
                }
                observeValue(it.openCalendarEvent) {
                    startActivityForResult(it, CALENDAR_REQUEST_CODE)
                }
                observeValue(it.removeReminderEvent) { title ->
                    checkPermissions(
                        arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR),
                        CALENDAR_ACCESS_PERMISSION_CODE
                    ) {
                        onDeleteReminder(title)
                    }
                }
            }
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { viewModel.loadData(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CALENDAR_REQUEST_CODE) {
            viewModel.checkReminderMatch()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALENDAR_ACCESS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.any { it == PackageManager.PERMISSION_GRANTED }) {
                viewModel.checkReminderMatch()
            } else {
                val showRationale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR)
                } else {
                    false
                }
                
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun onCheckCalendar(title: String) {
        val projection = arrayOf(CalendarContract.Events.TITLE)
        val cursor = context?.contentResolver?.query(
            CalendarContract.Events.CONTENT_URI,
            projection, CalendarContract.Events.TITLE + "= ?", arrayOf(title), null
        )
        viewModel.markAsReminderMatch(cursor?.count ?: 0 >= 1)
        cursor?.close()
    }

    @SuppressLint("MissingPermission")
    private fun onDeleteReminder(title: String) {
        context?.contentResolver?.delete(
            CalendarContract.Events.CONTENT_URI, CalendarContract.Events.TITLE + "= ?", arrayOf(title)
        )
        viewModel.markAsReminderMatch(false)
    }
}