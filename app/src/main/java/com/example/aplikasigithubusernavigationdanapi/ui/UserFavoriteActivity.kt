package com.example.aplikasigithubusernavigationdanapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigithubusernavigationdanapi.R
import com.example.aplikasigithubusernavigationdanapi.adapter.SearchAdapter
import com.example.aplikasigithubusernavigationdanapi.local.DbModule

class UserFavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_favorite)

        val recyclerView: RecyclerView = findViewById(R.id.rv_user_favorite)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val liveData = DbModule.getInstance().userDao().loadall()
        liveData.observe(this) { itemList ->
            recyclerView.adapter = SearchAdapter(itemList) {
                val intent = Intent(this@UserFavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra("item", it)
                startActivity(intent)
            }
        }
    }
}