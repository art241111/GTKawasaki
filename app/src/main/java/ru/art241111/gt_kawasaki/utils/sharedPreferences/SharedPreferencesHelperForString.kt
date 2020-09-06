package ru.art241111.gt_kawasaki.utils.sharedPreferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import ru.art241111.gt_kawasaki.utils.sharedPreferences.protocols.LoadFromSharedPreferences
import ru.art241111.gt_kawasaki.utils.sharedPreferences.protocols.SaveSharedPreferences
import ru.art241111.gt_kawasaki.utils.sharedPreferences.protocols.UpdateSharedPreferences

class SharedPreferencesHelperForString(activity: Activity, sharedPreferences: String)
    :SaveSharedPreferences, LoadFromSharedPreferences, UpdateSharedPreferences {

    private val preferences: SharedPreferences
            = activity.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)

    /**
     * Берется значение из sharedPreferences.
     * Если такого значения нет, то возвращается defaultValue
     *
     * @param preferencesKey - key for sharedPreferences
     * @param defaultValue - дефолтное значение, которое возвратиться, если
     *                       с таким ключом значений нет
     *
     * @return Если существет значение в SharedPreferences, то возвращается оно,
     * иначе возвращается defaultValue
     */
    fun load(preferencesKey: String, defaultValue: String = ""): String =
        if(preferences.contains(preferencesKey)) {
            preferences.getString(preferencesKey, defaultValue).toString()
        } else{
            defaultValue
        }

    fun save(preferencesKey: String, newValue: String) {
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