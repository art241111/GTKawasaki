package ru.art241111.gt_kawasaki.utils.sharedPreferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import ru.art241111.gt_kawasaki.utils.sharedPreferences.protocols.LoadFromSharedPreferences
import ru.art241111.gt_kawasaki.utils.sharedPreferences.protocols.SaveSharedPreferences

class SharedPreferencesHelperForJSON(activity: Activity, sharedPreferences: String)
    : SaveSharedPreferences, LoadFromSharedPreferences {

    private val preferences: SharedPreferences
            = activity.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)

    fun save(preferencesKey: String,data: JSONArray){
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(preferencesKey, data.toString())
        editor.apply()
    }

    fun load(preferencesKey: String): JSONArray =
        JSONArray(preferences.getString(preferencesKey, ""))
}