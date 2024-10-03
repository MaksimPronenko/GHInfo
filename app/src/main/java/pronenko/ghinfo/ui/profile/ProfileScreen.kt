package pronenko.ghinfo.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import pronenko.ghinfo.R
import pronenko.ghinfo.domain.getAuthorizationUrl
import pronenko.ghinfo.ui.common.MainText
import pronenko.ghinfo.ui.search.Avatar

@Composable
fun ProfileScreen(innerPadding: PaddingValues) {
    val viewModel: ProfileScreenViewModel = koinViewModel<ProfileScreenViewModel>()

    val profileState = viewModel.profileStateFlow.collectAsState()

    val context = LocalContext.current

    val screenMainPadding = dimensionResource(id = R.dimen.screen_main_padding)
    val spacerHeight = dimensionResource(id = R.dimen.spacer_height)
    val profileAvatarSize = dimensionResource(id = R.dimen.profile_avatar_size)

    val email = stringResource(R.string.email) + " ${profileState.value?.email}"
    val publicRepos = stringResource(R.string.public_repos) + " ${profileState.value?.public_repos}"

    LaunchedEffect(Unit) {
        val authorizationUrl = getAuthorizationUrl()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authorizationUrl))
        context.startActivity(intent)
    }

    if (profileState.value != null) {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(screenMainPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MainText(text = profileState.value!!.login)
                Avatar(data = profileState.value!!.avatar_url, avatarSize = profileAvatarSize)
            }
            if(!profileState.value?.email.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(spacerHeight))
                MainText(text = email, paddingStart = screenMainPadding, paddingEnd = screenMainPadding)
            }
            Spacer(modifier = Modifier.height(spacerHeight))
            MainText(text = publicRepos, paddingStart = screenMainPadding, paddingEnd = screenMainPadding)
        }
    }
}
