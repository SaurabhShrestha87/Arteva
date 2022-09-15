package com.arteva.user.Categories.model

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Parcel
import android.os.Parcelable
import com.arteva.user.R
import com.arteva.user.manager.Utils
import com.google.gson.annotations.SerializedName

class Category() : CommonItem, Parcelable {

    @SerializedName("id")
    var id = 0

    @SerializedName("title")
    lateinit var title: String

    @SerializedName("icon")
    var icon: String? = null

    @SerializedName("count")
    var count = 0

    @SerializedName("price")
    var price = 0

    @SerializedName("color")
    var color: String? = null

    @SerializedName("sub_categories")
    var subCategories: List<Category>? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString()!!
        icon = parcel.readString()
        count = parcel.readInt()
        price = parcel.readInt()
        color = parcel.readString()
        subCategories = parcel.createTypedArrayList(CREATOR)
    }

    override fun title(context: Context): String {
        return title
    }


    override fun desc(context: Context): String {
        return count.toString() + " " + context.getString(R.string.courses)
    }

    override fun img(): String? {
        return icon
    }

    override fun cardBg(): Int? {
        return R.color.white
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(icon)
        parcel.writeInt(count)
        parcel.writeInt(price)
        parcel.writeString(color)
        parcel.writeTypedList(subCategories)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun imgBgDrawable(context: Context): Drawable? {
        val shape = GradientDrawable()
        shape.cornerRadius = Utils.changeDpToPx(context, 20f)
        shape.color = ColorStateList.valueOf(Color.parseColor(color))

        return shape
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}