package pronenko.ghinfo.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pronenko.ghinfo.data.Repository
import pronenko.ghinfo.domain.PreferencesManager
import pronenko.ghinfo.models.User

class SearchScreenViewModel(
    val repository: Repository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    var queryStateFlow =
        MutableStateFlow(value = TextFieldValue(preferencesManager.lastSearchQuery ?: ""))
    var usersStateFlow = MutableStateFlow<List<User>>(value = emptyList())

    fun saveValue(query: TextFieldValue) {
        queryStateFlow.value = query
        preferencesManager.lastSearchQuery = query.text
    }

    fun search(query: TextFieldValue) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.text.isNotBlank()) {
                usersStateFlow.value = repository.searchUsers(query = query.text)
            } else usersStateFlow.value = emptyList()
        }
    }
}
