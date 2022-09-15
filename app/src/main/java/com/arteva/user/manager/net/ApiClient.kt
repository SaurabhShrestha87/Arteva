package com.arteva.user.manager.net

import com.arteva.user.Categories.model.Category
import com.arteva.user.Categories.model.Count
import com.arteva.user.Categories.model.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiClient {
    @GET("categories.php")
    fun getCategories(@QueryMap map: Map<String, String>): Call<Data<Count<Category>>>
}