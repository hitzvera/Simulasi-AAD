package com.dicoding.habitapp.setting

import android.content.SharedPreferences
import android.media.MediaCodec.MetricsConstants.MODE
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.habitapp.R

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

            //TODO 11 : Update theme based on value in ListPreference
            val preferenceTheme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            preferenceTheme?.setOnPreferenceChangeListener { preference, newValue ->
                Log.e("CHECK", newValue.toString())
                val value = when(newValue){
                    "follow_system" -> MODE_NIGHT_FOLLOW_SYSTEM
                    "on" -> MODE_NIGHT_YES
                    "off" -> MODE_NIGHT_NO
                    else -> MODE_NIGHT_NO
                }
                updateTheme(value)
                true
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}