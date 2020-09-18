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
import com.utair.core.databinding.ActivityMainBinding
import com.utair.di.presenters.orderflight.DaggerFlightOrderComponent
import com.utair.presentation.mvp.flightorder.FlightOrderPresenter
import com.utair.presentation.mvp.flightorder.FlightOrderView
import com.utair.presentation.mvp.flightorder.PassengersData
import com.utair.presentation.ui.global.base.BaseMvpActivity
import com.utair.presentation.ui.global.visible
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.joda.time.DateTime
import java.text.SimpleDateFormat

class MainActivity : BaseMvpActivity(), FlightOrderView {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val dateFormatter = SimpleDateFormat("d MMM, E")

    @InjectPresenter
    lateinit var presenter: FlightOrderPresenter

    @ProvidePresenter
    fun providePresenter(): FlightOrderPresenter {
        return DaggerFlightOrderComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .getFlightOrderPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val plusIcon = VectorDrawableCompat.create(resources, R.drawable.ic_plus_10dp, theme)
        binding.addReturnDateButtonLabel.setCompoundDrawablesRelativeWithIntrinsicBounds(plusIcon, null, null, null)

        binding.departCityButton.setOnClickListener {
            presenter.onDepartCityClicked()
        }
        binding.arriveCityButton.setOnClickListener {
            presenter.onArriveCityClicked()
        }
        binding.swapCitiesButton.setOnClickListener {
            presenter.onSwapCitiesButtonClicked()
        }
        binding.departDateButton.setOnClickListener {
            presenter.onSetDepartDateClicked()
        }
        binding.returnDateButton.setOnClickListener {
            presenter.onReturnDateClicked()
        }
        binding.addReturnDateButton.setOnClickListener {
            presenter.onSetReturnDateButtonClicked()
        }
        binding.removeReturnDateButton.setOnClickListener {
            presenter.onRemoveReturnDateClicked()
        }
        binding.passengerCountPicker.onValueChangedListener = {
            presenter.onPassengersValueChanged(it)
        }
        binding.findFlightsButton.setOnClickListener {
            presenter.onFindFlightsButtonClicked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun showEmptyDepartCity() {
        binding.departCityLabel.text = getString(R.string.where_from)
        binding.departAirportName.visibility = View.INVISIBLE
    }

    override fun showDepartCity(departCity: String) {
        binding.departCityLabel.text = departCity
        binding.departAirportName.visibility = View.VISIBLE
    }

    override fun showDepartCitySelector(cities: List<String>) {
        showCitySelector(cities) {
            presenter.onDepartCitySelected(it)
        }
    }

    override fun showEmptyArriveCity() {
        binding.arriveCityLabel.text = getString(R.string.where)
        binding.arriveAirportName.visibility = View.INVISIBLE
    }

    override fun showArriveCity(arriveCity: String) {
        binding.arriveCityLabel.text = arriveCity
        binding.arriveAirportName.visibility = View.VISIBLE
    }

    override fun showArriveCitySelector(cities: List<String>) {
        showCitySelector(cities) {
            presenter.onArriveCitySelected(it)
        }
    }

    override fun disableSwapCitiesButton() {
        binding.swapCitiesButton.isEnabled = false
    }

    override fun enableSwapCitiesButton() {
        binding.swapCitiesButton.isEnabled = true
    }

    override fun showDepartDate(departDate: DateTime) {
        binding.departDateView.text = dateFormatter.format(departDate.toDate())
    }

    override fun showDepartDatePicker(departDate: DateTime) {
        showDatePicker(departDate) {
            presenter.onDepartDateSelected(it)
        }
    }

    override fun showReturnDate(returnDate: DateTime) {
        binding.returnDateView.text = dateFormatter.format(returnDate.toDate())
    }

    override fun updateReturnDateVisibility(isVisible: Boolean) {
        binding.returnDateButton.visible(isVisible)
        binding.addReturnDateButton.visible(!isVisible)
        binding.removeReturnDateButton.visible(isVisible)
    }

    override fun showReturnDatePicker(returnDate: DateTime) {
        showDatePicker(returnDate) {
            presenter.onReturnDateSelected(it)
        }
    }

    override fun showPassengersData(passengersData: PassengersData) {
        binding.passengerCountPicker.setValues(passengersData)
    }

    override fun showValidationErrorMessage(errorMessage: String) {
        val snackbar = Snackbar.make(binding.snackbarContainer, errorMessage, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundResource(R.color.color_blue_dark100)
        snackbar.show()
    }

    override fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun showDatePicker(date: DateTime, onDateSelected: (DateTime) -> Unit) {
        val datePickerDialog = DatePickerDialog.newInstance(
                { _: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    onDateSelected(DateTime(year, monthOfYear, dayOfMonth, 0, 0))
                },
                date.year,
                date.monthOfYear().get(),
                date.dayOfMonth().get()
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