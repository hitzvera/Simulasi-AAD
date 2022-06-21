package com.dicoding.courseschedule.ui.setting

import android.app.AlarmManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val preferenceNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        preferenceNotification?.setOnPreferenceChangeListener { preference, newValue ->
            val alarmRecevier = DailyReminder()
            if(newValue as Boolean){
                alarmRecevier.setDailyReminder(requireContext())
            } else {
                alarmRecevier.cancelAlarm(requireContext())
            }
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}