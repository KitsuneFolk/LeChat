package com.pandacorp.lechat.presentation.ui.adapter.spinner

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.pandacorp.lechat.R
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerInterface
import com.skydoves.powerspinner.PowerSpinnerView
import com.skydoves.powerspinner.databinding.PowerspinnerItemDefaultPowerBinding

class SpinnerAdapter(
    powerSpinnerView: PowerSpinnerView
) : RecyclerView.Adapter<SpinnerAdapter.DefaultSpinnerViewHolder>(), PowerSpinnerInterface<CharSequence> {
    companion object {
        private const val NO_SELECTED_INDEX = -1
        private const val NO_INT_VALUE: Int = Int.MIN_VALUE
    }

    override var index: Int = powerSpinnerView.selectedIndex
    override val spinnerView: PowerSpinnerView = powerSpinnerView
    override var onSpinnerItemSelectedListener: OnSpinnerItemSelectedListener<CharSequence>? = null

    private val spinnerItems: MutableList<CharSequence> = arrayListOf()

    inner class DefaultSpinnerViewHolder(private val binding: PowerspinnerItemDefaultPowerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bind(spinnerView: PowerSpinnerView, item: CharSequence, isSelectedItem: Boolean) {
            binding.itemDefaultText.apply {
                text = item
                typeface = spinnerView.typeface
                gravity = spinnerView.gravity
                setTextSize(TypedValue.COMPLEX_UNIT_PX, spinnerView.textSize)
                setTextColor(spinnerView.currentTextColor)
            }
            binding.root.setPadding(
                spinnerView.paddingLeft,
                spinnerView.paddingTop,
                spinnerView.paddingRight,
                spinnerView.paddingBottom
            )
            if (spinnerView.spinnerItemHeight != NO_INT_VALUE) {
                binding.root.height = spinnerView.spinnerItemHeight
            }
            if (spinnerView.spinnerSelectedItemBackground != null && isSelectedItem) {
                binding.root.background = spinnerView.spinnerSelectedItemBackground
            } else {
                val tv = TypedValue()
                binding.root.context.theme.resolveAttribute(
                    android.R.attr.selectableItemBackground,
                    tv,
                    true
                )
                binding.root.background = AppCompatResources.getDrawable(
                    binding.root.context,
                    R.drawable.colorbackground_rounded_rectangle
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultSpinnerViewHolder {
        val binding =
            PowerspinnerItemDefaultPowerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return DefaultSpinnerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                notifyItemSelected(position)
            }
        }
    }

    override fun onBindViewHolder(holder: DefaultSpinnerViewHolder, position: Int) {
        holder.bind(spinnerView, spinnerItems[position], index == position)
    }

    override fun setItems(itemList: List<CharSequence>) {
        this.spinnerItems.clear()
        this.spinnerItems.addAll(itemList)
        this.index = NO_SELECTED_INDEX
        notifyDataSetChanged()
    }

    override fun notifyItemSelected(index: Int) {
        if (index == NO_SELECTED_INDEX) return
        val oldIndex = this.index
        this.index = index
        this.spinnerView.notifyItemSelected(index, spinnerItems[index])
        notifyDataSetChanged()
        this.onSpinnerItemSelectedListener?.onItemSelected(
            oldIndex = oldIndex,
            oldItem = oldIndex.takeIf { it != NO_SELECTED_INDEX }?.let { spinnerItems[oldIndex] },
            newIndex = index,
            newItem = spinnerItems[index]
        )
    }

    override fun getItemCount(): Int = spinnerItems.size
}
