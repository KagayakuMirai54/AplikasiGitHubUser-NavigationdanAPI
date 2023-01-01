package com.example.aplikasigithubusernavigationdanapi.retrofit

import com.example.aplikasigithubusernavigationdanapi.data.DetailResponse
import com.example.aplikasigithubusernavigationdanapi.data.Followers
import com.example.aplikasigithubusernavigationdanapi.data.Following
import com.example.aplikasigithubusernavigationdanapi.data.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<Following>>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<Followers>>
}