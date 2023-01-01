package com.example.aplikasigithubusernavigationdanapi.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aplikasigithubusernavigationdanapi.data.SearchResponse

@Database(entities = [SearchResponse.ItemsItem::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase(){
    abstract fun userDao(): UserDao
}