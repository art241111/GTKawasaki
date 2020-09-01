package ru.art241111.gt_kawasaki.utils.sharedPreferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import ru.art241111.gt_kawasaki.utils.sharedPreferences.protocols.LoadFromSharedPreferences
import ru.art241111.gt_kawasaki.utils.sharedPreferences.protocols.SaveSharedPreferences
import ru.art241111.gt_kawasaki.utils.sharedPreferences.protocols.UpdateSharedPreferences

class SharedPreferencesHelperForString( activity: Activity, sharedPreferences: String)
    :SaveSharedPreferences, LoadFromSharedPreferences, UpdateSharedPreferences {

    private val preferences: SharedPreferences
            = activity.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)

    fun load(preferencesKey: String): String {
        if(preferences.contains(preferencesKey)) {
            return preferences.getString(preferencesKey, "").toString()
        }
        return ""
    }

    fun save(preferencesKey: String, newValue: String) {
        Log.d("debugCommands", newValue)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(preferencesKey, newValue)
        editor.apply()
    }

    fun update(oldValue:String,newValue: String, preferencesKey: String): String{
        return if(oldValue != newValue){
            save(preferencesKey,newValue)
            newValue
        } else{
            oldValue
        }
    }
}