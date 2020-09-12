package com.utair.presentation.ui.flightorder

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.utair.R
import com.utair.di.presenters.orderflight.DaggerFlightOrderComponent
import com.utair.presentation.mvp.flightorder.FlightOrderPresenter
import com.utair.presentation.mvp.flightorder.FlightOrderView
import com.utair.presentation.ui.global.base.BaseMvpActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime
import java.text.SimpleDateFormat

class MainActivity : BaseMvpActivity(), FlightOrderView {

    private val dateFormatter = SimpleDateFormat("d MMM, E")

    @InjectPresenter
    lateinit var flightOrderPresenter: FlightOrderPresenter

    @ProvidePresenter
    fun providePresenter(): FlightOrderPresenter {
        return DaggerFlightOrderComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .getFlightOrderPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val plusIcon = VectorDrawableCompat.create(resources, R.drawable.ic_plus_10dp, theme)
        returnDateButtonLabel.setCompoundDrawablesRelativeWithIntrinsicBounds(plusIcon, null, null, null)

        departCityLayout.setOnClickListener {
            flightOrderPresenter.onDepartCityClicked()
        }
        arriveCityLayout.setOnClickListener {
            flightOrderPresenter.onArriveCityClicked()
        }
        swapCitiesButton.setOnClickListener {
            flightOrderPresenter.onSwapCitiesButtonClicked()
        }
        departDateLayout.setOnClickListener {
            flightOrderPresenter.onDepartDateClicked()
        }
        returnDateLayout.setOnClickListener {
            flightOrderPresenter.onReturnDateClicked()
        }
        returnDateButton.setOnClickListener {
            flightOrderPresenter.onSetReturnDateButtonClicked()
        }
        cleaReturnDateButton.setOnClickListener {
            flightOrderPresenter.onClearReturnDateClicked()
        }
        findFlightsButton.setOnClickListener {
            flightOrderPresenter.onFindFlightsButtonClicked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun showEmptyDepartCity() {
        departCityLabel.text = getString(R.string.where_from)
        departAirportName.visibility = View.INVISIBLE
    }

    override fun showDepartCity(departCity: String) {
        departCityLabel.text = departCity
        departAirportName.visibility = View.VISIBLE
    }

    override fun showDepartCitySelector(cities: List<String>) {
        showCitySelector(cities) {
            flightOrderPresenter.onDepartCitySelected(it)
        }
    }

    override fun showEmptyArriveCity() {
        arriveCityLabel.text = getString(R.string.where)
        arriveAirportName.visibility = View.INVISIBLE
    }

    override fun showArriveCity(arriveCity: String) {
        arriveCityLabel.text = arriveCity
        arriveAirportName.visibility = View.VISIBLE
    }

    override fun showArriveCitySelector(cities: List<String>) {
        showCitySelector(cities) {
            flightOrderPresenter.onArriveCitySelected(it)
        }
    }

    override fun disableSwapCitiesButton() {
        swapCitiesButton.isEnabled = false
    }

    override fun enableSwapCitiesButton() {
        swapCitiesButton.isEnabled = true
    }

    override fun showDepartDate(departDate: DateTime) {
        departDateView.text = dateFormatter.format(departDate.toDate())
    }

    override fun showDepartDatePicker(departDate: DateTime) {
        showDatePicker(departDate) {
            flightOrderPresenter.onDepartDateSelected(it)
        }
    }

    override fun showReturnDate(returnDate: DateTime) {
        returnDateButton.visibility = View.GONE
        returnDateLayout.visibility = View.VISIBLE
        cleaReturnDateButton.visibility = View.VISIBLE
        returnDateView.text = dateFormatter.format(returnDate.toDate())
    }

    override fun showReturnDatePicker(returnDate: DateTime) {
        showDatePicker(returnDate) {
            flightOrderPresenter.onReturnDateSelected(it)
        }
    }

    override fun hideReturnDate() {
        returnDateLayout.visibility = View.GONE
        cleaReturnDateButton.visibility = View.GONE
    }

    override fun showReturnDateButton() {
        returnDateButton.visibility = View.VISIBLE
    }

    override fun hideReturnDateButton() {
        returnDateButton.visibility = View.GONE
    }

    override fun showNoNetworkMessage() {
        Toast.makeText(this, getString(R.string.no_network_message), Toast.LENGTH_SHORT).show()
    }

    override fun showValidationErrorMessage(errorMessage: String) {
        val snackbar = Snackbar.make(snackbarContainer, errorMessage, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundResource(R.color.color_blue_dark100)
        snackbar.show()
    }

    private fun showDatePicker(date: DateTime, onDateSelected: (DateTime) -> Unit) {
        val datePickerDialog = DatePickerDialog.newInstance(
                { _: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    onDateSelected(DateTime(year, monthOfYear, dayOfMonth, 0, 0))
                },
                date.year,
                date.monthOfYear().get(),
                date.dayOfWeek().get()
        )
        datePickerDialog.show(supportFragmentManager, "DatePickerDialog")
    }

    private fun showCitySelector(cities: List<String>, onItemSelected: (String) -> Unit) {
        MaterialDialog(this).show {
            title(R.string.select_city)
            listItems(items = cities) { _, index, _ ->
                onItemSelected(cities[index])
            }

        }

    }
}