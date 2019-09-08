package com.ancient.ancient_handcraft.app

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.UserSession
import com.google.gson.Gson

class AppData(val context: Context) {

    val gson = Gson()

    val sharedPreferences: SharedPreferences

    val SESSION_KEY = "Session"

    init {
        sharedPreferences = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)
    }

    var userSession: UserSession? = null
        get() {
            val sessionJson = sharedPreferences.getString(SESSION_KEY, "{}")
            return gson.fromJson<UserSession>(sessionJson, UserSession::class.java)
        }
        set(value) {
            sharedPreferences.edit {
                putString(SESSION_KEY, gson.toJson(value))
            }
            field = value
        }
}