package com.dicoding.courseschedule.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferences2: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)
        sharedPreferences2 = requireContext().getSharedPreferences(NOTIFICATION_PREFERENCE, Context.MODE_PRIVATE)

        //TODO 10 : Update theme based on value in ListPreference

        val preferenceTheme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        var value = 0
        preferenceTheme?.setOnPreferenceChangeListener { preference, newValue ->
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            Log.e("CHECK", newValue.toString())
            value = when(preferenceTheme.value){
                "auto" -> 0
                "on" -> 1
                "off" -> 2
                else -> 0
            }
            editor.putInt(DARK_THEME, value)
            editor.apply()
            updateTheme(value)
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val preferenceNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        preferenceNotification?.setOnPreferenceChangeListener { preference, newValue ->
            val editor: SharedPreferences.Editor = sharedPreferences2.edit()
            val alarmRecevier = DailyReminder()
            if(newValue as Boolean){
                alarmRecevier.setDailyReminder(requireContext())
                editor.putBoolean(IS_NOTIFIED, newValue)
                editor.apply()
            } else {
                alarmRecevier.cancelAlarm(requireContext())
                sharedPreferences2.edit().apply {
                    clear()
                    apply()
                }
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}