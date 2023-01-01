package com.example.aplikasigithubusernavigationdanapi.local

import android.content.Context
import androidx.room.Room

object DbModule {
    private var INSTANCE: AppDb? = null
    fun getInstance(context: Context? = null): AppDb {
        if (INSTANCE == null && context != null) {
            INSTANCE = Room.databaseBuilder(
                context,
                AppDb::class.java,
                "usergithub.db")
                .allowMainThreadQueries()
                .build()
        }
        return INSTANCE as AppDb
    }
}