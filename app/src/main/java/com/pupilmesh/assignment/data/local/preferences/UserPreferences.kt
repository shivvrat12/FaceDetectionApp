package com.pupilmesh.assignment.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.userDataStore by preferencesDataStore(name = "user_prefs")