package com.example.internshiptasks.Task9_Weather_and_News_app.Weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R

class ForecastAdapter(
    private val forecastList: ArrayList<ForecastModel>
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvForecastDate: TextView =
            itemView.findViewById(R.id.tvForecastDate)

        val tvForecastIcon: TextView =
            itemView.findViewById(R.id.tvForecastIcon)

        val tvForecastCondition: TextView =
            itemView.findViewById(R.id.tvForecastCondition)

        val tvForecastTemperature: TextView =
            itemView.findViewById(R.id.tvForecastTemperature)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_weather_forecast_task9,
                parent,
                false
            )

        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ForecastViewHolder,
        position: Int
    ) {

        val forecast = forecastList[position]

        holder.tvForecastDate.text =
            forecast.date

        holder.tvForecastCondition.text =
            forecast.condition

        holder.tvForecastTemperature.text =
            "${forecast.minTemperature}° / ${forecast.maxTemperature}°"

        holder.tvForecastIcon.text =
            getWeatherIcon(forecast.condition)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    private fun getWeatherIcon(
        condition: String
    ): String{

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