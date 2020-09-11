package com.kode.utair.presentation.ui.adapters;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kode.utair.R;
import com.kode.utair.domain.models.WeatherForecastEntity;

import java.text.SimpleDateFormat;
import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.DayForecastViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<WeatherForecastEntity.DailyForecast> dailyForecasts;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("d MMM, E");

    public DailyForecastAdapter(Context context, List<WeatherForecastEntity.DailyForecast> dailyForecasts) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dailyForecasts = dailyForecasts;
    }

    @Override
    public DayForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_daily_forecast, parent, false);
        return new DayForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayForecastViewHolder holder, int position) {
        WeatherForecastEntity.DailyForecast dailyForecast = dailyForecasts.get(position);

        holder.dayLabel.setText(dateFormatter.format(dailyForecast.getDate().toDate()));
        holder.hourlyForecastList.setAdapter(new HourlyForecastAdapter(context, dailyForecast.getHourlyForecasts()));
    }

    @Override
    public int getItemCount() {
        return dailyForecasts.size();
    }

    public class DayForecastViewHolder extends RecyclerView.ViewHolder {

        private TextView dayLabel;
        private RecyclerView hourlyForecastList;

        private DayForecastViewHolder(View itemView) {
            super(itemView);

            this.dayLabel = (TextView) itemView.findViewById(R.id.day_label);
            this.hourlyForecastList = (RecyclerView) itemView.findViewById(R.id.hourly_forecast_list);

            hourlyForecastList.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }

    }
}