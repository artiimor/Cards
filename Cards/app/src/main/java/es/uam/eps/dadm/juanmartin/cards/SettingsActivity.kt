package es.uam.eps.dadm.juanmartin.cards

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager


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

        PreferenceManager.setDefaultValues(
            this,
            R.xml.root_preferences,
            false
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    companion object {
        private const val MAXIMUM_KEY = "max_number_cards"
        private const val MAXIMUM_DEFAULT = "20"
        private const val BOARD_KEY = "board"

        fun getBoardOption(context: Context): Boolean{
            return PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(BOARD_KEY, false)
        }

        fun getMaximumNumberOfCards(context: Context): String? {
            return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(MAXIMUM_KEY, MAXIMUM_DEFAULT)
        }

        fun setMaximumNumberOfCards(context: Context, max: String) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(MAXIMUM_KEY, max)
            editor.apply ()
        }
    }
}