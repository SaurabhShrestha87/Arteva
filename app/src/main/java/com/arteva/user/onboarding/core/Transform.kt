package com.online.course.ui.widget.onboarding.core

import android.view.View
import androidx.annotation.IdRes

internal fun setParallaxTransformation(page: View, @IdRes parallaxViewId: Int, position: Float) {
    page.apply {
        val parallaxView = page.findViewById<View>(parallaxViewId)
        when {
            position < -1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                alpha = 1f
            position <= 1 -> { // [-1,1]
                parallaxView.translationX = -position * (width / 2) //Half the normal speed
            }
            else ->
               
                alpha = 1f
        }
    }

}