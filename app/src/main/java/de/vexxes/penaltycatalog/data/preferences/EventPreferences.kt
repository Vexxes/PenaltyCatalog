package de.vexxes.penaltycatalog.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


val Context.eventPreferences: DataStore<Preferences> by preferencesDataStore(name = "EventPreferences")

class EventPreferences(context: Context) {
    private val _showPastEvents = booleanPreferencesKey("show_past_events")

    val showPastEvents = context.eventPreferences.data.map { preferences ->
        preferences[_showPastEvents] ?: false
    }

    suspend fun saveShowPastEvents(showPastEvents: Boolean, context: Context) {
        context.eventPreferences.edit { preferences ->
            preferences[_showPastEvents] = showPastEvents
        }
    }
}