package com.arteva.user.Categories

import android.util.Log
import com.arteva.user.Categories.model.Category
import com.arteva.user.Categories.model.Count
import com.arteva.user.Categories.model.Data
import com.arteva.user.manager.net.CustomCallback
import com.arteva.user.manager.net.RetryListener
import com.arteva.user.presenter.Presenter
import retrofit2.Call
import retrofit2.Response

class CategoriesPresenterImpl(private val frag: CategoriesFrag) : Presenter.CategoriesPresenter {


    override fun getCategories(map: HashMap<String, String>) {
        val categories = ApiService.apiClient!!.getCategories(map)
        frag.addNetworkRequest(categories)
        categories.enqueue(object : CustomCallback<Data<Count<Category>>> {
            override fun onStateChanged(): RetryListener {
                return RetryListener {
                    getCategories(map)
                }
            }

            override fun onResponse(
                call: Call<Data<Count<Category>>>,
                response: Response<Data<Count<Category>>>,
            ) {
                Log.e("CATEGORY", "onResponse: $response")
                if (response.body() != null)
                    frag.onCategoriesRecevied(response.body()!!)
            }

        })
    }

}