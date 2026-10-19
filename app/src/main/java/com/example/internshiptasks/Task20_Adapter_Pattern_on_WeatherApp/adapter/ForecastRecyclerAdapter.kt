package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.ForecastModel

class ForecastRecyclerAdapter(
    private var forecastList: List<ForecastModel> = emptyList()
) : RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvForecastDate: TextView =
            itemView.findViewById(R.id.tvForecastDate)

        val tvForecastTemperature: TextView =
            itemView.findViewById(R.id.tvForecastTemperature)

        val tvForecastIcon: TextView =
            itemView.findViewById(R.id.tvForecastIcon)


        val tvForecastCondition: TextView =
            itemView.findViewById(R.id.tvForecastCondition)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_forecast_task11, parent, false)

        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ForecastViewHolder,
        position: Int
    ) {
        val forecast = forecastList[position]

        holder.tvForecastTemperature.text =
            "${forecast.minTemperature}° / ${forecast.maxTemperature}°"

        holder.tvForecastIcon.text =
            getWeatherIcon(forecast.condition)

        holder.tvForecastDate.text = forecast.date
        holder.tvForecastCondition.text = forecast.condition
    }

    override fun getItemCount(): Int = forecastList.size

    fun updateForecast(newForecast: List<ForecastModel>) {
        forecastList = newForecast
        notifyDataSetChanged()
    }

    private fun getWeatherIcon(
        condition: String
    ): String {

        return when {
            condition.contains(
                "clear",
                ignoreCase = true
            ) -> "☀️"

            condition.contains(
                "cloud",
                ignoreCase = true
            ) -> "⛅"

            condition.contains(
                "rain",
                ignoreCase = true
            ) -> "🌧️"

            condition.contains(
                "Drizzle",
                ignoreCase = true
            ) -> "🌦️"

            condition.contains(
                "Thunderstorm",
                ignoreCase = true
            ) -> "⛈️"

            condition.contains(
                "snow",
                ignoreCase = true
            ) -> "❄️"

            condition.contains(
                "Fog",
                ignoreCase = true
            ) -> "🌫️"

            else -> "🌤️"

        }
    }
}
