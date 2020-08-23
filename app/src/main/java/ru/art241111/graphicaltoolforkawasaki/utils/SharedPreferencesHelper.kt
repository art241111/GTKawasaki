package ru.art241111.graphicaltoolforkawasaki.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray

class SharedPreferencesHelper(private val activity: Activity,
                              private val sharedPreferences: String,
                              private val sharedPreferencesName: String) {

    var preferences: SharedPreferences
            = activity.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)

    fun save(data: JSONArray){
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(sharedPreferencesName, data.toString())
        editor.apply()
    }

    fun load(): JSONArray =
        JSONArray(preferences.getString(sharedPreferencesName, ""))
}