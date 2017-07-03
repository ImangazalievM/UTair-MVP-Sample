package com.kode.utair.presentation.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kode.utair.R;


public class PassengerCountPicker extends RelativeLayout {

    public static final int SUMMARY_MAX_PASSENGERS_COUNT = 9;

    private ImageView adultPassengerIncrementButton;
    private ImageView adultPassengerDecrementButton;
    private TextView adultCounterLabel;
    private TextView adultCounterCount;

    private ImageView kidPassengerIncrementButton;
    private ImageView kidPassengerDecrementButton;
    private TextView kidCounterLabel;
    private TextView kidCounterValue;

    private ImageView babyPassengerIncrementButton;
    private ImageView babyPassengerDecrementButton;
    private TextView babyPassengerLabel;
    private TextView babyCounterValue;

    private int adultsCount = 1;
    private int kidsCount = 0;
    private int babiesCount = 0;
    private int summaryCount = 0;

    public PassengerCountPicker(Context context) {
        super(context);
        initView();
    }

    public PassengerCountPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    private void initView() {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.passenger_count_picker_view, this, true);

        adultCounterLabel = (TextView) findViewById(R.id.adult_counter_label);
        adultCounterCount = (TextView) findViewById(R.id.adult_counter_count);
        adultPassengerIncrementButton = (ImageView) findViewById(R.id.adult_passenger_increment_button);
        adultPassengerDecrementButton = (ImageView) findViewById(R.id.adult_passenger_decrement_button);
        adultCounterCount.setText(String.valueOf(adultsCount));
        adultPassengerIncrementButton.setOnClickListener(v -> incrementAdult());
        adultPassengerDecrementButton.setOnClickListener(v -> decrementAdult());

        kidCounterLabel = (TextView) findViewById(R.id.kid_counter_label);
        kidCounterValue = (TextView) findViewById(R.id.kid_counter_count);
        kidPassengerIncrementButton = (ImageView) findViewById(R.id.kid_passenger_increment_button);
        kidPassengerDecrementButton = (ImageView) findViewById(R.id.kid_passenger_decrement_button);
        kidCounterValue.setText(String.valueOf(kidsCount));
        kidPassengerIncrementButton.setOnClickListener(v -> incrementKid());
        kidPassengerDecrementButton.setOnClickListener(v -> decrementKid());

        babyPassengerLabel = (TextView) findViewById(R.id.baby_counter_label);
        babyCounterValue = (TextView) findViewById(R.id.baby_counter_count);
        babyPassengerIncrementButton = (ImageView) findViewById(R.id.baby_passenger_increment_button);
        babyPassengerDecrementButton = (ImageView) findViewById(R.id.baby_passenger_decrement_button);
        babyCounterValue.setText(String.valueOf(babiesCount));
        babyPassengerIncrementButton.setOnClickListener(v -> incrementBaby());
        babyPassengerDecrementButton.setOnClickListener(v -> decrementBaby());

        updateViewsState();
    }

    private void updateViewsState() {
        boolean enableAdultsCounter = adultsCount != 1;
        adultCounterLabel.setEnabled(enableAdultsCounter);
        adultCounterCount.setEnabled(enableAdultsCounter);

        boolean enableKidsCounter = kidsCount != 0;
        kidCounterLabel.setEnabled(enableKidsCounter);
        kidCounterValue.setEnabled(enableKidsCounter);

        boolean enableBabiesCounter = babiesCount != 0;
        babyPassengerLabel.setEnabled(enableBabiesCounter);
        babyCounterValue.setEnabled(enableBabiesCounter);
    }

    private void incrementAdult() {
        if (isMaxValueReached()) {
            showMaxPassengersCountReachedMessage();
            return;
        }

        setAdultValue(++adultsCount);
        calculateSummaryCount();
    }

    private void decrementAdult() {
        if (adultsCount == 1) {
            return;
        }

        setAdultValue(--adultsCount);
        calculateSummaryCount();
    }

    private void setAdultValue(int value) {
        adultCounterCount.setText(String.valueOf(value));
        animateValueView(adultCounterCount);
    }

    private void incrementKid() {
        if (isMaxValueReached()) {
            showMaxPassengersCountReachedMessage();
            return;
        }

        setKidValue(++kidsCount);
        calculateSummaryCount();
    }

    private void decrementKid() {
        if (kidsCount == 0) {
            return;
        }

        setKidValue(--kidsCount);
        calculateSummaryCount();
    }

    private void setKidValue(int value) {
        kidCounterValue.setText(String.valueOf(value));
        animateValueView(kidCounterValue);
    }

    private void incrementBaby() {
        if (isMaxValueReached()) {
            showMaxPassengersCountReachedMessage();
            return;
        }

        if (babiesCount == adultsCount) {
            Toast.makeText(getContext(), getResources().getString(R.string.babies_less_adults_message), Toast.LENGTH_SHORT).show();
            return;
        }

        setBabyValue(++babiesCount);
        calculateSummaryCount();
    }

    private void decrementBaby() {
        if (babiesCount == 0) {
            return;
        }

        setBabyValue(--babiesCount);
        calculateSummaryCount();
    }

    private void setBabyValue(int value) {
        babyCounterValue.setText(String.valueOf(value));
        animateValueView(babyCounterValue);
    }

    private void animateValueView(TextView textView) {
        textView.animate().scaleX(1.3f).scaleY(1.3f).setDuration(150).withEndAction(() -> textView.animate().scaleX(1).scaleY(1).setDuration(50));
    }

    private void showMaxPassengersCountReachedMessage() {
        Toast.makeText(getContext(), String.format(getResources().getString(R.string.max_passenger_count_message), SUMMARY_MAX_PASSENGERS_COUNT), Toast.LENGTH_SHORT).show();
    }

    private boolean isMaxValueReached() {
        return summaryCount == SUMMARY_MAX_PASSENGERS_COUNT;
    }

    private void calculateSummaryCount() {
        summaryCount = adultsCount + kidsCount + babiesCount;
    }

}
