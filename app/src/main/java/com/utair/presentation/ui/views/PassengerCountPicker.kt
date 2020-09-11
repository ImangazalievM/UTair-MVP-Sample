package com.utair.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.utair.R
import kotlinx.android.synthetic.main.passenger_count_picker_view.view.*
import javax.inject.Inject

class PassengerCountPicker @Inject constructor(
        context: Context,
        attrs: AttributeSet?,
        theme: Int = 0
) : RelativeLayout(context, attrs, theme) {

    private var adultsCount = 1
    private var kidsCount = 0
    private var babiesCount = 0
    private var summaryCount = 0

    init {
        initView()
    }

    private fun initView() {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.passenger_count_picker_view, this, true)

        adultCounterValue.text = adultsCount.toString()
        adultPassengerIncrementButton.setOnClickListener { incrementAdult() }
        adultPassengerDecrementButton.setOnClickListener { decrementAdult() }

        kidCounterValue.text = kidsCount.toString()
        kidPassengerIncrementButton.setOnClickListener { incrementKid() }
        kidPassengerDecrementButton.setOnClickListener { decrementKid() }

        babyCounterValue.text = babiesCount.toString()
        babyPassengerIncrementButton.setOnClickListener { incrementBaby() }
        babyPassengerDecrementButton.setOnClickListener { decrementBaby() }

        updateViewsState()
    }

    private fun updateViewsState() {
        val enableKidsCounter = kidsCount != 0
        kidCounteIcon.isEnabled = enableKidsCounter
        kidCounterValue.isEnabled = enableKidsCounter
        val enableBabiesCounter = babiesCount != 0
        babyCounteIcon.isEnabled = enableBabiesCounter
        babyCounterValue.isEnabled = enableBabiesCounter
    }

    private fun incrementAdult() {
        if (isMaxValueReached) {
            showMaxPassengersCountReachedMessage()
            return
        }
        setAdultValue(++adultsCount)
    }

    private fun decrementAdult() {
        if (adultsCount == 1) {
            return
        }
        setAdultValue(--adultsCount)
    }

    private fun setAdultValue(value: Int) {
        adultCounterValue.text = value.toString()
        animateValueView(adultCounterValue)
        calculateSummaryCount()
        updateViewsState()
    }

    private fun incrementKid() {
        if (isMaxValueReached) {
            showMaxPassengersCountReachedMessage()
            return
        }
        setKidValue(++kidsCount)
    }

    private fun decrementKid() {
        if (kidsCount == 0) {
            return
        }
        setKidValue(--kidsCount)
    }

    private fun setKidValue(value: Int) {
        kidCounterValue.text = value.toString()
        animateValueView(kidCounterValue)
        calculateSummaryCount()
        updateViewsState()
    }

    private fun incrementBaby() {
        if (isMaxValueReached) {
            showMaxPassengersCountReachedMessage()
            return
        }
        if (babiesCount == adultsCount) {
            Toast.makeText(context, resources.getString(R.string.babies_less_adults_message), Toast.LENGTH_SHORT).show()
            return
        }
        setBabyValue(++babiesCount)
    }

    private fun decrementBaby() {
        if (babiesCount == 0) {
            return
        }
        setBabyValue(--babiesCount)
    }

    private fun setBabyValue(value: Int) {
        babyCounterValue.text = value.toString()
        animateValueView(babyCounterValue)
        calculateSummaryCount()
        updateViewsState()
    }

    private fun animateValueView(textView: TextView) {
        textView.animate()
                .scaleX(1.3f)
                .scaleY(1.3f)
                .setDuration(150)
                .withEndAction {
                    textView.animate().scaleX(1f).scaleY(1f).duration = 50
                }
    }

    private fun showMaxPassengersCountReachedMessage() {
        Toast.makeText(context, String.format(resources.getString(R.string.max_passenger_count_message), SUMMARY_MAX_PASSENGERS_COUNT), Toast.LENGTH_SHORT).show()
    }

    private val isMaxValueReached: Boolean
        private get() = summaryCount == SUMMARY_MAX_PASSENGERS_COUNT

    private fun calculateSummaryCount() {
        summaryCount = adultsCount + kidsCount + babiesCount
    }

    companion object {
        const val SUMMARY_MAX_PASSENGERS_COUNT = 9
    }
}