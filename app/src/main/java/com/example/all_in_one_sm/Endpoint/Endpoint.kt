package com.example.all_in_one_sm.Endpoint

import com.example.all_in_one_sm.ArticlesItem
import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {
    @GET("items")
    fun getItems(): Call<List<ArticlesItem.Items>>
}