package com.pandacorp.dropspinner

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.pandacorp.lechat.dropspinner.R
import net.cachapa.expandablelayout.ExpandableLayout


class DropDownView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialCardView(context, attrs, defStyleAttr), View.OnClickListener {
    val selectedIndex: Int
        get() = adapter.selectedIndex

    private lateinit var items: List<DropDownItem>
    private lateinit var adapter: RecyclerViewAdapter
    private val expandableLayout: ExpandableLayout
    private val recyclerView: RecyclerView
    private val imageArrow: AppCompatImageView
    private val label: TextView
    private val value: TextView

    init {
        // Add body layout
        val dropDownBody =
            LayoutInflater.from(context).inflate(
                R.layout.dropsy_layout_drop_down,
                this,
                false
            ) as LinearLayout
        addView(dropDownBody)
        expandableLayout = findViewById(R.id.expandableLayout)
        recyclerView = findViewById(R.id.recyclerView)
        imageArrow = findViewById(R.id.img_arrow)
        label = findViewById(R.id.txt_drop_drown_label)
        value = findViewById(R.id.txt_drop_drown_value)

        // Get attrs
        val dropsyAttrs =
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.DropDownView,
                0,
                0
            )
        initData(dropsyAttrs)
        setStyles(dropsyAttrs)
        dropsyAttrs.recycle()

        setOnClickListener(this)
    }

    fun setSelectedItem(position: Int) {
        val item = items[position]
        value.text = item.text
        adapter.setSelection(position, item)
    }

    override fun onClick(v: View?) {
        if (expandableLayout.isExpanded) {
            hideListView()
        } else {
            showListView()
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()
        return Bundle().apply {
            putParcelable("superState", super.onSaveInstanceState())
            putInt("selectedPosition", adapter.selectedIndex)
            putBoolean("isExpanded", expandableLayout.isExpanded)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state == null) {
            super.onRestoreInstanceState(null)
            return
        }
        val viewState = (state as Bundle)
        if (viewState.getBoolean("isExpanded")) {
            showListView(false)
        } else {
            hideListView(false)
        }
        val position = viewState.getInt("selectedPosition")
        val item = items[position]
        value.text = item.text
        adapter.setSelection(position, item)
        super.onRestoreInstanceState(state.getParcelableExtraSupport("superState", Parcelable::class.java))
    }

    private fun showListView(withAnimation: Boolean = true) {
        post {
            animateExpand(withAnimation)
        }
    }

    private fun hideListView(withAnimation: Boolean = true) {
        postDelayed({
            animateCollapse(withAnimation)
        }, if (withAnimation) 150 else 0)
    }

    private fun setStyles(dropsyAttrs: TypedArray) {
        val resources = context.resources
        val dropsyLabelColor =
            dropsyAttrs.getColor(
                R.styleable.DropDownView_dropsyLabelColor,
                ContextCompat.getColor(
                    context,
                    R.color.dropsy_text_color_secondary
                )
            )
        val dropsyValueColor =
            dropsyAttrs.getColor(
                R.styleable.DropDownView_dropsyValueColor,
                Color.BLACK
            )
        val dropsyElevation =
            dropsyAttrs.getDimension(
                R.styleable.DropDownView_dropsyElevation,
                0.0f
            )
        val dropsySelector =
            dropsyAttrs.getColor(
                R.styleable.DropDownView_dropsySelector,
                Color.BLACK
            )

        // Arrow styling
        imageArrow.imageTintList =
            dropsyAttrs.getColorStateList(R.styleable.DropDownView_dropsySelector)

        // Text styling
        label.setTextColor(dropsyLabelColor)
        value.setTextColor(dropsyValueColor)
        adapter.setTextColor(dropsyValueColor)
        adapter.setArrowColor(dropsySelector)

        // Card styling
        val padding =
            resources.getDimension(R.dimen.dropsy_dropdown_padding).toInt()
        setContentPadding(padding, padding, padding, padding)

        elevation = dropsyElevation
    }

    private fun initData(dropsyAttrs: TypedArray) {
        val dropsyLabel =
            dropsyAttrs.getString(R.styleable.DropDownView_dropsyLabel)
        items = dropsyAttrs.getTextArray(R.styleable.DropDownView_dropsyItems)
            ?.mapIndexed { index, value ->
                DropDownItem(index, value.toString())
            } ?: listOf()
        label.text = dropsyLabel
        if (dropsyLabel.isNullOrBlank()) {
            label.visibility = View.GONE
        }
        if (items.isNotEmpty()) {
            value.text = items[0].text
        }

        adapter = RecyclerViewAdapter()
        adapter.setSelection(0, items[0])
        adapter.onItemClickListener = { position ->
            val item = items[position]
            value.text = item.text
            adapter.setSelection(position, item)
            hideListView()
        }
        adapter.submitList(items)
        recyclerView.adapter = adapter
        // Fix inability to scroll from bottom to top
        recyclerView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                }

                MotionEvent.ACTION_UP -> {
                    v.performClick()
                    v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }

            v.onTouchEvent(event)
            true
        }
        val itemView: View = LayoutInflater.from(recyclerView.context)
            .inflate(R.layout.dropsy_item_drop_down, null)
        itemView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)

        // Set the height of the RecyclerView to be 4 times the height of a single item
        val layoutParams = recyclerView.layoutParams
        layoutParams.height = itemView.measuredHeight * 4
        recyclerView.layoutParams = layoutParams

    }

    private fun animateExpand(withAnimation: Boolean) {
        val rotate =
            RotateAnimation(
                360f,
                180f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
        rotate.duration = if (withAnimation) 300 else 0
        rotate.fillAfter = true
        imageArrow.startAnimation(rotate)
        expandableLayout.setExpanded(true, withAnimation)
    }

    private fun animateCollapse(withAnimation: Boolean) {
        val rotate =
            RotateAnimation(
                180f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
        rotate.duration = if (withAnimation) 300 else 0
        rotate.fillAfter = true
        imageArrow.startAnimation(rotate)
        expandableLayout.setExpanded(false, withAnimation)
    }
}