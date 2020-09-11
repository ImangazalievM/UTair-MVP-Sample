package com.kode.utair.presentation.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.kode.utair.R
import com.kode.utair.UTairApplication
import com.kode.utair.presentation.mvp.presenters.MainPresenter
import com.kode.utair.presentation.mvp.views.MainView
import com.kode.utair.presentation.ui.views.PassengerCountPicker
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.joda.time.DateTime
import java.text.SimpleDateFormat

class MainActivity : BaseMvpActivity(), MainView {
    @JvmField
    @InjectPresenter
    var mainPresenter: MainPresenter? = null

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return MainPresenter(UTairApplication.component())
    }

    private var departCityLayout: View? = null
    private var departCityName: TextView? = null
    private var departAirportName: TextView? = null
    private var arriveCityLayout: View? = null
    private var arriveCityName: TextView? = null
    private var arriveAirportName: TextView? = null
    private var swapCitiesButton: View? = null
    private var departDateLayout: View? = null
    private var departDateView: TextView? = null
    private var returnDateLayout: View? = null
    private var returnDateView: TextView? = null
    private var returnDateButton: LinearLayout? = null
    private var returnDateButtonLabel: TextView? = null
    private var cleaReturnDateButton: View? = null
    private var passengerCountPicker: PassengerCountPicker? = null
    private var findFlightsButton: View? = null
    private var snackbarContainer: View? = null
    private val dateFormatter = SimpleDateFormat("d MMM, E")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initViews()
    }

    private fun initToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    private fun initViews() {
        departCityLayout = findViewById(R.id.depart_city_layout)
        departCityName = findViewById<View>(R.id.depart_city) as TextView
        departAirportName = findViewById<View>(R.id.depart_airport) as TextView
        arriveCityLayout = findViewById(R.id.arrive_city_layout)
        arriveCityName = findViewById<View>(R.id.arrive_city) as TextView
        arriveAirportName = findViewById<View>(R.id.arrive_airport) as TextView
        swapCitiesButton = findViewById(R.id.swap_cities_button)
        departDateLayout = findViewById(R.id.depart_date_layout)
        departDateView = findViewById<View>(R.id.depart_date) as TextView
        returnDateLayout = findViewById(R.id.return_date_layout)
        returnDateView = findViewById<View>(R.id.return_date) as TextView
        returnDateButton = findViewById<View>(R.id.return_date_button) as LinearLayout
        returnDateButtonLabel = findViewById<View>(R.id.return_date_button_label) as TextView
        cleaReturnDateButton = findViewById(R.id.clear_return_date_button)
        passengerCountPicker = findViewById<View>(R.id.passenger_count_picker) as PassengerCountPicker
        findFlightsButton = findViewById(R.id.find_flights_button)
        snackbarContainer = findViewById(R.id.snackbar_container)
        val plusIcon = VectorDrawableCompat.create(resources, R.drawable.ic_plus_10dp, theme)
        returnDateButtonLabel!!.setCompoundDrawablesRelativeWithIntrinsicBounds(plusIcon, null, null, null)
        departCityLayout!!.setOnClickListener(View.OnClickListener { v: View? -> mainPresenter!!.onDepartCityClicked() })
        arriveCityLayout!!.setOnClickListener(View.OnClickListener { v: View? -> mainPresenter!!.onArriveCityClicked() })
        swapCitiesButton!!.setOnClickListener(View.OnClickListener { v: View? -> mainPresenter!!.onSwapCitiesButtonClicked() })
        departDateLayout!!.setOnClickListener(View.OnClickListener { v: View? -> mainPresenter!!.onDepartDateClicked() })
        returnDateLayout!!.setOnClickListener(View.OnClickListener { v: View? -> mainPresenter!!.onReturnDateClicked() })
        returnDateButton!!.setOnClickListener { v: View? -> mainPresenter!!.onSetReturnDateButtonClicked() }
        cleaReturnDateButton!!.setOnClickListener(View.OnClickListener { v: View? -> mainPresenter!!.onClearReturnDateClicked() })
        findFlightsButton!!.setOnClickListener(View.OnClickListener { v: View? -> mainPresenter!!.onFindFlightsButtonClicked() })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun showEmptyDepartCity() {
        departCityName!!.text = getString(R.string.where_from)
        departAirportName!!.visibility = View.INVISIBLE
    }

    override fun showDepartCity(departCity: String) {
        departCityName!!.text = departCity
        departAirportName!!.visibility = View.VISIBLE
    }

    override fun showDepartCitySelector(cities: List<String>) {
        showCitySelector(cities) {
            mainPresenter!!.onDepartCitySelected(it)
        }
    }

    override fun showEmptyArriveCity() {
        arriveCityName!!.text = getString(R.string.where)
        arriveAirportName!!.visibility = View.INVISIBLE
    }

    override fun showArriveCity(arriveCity: String) {
        arriveCityName!!.text = arriveCity
        arriveAirportName!!.visibility = View.VISIBLE
    }

    override fun showArriveCitySelector(cities: List<String>) {
        showCitySelector(cities) {
            mainPresenter!!.onArriveCitySelected(it)
        }
    }

    override fun disableSwapCitiesButton() {
        swapCitiesButton!!.isEnabled = false
    }

    override fun enableSwapCitiesButton() {
        swapCitiesButton!!.isEnabled = true
    }

    override fun showDepartDate(departDate: DateTime) {
        departDateView!!.text = dateFormatter.format(departDate.toDate())
    }

    override fun showDepartDatePicker(departDate: DateTime) {
        showDatePicker(departDate, DatePickerDialog.OnDateSetListener { view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int -> mainPresenter!!.onDepartDateSelected(DateTime(year, monthOfYear, dayOfMonth, 0, 0)) })
    }

    override fun showReturnDate(returnDate: DateTime) {
        returnDateButton!!.visibility = View.GONE
        returnDateLayout!!.visibility = View.VISIBLE
        cleaReturnDateButton!!.visibility = View.VISIBLE
        returnDateView!!.text = dateFormatter.format(returnDate.toDate())
    }

    override fun showReturnDatePicker(returnDate: DateTime) {
        showDatePicker(returnDate, DatePickerDialog.OnDateSetListener { view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int -> mainPresenter!!.onReturnDateSelected(DateTime(year, monthOfYear, dayOfMonth, 0, 0)) })
    }

    override fun hideReturnDate() {
        returnDateLayout!!.visibility = View.GONE
        cleaReturnDateButton!!.visibility = View.GONE
    }

    override fun showReturnDateButton() {
        returnDateButton!!.visibility = View.VISIBLE
    }

    override fun hideReturnDateButton() {
        returnDateButton!!.visibility = View.GONE
    }

    override fun showNoNetworkMessage() {
        Toast.makeText(this, getString(R.string.no_network_message), Toast.LENGTH_SHORT).show()
    }

    override fun showValidationErrorMessage(errorMessage: String) {
        val snackbar = Snackbar.make(snackbarContainer!!, errorMessage, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundResource(R.color.color_blue_dark100)
        snackbar.show()
    }

    override fun openWeatherScreen() {
        startActivity(WeatherForecastActivity.buildIntent(this))
    }

    private fun showDatePicker(date: DateTime, callBack: DatePickerDialog.OnDateSetListener) {
        val datePickerDialog = DatePickerDialog.newInstance(callBack, date.year, date.monthOfYear().get(), date.dayOfWeek().get())
        datePickerDialog.show(fragmentManager, "DatePickerDialog")
    }

    private fun showCitySelector(cities: List<String>, onItemSelected: (String) -> Unit) {
        MaterialDialog(this).show {
            title(R.string.city_selection)
            listItems(items = cities) { _, index, _ ->
                onItemSelected(cities[index])
            }

        }

    }
}