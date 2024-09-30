package pronenko.ghinfo.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import pronenko.ghinfo.R
import pronenko.ghinfo.ui.common.ProjectGray

@Composable
fun DetailsScreen(innerPadding: PaddingValues, navController: NavHostController, login: String) {
    val viewModel: DetailsScreenViewModel = koinViewModel<DetailsScreenViewModel>()

//    val weatherListState = viewModel.weatherListStateFlow.collectAsState()
    val errorState = viewModel.errorStateFlow.collectAsState()

    val context = LocalContext.current
    val textSize =
        with(LocalDensity.current) { context.resources.getDimension(R.dimen.text_size).toSp() }

    val detailsTopFieldHeight = dimensionResource(id = R.dimen.details_top_field_height)
    val detailsTopTextPaddingStart = dimensionResource(id = R.dimen.details_top_text_padding_start)
    val garbageButtonPaddingEnd = dimensionResource(id = R.dimen.garbage_button_padding_end)
    val weatherColumnPaddingVertical =
        dimensionResource(id = R.dimen.weather_column_padding_vertical)
    val errorBoxHeight = dimensionResource(id = R.dimen.error_box_height)
    val errorBoxWidth = dimensionResource(id = R.dimen.error_box_width)
    val errorBoxCorners = dimensionResource(id = R.dimen.error_box_corners)
    val errorBoxPadding = dimensionResource(id = R.dimen.error_box_padding)

    LaunchedEffect(login) {
        if (login.isNotBlank()) viewModel.loadWeather(login)
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(detailsTopFieldHeight)
                .background(color = ProjectGray),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
//            IconButton(
//                icon = R.drawable.arrow_back,
//                onClick = { navController.popBackStack() }
//            )
//            MainText(text = city, paddingStart = detailsTopTextPaddingStart)
//            Spacer(modifier = Modifier.weight(1f))
//            IconButton(
//                icon = R.drawable.garbage_basket,
//                paddingEnd = garbageButtonPaddingEnd,
//                onClick = {
//                    viewModel.deleteCity(city)
//                    navController.popBackStack()
//                }
//            )
        }
        if (!errorState.value) {
            Column(
                modifier = Modifier
                    .padding(vertical = weatherColumnPaddingVertical)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
//                weatherListState.value.forEach { weather -> WeatherData(weather) }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(errorBoxHeight)
                        .width(errorBoxWidth)
                        .background(
                            color = ProjectGray,
                            shape = RoundedCornerShape(errorBoxCorners)
                        )
                        .padding(errorBoxPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.no_data),
                        color = Color.Black,
                        fontSize = textSize,
                        fontWeight = SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

//@Composable
//fun UserData(user: User) {
//    val weatherDataRowHeight = dimensionResource(id = R.dimen.user_data_row_height)
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(weatherDataRowHeight),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        WeatherDataText(text = weather.date)
//        WeatherDataText(text = weather.temp.toString())
//    }
//}

@Composable
fun WeatherDataText(text: String) {
    val context = LocalContext.current
    val textSizeSmall = with(LocalDensity.current) {
        context.resources.getDimension(R.dimen.text_size_small).toSp()
    }

    val weatherDataTextPaddingStart =
        dimensionResource(id = R.dimen.weather_data_text_padding_start)
    val weatherDataTextPaddingEnd = dimensionResource(id = R.dimen.weather_data_text_padding_end)

    Text(
        modifier = Modifier
            .padding(start = weatherDataTextPaddingStart, end = weatherDataTextPaddingEnd),
        text = text,
        color = Color.Black,
        fontSize = textSizeSmall,
        fontWeight = SemiBold
    )
}