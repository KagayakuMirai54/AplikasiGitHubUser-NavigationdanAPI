package com.example.aplikasigithubusernavigationdanapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.aplikasigithubusernavigationdanapi.data.SearchResponse
import com.example.aplikasigithubusernavigationdanapi.local.DbModule
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun saveData(user: SearchResponse.ItemsItem) {
        DbModule.getInstance().userDao().insert(user)
    }

    fun deleteData(user: SearchResponse.ItemsItem) {
        DbModule.getInstance().userDao().delete(user)
    }

    fun isDataExist(id: Int): Boolean {
        val data = DbModule.getInstance().userDao().findById(id)
        return data != null
    }
}