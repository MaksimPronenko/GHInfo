package pronenko.ghinfo.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pronenko.ghinfo.data.Repository

class DetailsScreenViewModel(val repository: Repository) : ViewModel() {
//    private var cityWeatherCurrent: WeatherTable? = null
//    var weatherListStateFlow = MutableStateFlow<List<WeatherTable>>(value = emptyList())
    var errorStateFlow = MutableStateFlow<Boolean>(value = false)

    fun loadWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loadWeatherFromDB(city)
//            cityWeatherCurrent = repository.getWeather(city)
//            if (cityWeatherCurrent != null) {
//                repository.insertWeather(cityWeatherCurrent!!)
//            }
            loadWeatherFromDB(city)
//            errorStateFlow.value = weatherListStateFlow.value.isEmpty()
        }
    }
    private fun loadWeatherFromDB(city: String) {
//        weatherListStateFlow.value = repository.getWeatherList(query = city)
    }
    fun deleteCity(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteCity(name)
        }
    }
}
