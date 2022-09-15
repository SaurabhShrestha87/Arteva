package com.arteva.user.Categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arteva.user.Categories.adapter.CategoriesRvAdapter
import com.arteva.user.Categories.model.Category
import com.arteva.user.Categories.model.Count
import com.arteva.user.Categories.model.Data
import com.arteva.user.databinding.FragCategoriesBinding
import com.arteva.user.helper.Session
import com.arteva.user.manager.net.observer.NetworkObserverFragment
import com.arteva.user.model.view.CategoryView
import com.arteva.user.presenter.Presenter

class CategoriesFrag : NetworkObserverFragment(){

    private val TAG: String = "CATEGORIES"
    private lateinit var mBinding: FragCategoriesBinding
    private lateinit var mPresneter: Presenter.CategoriesPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragCategoriesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mPresneter = CategoriesPresenterImpl(this)
        val map: HashMap<String, String> = HashMap()
        map["class"] = Session.getUserData(Session.CLASS_, context)
        map["subject"] = Session.getUserData(Session.SUBJECT, context)
        Log.e(TAG, "init: "+Session.getUserData(Session.CLASS_, context) + " SUBJECTS : " + Session.getUserData(Session.SUBJECT, context))
        mPresneter.getCategories(map)
    }

    fun onCategoriesRecevied(data: Data<Count<Category>>) {
        val items = data.data!!.items
        var total = 0
        mBinding.categoriesProgressBar.visibility = View.GONE
        val groups = ArrayList<CategoryView>()
        for (item in items) {
            item.subCategories?.let {
                val categoryView =
                    CategoryView(item.title, it)
                categoryView.catTitle = item.title
                categoryView.count = item.count
                categoryView.icon = item.icon
                categoryView.id = item.id
                groups.add(categoryView)
            }
            total += item.price
        }
        mBinding.categoriesExpandableRv.adapter = CategoriesRvAdapter(groups, activity as MainActivityCat)
        mBinding.categoriesTv.text = "Class : ${data.msg.toString()}"
        mBinding.payBtn.isEnabled = true
        mBinding.payBtn.text = "Pay ₹$total"
        mBinding.payBtn.setOnClickListener { (activity as MainActivityCat).startPayment(total) }
    }
}