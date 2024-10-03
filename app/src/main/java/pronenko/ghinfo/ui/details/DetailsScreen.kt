package pronenko.ghinfo.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import pronenko.ghinfo.R
import pronenko.ghinfo.domain.convertToDate
import pronenko.ghinfo.models.GitHubRepo
import pronenko.ghinfo.ui.common.MainText
import pronenko.ghinfo.ui.common.ProjectBlue
import pronenko.ghinfo.ui.common.ProjectLightBlue

@Composable
fun DetailsScreen(innerPadding: PaddingValues, login: String) {
    val viewModel: DetailsScreenViewModel = koinViewModel<DetailsScreenViewModel>()

    val repositoriesState = viewModel.repositoriesStateFlow.collectAsState()

    val screenMainPadding = dimensionResource(id = R.dimen.screen_main_padding)
    val detailsTitlePaddingHorizontal =
        dimensionResource(id = R.dimen.details_title_padding_horisontal)
    val detailsTitlePaddingVertical = dimensionResource(id = R.dimen.details_title_padding_vertical)
    val repositoriesVerticalSpacer = dimensionResource(id = R.dimen.repositories_vertical_spacer)
    val title = stringResource(R.string.repositories) + " $login"

    LaunchedEffect(login) {
        if (login.isNotBlank()) viewModel.loadRepositories(login)
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ProjectBlue)
                .padding(
                    horizontal = detailsTitlePaddingHorizontal,
                    vertical = detailsTitlePaddingVertical
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainText(
                text = title,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = screenMainPadding)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            repositoriesState.value.forEach { repository ->
                Spacer(modifier = Modifier.height(repositoriesVerticalSpacer))
                RepositoryData(
                    repository = repository
                )
            }
        }
    }
}

@Composable
fun RepositoryData(
    repository: GitHubRepo
) {
    val context = LocalContext.current
    val textSizeSmall =
        with(LocalDensity.current) { context.resources.getDimension(R.dimen.text_size_small).toSp() }

    val paddingMainHalf = dimensionResource(id = R.dimen.padding_main_half)
    val repositoryCorners = dimensionResource(id = R.dimen.repository_corners)
    val date = stringResource(id = R.string.updated) + " " + convertToDate(repository.updated_at)
    val defaultBranch = stringResource(id = R.string.default_branch) + " " + repository.default_branch
    val forksCount = stringResource(id = R.string.forks_count) + " ${repository.forks_count}"
    val starsCount = stringResource(id = R.string.stars_count) + " ${repository.stargazers_count}"

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(repositoryCorners))
            .background(color = ProjectLightBlue)
            .padding(paddingMainHalf)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MainText(text = repository.name)
        }
        if(!repository.description.isNullOrBlank()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                text = repository.description,
                color = Color.Black,
                fontSize = textSizeSmall,
                fontWeight = Normal
            )
        }
        if(!repository.language.isNullOrBlank()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = repository.language,
                color = Color.Black,
                fontSize = textSizeSmall,
                fontWeight = Normal
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = date,
            color = Color.Black,
            fontSize = textSizeSmall,
            fontWeight = Normal
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = defaultBranch,
            color = Color.Black,
            fontSize = textSizeSmall,
            fontWeight = Normal
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = forksCount,
            color = Color.Black,
            fontSize = textSizeSmall,
            fontWeight = Normal
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = starsCount,
            color = Color.Black,
            fontSize = textSizeSmall,
            fontWeight = Normal
        )
    }
}
