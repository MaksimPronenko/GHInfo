package pronenko.ghinfo.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pronenko.ghinfo.data.Repository

class ProfileScreenViewModel(val repository: Repository): ViewModel() {
    var localitiesStateFlow = MutableStateFlow<List<String>>(value = emptyList())

    fun getSavedCities() {
        viewModelScope.launch(Dispatchers.IO) {
//            localitiesStateFlow.value = repository.getAllLocalities()
        }
    }
}