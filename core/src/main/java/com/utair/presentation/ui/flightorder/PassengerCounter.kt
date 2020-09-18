package com.utair.presentation.ui.flightorder

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.utair.core.R
import kotlinx.android.synthetic.main.view_passenger_counter.view.*

class PassengerCounter @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        theme: Int = 0
) : LinearLayout(context, attrs, theme) {

    var value: Int = 0
        set(value) {
            field = value
            counterValue.text = value.toString()
        }
    var minValue: Int = 0
    var maxValue: Int = Int.MAX_VALUE
    private val isMaxValueReached: Boolean
        get() = value == maxValue
    var onValueChanged: ((value: Int) -> Unit)? = null

    init {
        orientation = VERTICAL
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_passenger_counter, this, true)

        incrementButton.setOnClickListener { incrementValue() }
        decrementButton.setOnClickListener { decrementValue() }
    }

    fun setIcon(@DrawableRes iconResId: Int) {
        counterIcon.setImageResource(iconResId)
    }

    fun setText(@StringRes textResId: Int) {
        counterLabel.setText(textResId)
    }

    private fun incrementValue() {
        if (!isMaxValueReached) {
            ++value
            animateValueView()
            updateViewsState()
            onValueChanged?.invoke(value)
        }
    }

    private fun decrementValue() {
        if (value > minValue) {
            --value
            animateValueView()
            updateViewsState()
            onValueChanged?.invoke(value)
        }
    }

    private fun updateViewsState() {
        val isEnabled = value != 0
        counterIcon.isEnabled = isEnabled
        counterValue.isEnabled = isEnabled
    }

    private fun animateValueView() {
        counterValue.animate()
                .scaleX(1.3f)
                .scaleY(1.3f)
                .setDuration(150)
                .withEndAction {
                    counterValue.animate().scaleX(1f).scaleY(1f).duration = 50
                }
    }

}