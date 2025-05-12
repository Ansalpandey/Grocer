package com.app.grocer.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object UserPreferences {
  val USER_ID = stringPreferencesKey("user_id")
  val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
  val ROLE = stringPreferencesKey("role")
  val USER_NAME = stringPreferencesKey("user_name")
  val USER_EMAIL = stringPreferencesKey("user_email")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
