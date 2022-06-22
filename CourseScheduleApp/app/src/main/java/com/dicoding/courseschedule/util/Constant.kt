package com.dicoding.courseschedule.util

import android.content.SharedPreferences
import java.util.concurrent.Executors

const val NOTIFICATION_CHANNEL_NAME = "Course Channel"
const val NOTIFICATION_CHANNEL_ID = "notify-schedule"
const val NOTIFICATION_PREFERENCE = "notification_preference"
const val IS_NOTIFIED = "is_notified"
const val NOTIFICATION_ID = 32
const val ID_REPEATING = 101
const val SHARED_PREFERENCE = "shared_preference"
const val DARK_THEME = "dark_theme"

private val SINGLE_EXECUTOR = Executors.newSingleThreadExecutor()

fun executeThread(f: () -> Unit) {
    SINGLE_EXECUTOR.execute(f)
}
