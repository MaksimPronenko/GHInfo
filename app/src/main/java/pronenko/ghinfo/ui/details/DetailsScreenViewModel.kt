package pronenko.ghinfo.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pronenko.ghinfo.data.Repository
import pronenko.ghinfo.models.GitHubRepo

class DetailsScreenViewModel(val repository: Repository) : ViewModel() {

    var repositoriesStateFlow = MutableStateFlow<List<GitHubRepo>>(value = emptyList())

    fun loadRepositories(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoriesStateFlow.value = repository.getRepositories(login)
        }
    }
}
