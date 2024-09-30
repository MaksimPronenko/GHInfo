package pronenko.ghinfo.domain

import android.content.SharedPreferences
import pronenko.ghinfo.ui.common.LAST_SEARCH

class PreferencesManager(private val sharedPreferences: SharedPreferences) {
    var lastSearchQuery: String?
        get() = sharedPreferences.getString(LAST_SEARCH, null)
        set(value) {
            sharedPreferences.edit().putString(LAST_SEARCH, value).apply()
        }
}
