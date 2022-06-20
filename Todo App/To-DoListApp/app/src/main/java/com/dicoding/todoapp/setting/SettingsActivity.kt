package com.dicoding.todoapp.setting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.todoapp.R
import androidx.work.Data.Builder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.dicoding.todoapp.notification.NotificationWorker
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.NOTIFICATION_CHANNEL_ID
import com.dicoding.todoapp.utils.SWITCH_VALUE
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
                val channelName = getString(R.string.notify_channel_name)
                Log.e("CHECK", "$preference $newValue")
                val workManager = WorkManager.getInstance(requireContext())
                //TODO 13 : Schedule and cancel daily reminder using WorkManager with data channelName
                val data = Builder()
                    .putString(NOTIFICATION_CHANNEL_ID, channelName)
                    .putBoolean(SWITCH_VALUE, newValue as Boolean)
                    .build()
                val periodicWorkRequest = PeriodicWorkRequest
                    .Builder(NotificationWorker::class.java, 1, TimeUnit.DAYS)
                    .setInputData(data)
                    .build()
                workManager.enqueue(periodicWorkRequest)
                true
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}