package com.pandacorp.lechat.utils

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.drawerlayout.widget.DrawerLayout

class MotionDrawerLayout : MotionLayout, DrawerLayout.DrawerListener {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        private const val PROGRESS = "PROGRESS"
    }

    override fun onDrawerStateChanged(newState: Int) {}

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        progress = slideOffset
    }

    override fun onDrawerClosed(drawerView: View) {}

    override fun onDrawerOpened(drawerView: View) {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (parent as? DrawerLayout)?.addDrawerListener(this)
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()
        val bundle = Bundle().apply {
            putFloat(PROGRESS, progress)
        }
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as Bundle
        onDrawerSlide(View(context), bundle.getFloat(PROGRESS))
        super.onRestoreInstanceState(bundle.getParcelableExtraSupport("superState", Parcelable::class.java))
    }
}