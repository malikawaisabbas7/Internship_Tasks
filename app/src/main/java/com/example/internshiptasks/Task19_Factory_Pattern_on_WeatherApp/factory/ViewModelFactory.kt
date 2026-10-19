
package com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.repository.NewsRepository
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.repository.WeatherRepository
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.viewmodel.NewsViewModel
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.viewmodel.WeatherViewModel

class ViewModelFactory(
    private val weatherRepository: WeatherRepository,
    private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        return when {
            modelClass.isAssignableFrom(
                WeatherViewModel::class.java
            ) -> WeatherViewModel(weatherRepository) as T

            modelClass.isAssignableFrom(
                NewsViewModel::class.java
            ) -> NewsViewModel(newsRepository) as T

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}
