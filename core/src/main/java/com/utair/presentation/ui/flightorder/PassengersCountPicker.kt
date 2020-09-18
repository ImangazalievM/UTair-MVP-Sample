package com.utair.presentation.ui.flightorder

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.utair.core.R
import com.utair.presentation.mvp.flightorder.PassengersData

class PassengersCountPicker @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        theme: Int = 0
) : LinearLayout(context, attrs, theme) {

    private val adultsCounter by lazy { PassengerCounter(context) }
    private val kidsCounter by lazy { PassengerCounter(context) }
    private val babiesCounter by lazy { PassengerCounter(context) }
    var onValueChangedListener: ((PassengersData) -> Unit)? = null

    init {
        orientation = HORIZONTAL

        addView(adultsCounter)
        addView(kidsCounter)
        addView(babiesCounter)

        adultsCounter.setIcon(R.drawable.ic_passenger_adult)
        adultsCounter.setText(R.string.adult_passenger)
        adultsCounter.minValue = 1
        adultsCounter.value = 1
        adultsCounter.onValueChanged = { onValueChanged() }

        kidsCounter.setIcon(R.drawable.ic_passenger_kid)
        kidsCounter.setText(R.string.kid_passenger)
        kidsCounter.value = 0
        kidsCounter.onValueChanged = { onValueChanged() }

        babiesCounter.setIcon(R.drawable.ic_passenger_baby)
        babiesCounter.setText(R.string.baby_passenger)
        babiesCounter.value = 0
        babiesCounter.onValueChanged = { onValueChanged() }
    }

    fun setValues(passengersData: PassengersData) {
        adultsCounter.value = passengersData.adultCount
        kidsCounter.value = passengersData.kidCount
        babiesCounter.value = passengersData.babyCount
    }

    private fun onValueChanged() {
        val passengersData = PassengersData(
                adultCount = adultsCounter.value,
                kidCount = kidsCounter.value,
                babyCount = babiesCounter.value
        )
        onValueChangedListener?.invoke(passengersData)
    }

}