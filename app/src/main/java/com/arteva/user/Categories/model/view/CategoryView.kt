package com.arteva.user.model.view

import com.arteva.user.Categories.model.Category
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup


class CategoryView(title: String, items: List<Category>) : ExpandableGroup<Category>(title, items) {

    var subCats = items

    var id = 0

    var catTitle: String = ""

    var icon: String? = null

    var count = 0

    var color: String? = null

    var expanded = false
}