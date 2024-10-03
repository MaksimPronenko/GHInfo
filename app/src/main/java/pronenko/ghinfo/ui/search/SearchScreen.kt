package pronenko.ghinfo.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import pronenko.ghinfo.ui.common.HeaderText
import pronenko.ghinfo.ui.common.IconButton
import pronenko.ghinfo.ui.common.MainText
import pronenko.ghinfo.ui.common.ProjectBlue
import pronenko.ghinfo.ui.common.ProjectLightBlue
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
    val textSizeMain =
        with(LocalDensity.current) { context.resources.getDimension(R.dimen.text_size_main).toSp() }

    val screenMainPadding = dimensionResource(id = R.dimen.screen_main_padding)
    val searchFieldHeight = dimensionResource(id = R.dimen.search_field_height)
    val searchFieldBorderWidth = dimensionResource(id = R.dimen.search_field_border_width)
    val searchBorderCorners = dimensionResource(id = R.dimen.search_border_corners)
    val searchFieldPaddingStart = dimensionResource(id = R.dimen.search_field_padding_start)
    val searchFieldPaddingEnd = dimensionResource(id = R.dimen.search_field_padding_end)
    val spacerHeight = dimensionResource(id = R.dimen.spacer_height)
    val tableVerticalSpacer = dimensionResource(id = R.dimen.table_vertical_spacer)

    LaunchedEffect(Unit) {
        if (queryState.value.text.isNotBlank()) viewModel.search(queryState.value)
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(start = screenMainPadding, end = screenMainPadding, top = screenMainPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(searchFieldHeight)
                .border(
                    width = searchFieldBorderWidth,
                    color = ProjectBlue,
                    shape = RoundedCornerShape(searchBorderCorners)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                icon = R.drawable.search,
                enable = true,
                onClick = { viewModel.search(queryState.value) }
            )
            Box(
                modifier = Modifier
                    .padding(start = searchFieldPaddingStart, end = searchFieldPaddingEnd)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (queryState.value.text.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.search_field),
                        color = Color.Black,
                        fontSize = textSizeMain,
                        fontWeight = SemiBold,
                    )
                }
                BasicTextField(
                    value = queryState.value,
                    singleLine = true,
                    onValueChange = { viewModel.saveValue(it) },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontWeight = SemiBold,
                        fontSize = textSizeMain
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(spacerHeight))
        UsersTableHeader()
        Column(
            modifier = Modifier
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            usersState.value.forEach { user ->
                Spacer(modifier = Modifier.height(tableVerticalSpacer))
                UserData(
                    user = user,
                    showRepositories = showRepositories
                )
            }
        }
    }
}

@Composable
fun UsersTableHeader() {

    val paddingMainHalf = dimensionResource(id = R.dimen.padding_main_half)
    val tableHeaderHeight = dimensionResource(id = R.dimen.table_header_height)
    val tableUsersFirstColumnWidth = dimensionResource(id = R.dimen.table_users_first_column_width)
    val tableUsersLastColumnWidth = dimensionResource(id = R.dimen.table_users_last_column_width)
    val tableHorizontalSpacer = dimensionResource(id = R.dimen.table_horizontal_spacer)
    val tableHeaderCorners = dimensionResource(id = R.dimen.table_header_corners)
    val boxesColor = ProjectBlue

    Row(
        modifier = Modifier
            .height(tableHeaderHeight)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(tableUsersFirstColumnWidth)
                .clip(
                    RoundedCornerShape(
                        topStart = tableHeaderCorners,
                        topEnd = tableHeaderCorners
                    )
                )
                .background(color = boxesColor)
                .padding(horizontal = paddingMainHalf),
            contentAlignment = Alignment.Center
        ) {
            HeaderText(text = stringResource(id = R.string.avatar))
        }
        Spacer(modifier = Modifier
            .width(tableHorizontalSpacer)
            .background(color = Color.White))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = tableHeaderCorners,
                        topEnd = tableHeaderCorners
                    )
                )
                .background(color = boxesColor)
                .padding(horizontal = paddingMainHalf),
            contentAlignment = Alignment.Center
        ) {
            HeaderText(text = stringResource(id = R.string.login))
        }
        Spacer(modifier = Modifier
            .width(tableHorizontalSpacer)
            .background(color = Color.White))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(tableUsersLastColumnWidth)
                .clip(
                    RoundedCornerShape(
                        topStart = tableHeaderCorners,
                        topEnd = tableHeaderCorners
                    )
                )
                .background(color = boxesColor)
                .padding(horizontal = paddingMainHalf),
            contentAlignment = Alignment.Center
        ) {
            HeaderText(text = stringResource(id = R.string.followers))
        }
    }
}

@Composable
fun UserData(
    user: User,
    showRepositories: (String) -> Unit
) {
    val userDataRowHeight = dimensionResource(id = R.dimen.user_data_row_height)
    val paddingMainHalf = dimensionResource(id = R.dimen.padding_main_half)
    val tableUsersFirstColumnWidth = dimensionResource(id = R.dimen.table_users_first_column_width)
    val tableUsersLastColumnWidth = dimensionResource(id = R.dimen.table_users_last_column_width)
    val boxesColor = ProjectLightBlue
    val tableHorizontalSpacer = dimensionResource(id = R.dimen.table_horizontal_spacer)

    Row(
        modifier = Modifier
            .height(userDataRowHeight)
            .fillMaxWidth()
            .clickable { showRepositories(user.login) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(tableUsersFirstColumnWidth)
                .background(color = boxesColor)
                .padding(horizontal = paddingMainHalf),
            contentAlignment = Alignment.Center
        ) {
            Avatar(data = user.avatar)
        }
        Spacer(modifier = Modifier
            .width(tableHorizontalSpacer)
            .background(color = Color.White))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(color = boxesColor)
                .padding(horizontal = paddingMainHalf),
            contentAlignment = Alignment.Center
        ) {
            MainText(text = user.login)
        }
        Spacer(modifier = Modifier
            .width(tableHorizontalSpacer)
            .background(color = Color.White))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(tableUsersLastColumnWidth)
                .background(color = boxesColor)
                .padding(horizontal = paddingMainHalf),
            contentAlignment = Alignment.Center
        ) {
            MainText(text = user.followersCount.toString())
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Avatar(
    data: String?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    val avatarSize = dimensionResource(id = R.dimen.avatar_size)

    if (data.isNullOrBlank()) {
        Image(
            painter = painterResource(id = R.drawable.baseline_face_24),
            contentDescription = contentDescription,
            modifier = Modifier.size(avatarSize),
            contentScale = contentScale
        )
    } else {
        GlideImage(
            model = data,
            contentDescription = contentDescription,
            modifier = Modifier.size(avatarSize),
            contentScale = contentScale
        )
    }
}
