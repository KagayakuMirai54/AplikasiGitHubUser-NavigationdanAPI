package com.example.aplikasigithubusernavigationdanapi.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aplikasigithubusernavigationdanapi.data.SearchResponse

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: SearchResponse.ItemsItem)

    @Query("SELECT * FROM user")
    fun loadall(): LiveData<MutableList<SearchResponse.ItemsItem>>

    @Delete
    fun delete(user: SearchResponse.ItemsItem)

    @Query("SELECT * FROM user WHERE id LIKE :id Limit 1")
    fun findById(id: Int): SearchResponse.ItemsItem?
}