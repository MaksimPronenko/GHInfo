package pronenko.ghinfo.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import org.koin.androidx.compose.koinViewModel
import pronenko.ghinfo.R
import pronenko.ghinfo.models.User
import pronenko.ghinfo.ui.common.IconButton
import pronenko.ghinfo.ui.common.MainText
import pronenko.ghinfo.ui.common.ProjectBlue
import pronenko.ghinfo.ui.common.SCREEN_DETAIL

@Composable
fun SearchScreen(innerPadding: PaddingValues, navController: NavHostController) {
    val viewModel: SearchScreenViewModel = koinViewModel<SearchScreenViewModel>()

    val queryState = viewModel.queryStateFlow.collectAsState()
    val usersState = viewModel.usersStateFlow.collectAsState()
    val showRepositories: (String) -> Unit = { it ->
        if (it.isNotBlank()) navController.navigate("$SCREEN_DETAIL/$it")
    }

    val context = LocalContext.current
    val textSize = with(LocalDensity.current) { context.resources.getDimension(R.dimen.text_size).toSp() }

    val screenMainPadding = dimensionResource(id = R.dimen.screen_main_padding)
    val searchFieldHeight = dimensionResource(id = R.dimen.search_field_height)
    val searchFieldBorderWidth = dimensionResource(id = R.dimen.search_field_border_width)
    val searchBorderCorners = dimensionResource(id = R.dimen.search_border_corners)
    val searchFieldPaddingEnd = dimensionResource(id = R.dimen.search_field_padding_end)
    val spacerHeight = dimensionResource(id = R.dimen.spacer_height)

    LaunchedEffect(queryState.value.text) {
        if (queryState.value.text.isNotBlank()) viewModel.search(queryState.value)
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(screenMainPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(searchFieldHeight)
                .border(width = searchFieldBorderWidth, color = ProjectBlue, shape = RoundedCornerShape(searchBorderCorners)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                icon = R.drawable.search,
                enable = false,
                onClick = {}
            )
            Box(
                modifier = Modifier
                    .padding(end = searchFieldPaddingEnd)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (queryState.value.text.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.search_field),
                        color = Color.Black,
                        fontSize = textSize,
                        fontWeight = SemiBold,
                    )
                }
                BasicTextField(
                    value = queryState.value,
                    singleLine = true,
                    onValueChange = { viewModel.search(it) },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontWeight = SemiBold,
                        fontSize = textSize
                    ),
                    modifier = Modifier.testTag("SearchTextField")
                )
            }
        }
        Spacer(modifier = Modifier.height(spacerHeight))
        Column(
            modifier = Modifier
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            usersState.value.forEach { user ->
                UserData(
                    user = user,
                    showRepositories = showRepositories
                )
            }
        }
    }
}

//@Composable
//fun LocalityButton(
//    name: String,
//    loadWeather: (String) -> Unit
//) {
//    val buttonHeight = dimensionResource(id = R.dimen.button_height)
//    val paddingStart = dimensionResource(id = R.dimen.button_text_padding_start)
//    val paddingEnd = dimensionResource(id = R.dimen.button_text_padding_end)
//    Box(
//        modifier = Modifier
//            .height(buttonHeight)
//            .clickable { loadWeather(name) },
//        contentAlignment = Alignment.CenterStart
//    ) {
//        MainText(
//            text = name,
//            paddingStart = paddingStart,
//            paddingEnd = paddingEnd
//        )
//    }
//}

@Composable
fun UserData(
    user: User,
    showRepositories: (String) -> Unit
) {
    val userDataRowHeight = dimensionResource(id = R.dimen.user_data_row_height)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(userDataRowHeight)
            .clickable { showRepositories(user.login) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(data = user.avatar)
        MainText(text = user.login, paddingStart = dimensionResource(id = R.dimen.screen_main_padding))
        MainText(text = user.followersCount.toString())
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Avatar(
    data: String?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    if (data.isNullOrBlank() ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_face_24),
            contentDescription = contentDescription,
            modifier = Modifier.size(dimensionResource(id = R.dimen.avatar_size)),
            contentScale = contentScale
        )
    } else {
        GlideImage(
            model = data,
            contentDescription = contentDescription,
            modifier = Modifier.size(dimensionResource(id = R.dimen.avatar_size)),
            contentScale = contentScale
        )
    }
}
