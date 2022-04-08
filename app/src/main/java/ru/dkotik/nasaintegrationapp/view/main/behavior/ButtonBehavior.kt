package ru.dkotik.nasaintegrationapp.view.main.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs

const val BAR_HEIGHT_PROPORTION = 2/3
const val HEIGHT_DENOMINATOR = 2

class ButtonBehavior(context: Context, attr: AttributeSet?=null): CoordinatorLayout.Behavior<View>(context,attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ) = dependency is AppBarLayout


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        val bar = dependency as AppBarLayout
        val barHeight = bar.height.toFloat()
        val barY = bar.y

        if (abs(barY) > (barHeight * BAR_HEIGHT_PROPORTION)) {
            child.visibility = View.GONE
        } else {
            child.visibility = View.VISIBLE
            child.alpha = ((barHeight * BAR_HEIGHT_PROPORTION) - abs(barY / HEIGHT_DENOMINATOR)) / (barHeight * BAR_HEIGHT_PROPORTION)
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }

}