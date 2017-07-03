package com.kode.utair.presentation.ui.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kode.utair.R;
import com.kode.utair.UTairApplication;
import com.kode.utair.presentation.mvp.presenters.MainPresenter;
import com.kode.utair.presentation.mvp.views.MainView;
import com.kode.utair.presentation.ui.views.PassengerCountPicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends BaseMvpActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return new MainPresenter(UTairApplication.component());
    }

    private View departCityLayout;
    private TextView departCityName;
    private TextView departAirportName;
    private View arriveCityLayout;
    private TextView arriveCityName;
    private TextView arriveAirportName;
    private View swapCitiesButton;
    private View departDateLayout;
    private TextView departDateView;
    private View returnDateLayout;
    private TextView returnDateView;
    private LinearLayout setReturnDateButton;
    private View cleaReturnDateButton;
    private PassengerCountPicker passengerCountPicker;
    private View findFlightsButton;
    private View snackbarContainer;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("d MMM, E");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initViews();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initViews() {
        departCityLayout = findViewById(R.id.depart_city_layout);
        departCityName = (TextView) findViewById(R.id.depart_city);
        departAirportName = (TextView) findViewById(R.id.depart_airport);
        arriveCityLayout = findViewById(R.id.arrive_city_layout);
        arriveCityName = (TextView) findViewById(R.id.arrive_city);
        arriveAirportName = (TextView) findViewById(R.id.arrive_airport);
        swapCitiesButton = findViewById(R.id.swap_cities_button);
        departDateLayout = findViewById(R.id.depart_date_layout);
        departDateView = (TextView) findViewById(R.id.depart_date);
        returnDateLayout = findViewById(R.id.return_date_layout);
        returnDateView = (TextView) findViewById(R.id.return_date);
        setReturnDateButton = (LinearLayout) findViewById(R.id.set_return_date_button);
        cleaReturnDateButton = findViewById(R.id.clear_return_date_button);
        passengerCountPicker = (PassengerCountPicker) findViewById(R.id.passenger_count_picker);
        findFlightsButton = findViewById(R.id.find_flights_button);
        snackbarContainer = findViewById(R.id.snackbar_container);

        departCityLayout.setOnClickListener(v -> mainPresenter.onDepartCityClicked());
        arriveCityLayout.setOnClickListener(v -> mainPresenter.onArriveCityClicked());
        swapCitiesButton.setOnClickListener(v -> mainPresenter.onSwapCitiesButtonClicked());
        departDateLayout.setOnClickListener(v -> mainPresenter.onDepartDateClicked());
        returnDateLayout.setOnClickListener(v -> mainPresenter.onReturnDateClicked());
        setReturnDateButton.setOnClickListener(v -> mainPresenter.onSetReturnDateButtonClicked());
        cleaReturnDateButton.setOnClickListener(v -> mainPresenter.onClearReturnDateClicked());
        findFlightsButton.setOnClickListener(v -> mainPresenter.onFindFlightsButtonClicked());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public void showEmptyDepartCity() {
        departCityName.setText(getString(R.string.where_from));
        departAirportName.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showDepartCity(String departCity) {
        departCityName.setText(departCity);
        departAirportName.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDepartCitySelector(List<String> cities) {
        showCitySelector(cities, (dialog, itemView, position, text) -> {
            mainPresenter.onDepartCitySelected(text.toString());
        });
    }

    @Override
    public void showEmptyArriveCity() {
        arriveCityName.setText(getString(R.string.where));
        arriveAirportName.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showArriveCity(String arriveCity) {
        arriveCityName.setText(arriveCity);
        arriveAirportName.setVisibility(View.VISIBLE);
    }

    @Override
    public void showArriveCitySelector(List<String> cities) {
        showCitySelector(cities, (dialog, itemView, position, text) -> {
            mainPresenter.onArriveCitySelected(text.toString());
        });
    }

    @Override
    public void disableSwapCitiesButton() {
        swapCitiesButton.setEnabled(false);
    }

    @Override
    public void enableSwapCitiesButton() {
        swapCitiesButton.setEnabled(true);
    }

    @Override
    public void showDepartDate(DateTime departDate) {
        departDateView.setText(dateFormatter.format(departDate.toDate()));
    }

    @Override
    public void showDepartDatePicker(DateTime departDate) {
        showDatePicker(departDate, (view, year, monthOfYear, dayOfMonth) -> {
            mainPresenter.onDepartDateSelected(new DateTime(year, monthOfYear, dayOfMonth, 0, 0));
        });
    }

    @Override
    public void showReturnDate(DateTime returnDate) {
        setReturnDateButton.setVisibility(View.GONE);
        returnDateLayout.setVisibility(View.VISIBLE);
        cleaReturnDateButton.setVisibility(View.VISIBLE);
        returnDateView.setText(dateFormatter.format(returnDate.toDate()));
    }

    @Override
    public void showReturnDatePicker(DateTime returnDate) {
        showDatePicker(returnDate, (view, year, monthOfYear, dayOfMonth) -> {
            mainPresenter.onReturnDateSelected(new DateTime(year, monthOfYear, dayOfMonth, 0, 0));
        });
    }

    @Override
    public void hideReturnDate() {
        returnDateLayout.setVisibility(View.GONE);
        cleaReturnDateButton.setVisibility(View.GONE);
    }

    @Override
    public void showReturnDateButton() {
        setReturnDateButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideReturnDateButton() {
        setReturnDateButton.setVisibility(View.GONE);
    }

    @Override
    public void showValidationErrorMessage(String errorMessage) {
        Snackbar snackbar = Snackbar.make(snackbarContainer, errorMessage, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundResource(R.color.color_blue_dark100);
        snackbar.show();
    }

    @Override
    public void openWeatherScreen() {
        startActivity(WeatherForecastActivity.buildIntent(this));
    }

    private void showDatePicker(DateTime date, DatePickerDialog.OnDateSetListener callBack) {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(callBack, date.getYear(), date.monthOfYear().get(), date.dayOfWeek().get());
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    private void showCitySelector(List<String> cities, MaterialDialog.ListCallback callback) {
        new MaterialDialog.Builder(this)
                .title(R.string.city_selection)
                .items(cities)
                .itemsCallback(callback)
                .show();
    }

}
