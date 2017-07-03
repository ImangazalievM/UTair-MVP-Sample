package com.kode.utair.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kode.utair.R;
import com.kode.utair.domain.models.WeatherForecastEntity;

import java.text.SimpleDateFormat;
import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.ProductViewHolder> {

    private LayoutInflater layoutInflater;
    private List<WeatherForecastEntity.HourlyForecast> hourlyForecasts;

    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    public HourlyForecastAdapter(Context context, List<WeatherForecastEntity.HourlyForecast> hourlyForecasts) {
        this.layoutInflater = LayoutInflater.from(context);
        this.hourlyForecasts = hourlyForecasts;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_hourly_forecast, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        WeatherForecastEntity.HourlyForecast hourlyForecast = hourlyForecasts.get(position);

        holder.time.setText(timeFormatter.format(hourlyForecast.getDateTime().toDate()));
        holder.weatherIcon.setImageResource(getIconForCondition(hourlyForecast.getCondition()));
        holder.temperature.setText(String.format("%.2f °C", hourlyForecast.getTemperature()));
        holder.windSpeed.setText(String.format("%.2f м/с", hourlyForecast.getSpeed()));
    }


    @Override
    public int getItemCount() {
        return hourlyForecasts.size();
    }

    public int getIconForCondition(WeatherForecastEntity.WeatherCondition weatherCondition) {
        switch (weatherCondition) {
            case SUNNY: return R.drawable.ic_sun;
            case FOG: return R.drawable.ic_fog;
            case LIGHT_CLOUDS: return R.drawable.ic_light_clouds;
            case CLOUDS: return R.drawable.ic_clouds;
            case LIGHT_RAIN: return R.drawable.ic_light_rain;
            case RAIN: return R.drawable.ic_rain;
            case SNOW: return R.drawable.ic_snowflake;
            case STORM: return R.drawable.ic_storm;
            default: return R.drawable.ic_question;
        }
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        final TextView time;
        final ImageView weatherIcon;
        final TextView temperature;
        final TextView windSpeed;

        ProductViewHolder(View itemView) {
            super(itemView);

            this.time = (TextView) itemView.findViewById(R.id.forecast_time);
            this.weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon);
            this.temperature = (TextView) itemView.findViewById(R.id.temperature);
            this.windSpeed = (TextView) itemView.findViewById(R.id.wind_speed);
        }

    }

}
