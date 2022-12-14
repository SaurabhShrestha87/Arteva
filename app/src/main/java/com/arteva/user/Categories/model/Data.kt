package com.arteva.user.Categories.model

import com.google.gson.annotations.SerializedName

class Data<T> : BaseResponse() {
    @SerializedName(
        "data",
        alternate = ["user", "result", "cart", "order", "amounts", "quizzes", "teachers",
            "users", "webinars"]
    )
    var data: T? = null

    @SerializedName("msg")
    var msg: String? = null
}