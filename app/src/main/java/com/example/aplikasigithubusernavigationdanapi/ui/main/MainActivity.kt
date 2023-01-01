package com.example.aplikasigithubusernavigationdanapi.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigithubusernavigationdanapi.MainViewModel
import com.example.aplikasigithubusernavigationdanapi.R
import com.example.aplikasigithubusernavigationdanapi.SettingPreferences
import com.example.aplikasigithubusernavigationdanapi.ViewModelFactory
import com.example.aplikasigithubusernavigationdanapi.adapter.SearchAdapter
import com.example.aplikasigithubusernavigationdanapi.data.SearchResponse
import com.example.aplikasigithubusernavigationdanapi.databinding.ActivityMainBinding
import com.example.aplikasigithubusernavigationdanapi.local.DbModule
import com.example.aplikasigithubusernavigationdanapi.retrofit.ApiConfig
import com.example.aplikasigithubusernavigationdanapi.ui.DetailUserActivity
import com.example.aplikasigithubusernavigationdanapi.ui.UserFavoriteActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DbModule.getInstance(applicationContext)

        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Github User's Search"
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]
        mainViewModel.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val btnFavorite = menu.findItem(R.id.favorite)
        btnFavorite.setOnMenuItemClickListener {
            val mIntent = Intent(this@MainActivity, UserFavoriteActivity::class.java)
            startActivity(mIntent)
            true
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchUser(query)
                val text: TextView = findViewById(R.id.main_text)
                text.visibility = View.INVISIBLE
                val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
                switchTheme.visibility = View.INVISIBLE
                searchView.clearFocus()
                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun searchUser(name: String) {
        binding.progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().searchUser(name)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.INVISIBLE
                    val responseBody = response.body()
                    if (responseBody != null) {
                        recyclerView.adapter = SearchAdapter(responseBody.items) {
                            val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                            intent.putExtra("item", it)
                            startActivity(intent)
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "MainActivity"
    }
}