package com.tryouts.finalyearproject.common

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tryouts.finalyearproject.data.models.AttackData
import java.lang.reflect.Type

class SharedPreferencesClass ( activity: Activity ) {

    private val sharedPreferences: SharedPreferences by lazy {
        activity.getSharedPreferences(Constants.SHARED_PREF_NAME , Context.MODE_PRIVATE)
    }
    private val editor : SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    fun saveFileData (list : MutableList<AttackData>) {
        val gson = Gson()
        val data = gson.toJson(list)
        editor.putString(Constants.SHARED_PREF_ATTACK_DATA , data)
        editor.commit()
    }

    fun checkFileData () : Boolean{
        return sharedPreferences.contains(Constants.SHARED_PREF_ATTACK_DATA)
    }

    fun getFileData () : ArrayList<AttackData> {
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<AttackData>>() {}.type
        val data = sharedPreferences.getString(Constants.SHARED_PREF_ATTACK_DATA , "")
        val list = gson.fromJson<Any>(data , type) as ArrayList<AttackData>
        return list
    }
}