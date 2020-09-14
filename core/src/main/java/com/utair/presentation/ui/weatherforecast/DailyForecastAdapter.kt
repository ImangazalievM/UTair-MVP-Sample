package com.utair.presentation.ui.weatherforecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utair.core.R
import com.utair.domain.global.models.WeatherForecast.DailyForecast
import com.utair.presentation.ui.weatherforecast.DailyForecastAdapter.DayForecastViewHolder
import kotlinx.android.synthetic.main.item_daily_forecast.view.*
import java.text.SimpleDateFormat

class DailyForecastAdapter(
        private val context: Context,
        private val dailyForecasts: List<DailyForecast>
) : RecyclerView.Adapter<DayForecastViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val dateFormatter = SimpleDateFormat("d MMM, E")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayForecastViewHolder {
        val view = layoutInflater.inflate(R.layout.item_daily_forecast, parent, false)
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        view.hourlyForecastList.layoutManager = layoutManager
        return DayForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayForecastViewHolder, position: Int) {
        val dailyForecast = dailyForecasts[position]
        val itemView = holder.itemView
        itemView.dayLabel.text = dateFormatter.format(dailyForecast.date.toDate())
        itemView.hourlyForecastList.adapter = HourlyForecastAdapter(context, dailyForecast.getHourlyForecasts())
    }

    override fun getItemCount() = dailyForecasts.size

    class DayForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}