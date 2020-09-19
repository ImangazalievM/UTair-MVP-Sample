package com.utair.presentation.ui.weatherforecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utair.core.R
import com.utair.domain.global.models.WeatherForecast
import com.utair.domain.global.models.WeatherForecast.WeatherCondition
import com.utair.presentation.ui.weatherforecast.HourlyForecastAdapter.ProductViewHolder
import java.text.SimpleDateFormat

class HourlyForecastAdapter(
        context: Context,
        private val hourlyForecasts: List<WeatherForecast.HourlyForecast>
) : RecyclerView.Adapter<ProductViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val timeFormatter = SimpleDateFormat("HH:mm")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = layoutInflater.inflate(R.layout.item_hourly_forecast, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val hourlyForecast = hourlyForecasts[position]
        holder.time.text = timeFormatter.format(hourlyForecast.dateTime.toDate())
        holder.weatherIcon.setImageResource(getIconForCondition(hourlyForecast.condition))
        holder.temperature.text = String.format("%.2f °C", hourlyForecast.temperature)
        holder.windSpeed.text = String.format("%.2f m/s", hourlyForecast.speed)
    }

    override fun getItemCount() = hourlyForecasts.size

    private fun getIconForCondition(weatherCondition: WeatherCondition?): Int {
        return when (weatherCondition) {
            WeatherCondition.SUNNY -> R.drawable.ic_sun
            WeatherCondition.FOG -> R.drawable.ic_fog
            WeatherCondition.LIGHT_CLOUDS -> R.drawable.ic_light_clouds
            WeatherCondition.CLOUDS -> R.drawable.ic_clouds
            WeatherCondition.LIGHT_RAIN -> R.drawable.ic_light_rain
            WeatherCondition.RAIN -> R.drawable.ic_rain
            WeatherCondition.SNOW -> R.drawable.ic_snowflake
            WeatherCondition.STORM -> R.drawable.ic_storm
            else -> R.drawable.ic_question
        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView
        val weatherIcon: ImageView
        val temperature: TextView
        val windSpeed: TextView

        init {
            time = itemView.findViewById<View>(R.id.forecast_time) as TextView
            weatherIcon = itemView.findViewById<View>(R.id.weather_icon) as ImageView
            temperature = itemView.findViewById<View>(R.id.temperature) as TextView
            windSpeed = itemView.findViewById<View>(R.id.wind_speed) as TextView
        }
    }

}