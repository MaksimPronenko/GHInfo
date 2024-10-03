package pronenko.ghinfo.ui.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import pronenko.ghinfo.models.GitHubUser

class ProfileScreenViewModel: ViewModel() {
    var profileStateFlow = MutableStateFlow<GitHubUser?>(value = null)

    fun updateProfileState(userProfile: GitHubUser?) {
        profileStateFlow.value = userProfile
    }
}