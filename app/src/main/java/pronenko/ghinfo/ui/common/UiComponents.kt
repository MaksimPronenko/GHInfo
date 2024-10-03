package pronenko.ghinfo.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.Dp
import pronenko.ghinfo.R

const val SCREEN_SEARCH = "searchScreen"
const val SCREEN_DETAIL = "detailScreen"
const val SCREEN_PROFILE = "profileScreen"

const val KEY_LOGIN = "login"
const val LAST_SEARCH = "last_search_query"

@Composable
fun MainText(
    text: String,
    paddingStart: Dp = dimensionResource(id = R.dimen.main_text_padding_start),
    paddingEnd: Dp = dimensionResource(id = R.dimen.main_text_padding_end),
    color: Color = Color.Black
) {
    val context = LocalContext.current
    val textSize = with(LocalDensity.current) { context.resources.getDimension(R.dimen.text_size_main).toSp() }

    Text(
        modifier = Modifier
            .padding(start = paddingStart, end = paddingEnd),
        text = text,
        color = color,
        fontSize = textSize,
        fontWeight = SemiBold
    )
}

@Composable
fun HeaderText(
    text: String,
    color: Color = Color.White
) {
    val context = LocalContext.current
    val textSize = with(LocalDensity.current) { context.resources.getDimension(R.dimen.text_size_table_header).toSp() }

    Text(
        text = text,
        color = color,
        fontSize = textSize,
        fontWeight = Normal
    )
}

@Composable
fun IconButton(
    icon: Int,
    paddingEnd: Dp = dimensionResource(id = R.dimen.icon_button_padding_end),
    enable: Boolean = true,
    onClick: () -> Unit
) {
    val iconButtonPaddingStart = dimensionResource(id = R.dimen.icon_button_padding_start)
    val iconButtonBoxSize = dimensionResource(id = R.dimen.icon_button_box_size)
    val iconSize = dimensionResource(id = R.dimen.icon_size)
    val searchButtonCorners = dimensionResource(id = R.dimen.search_button_corners)

    Box(
        modifier = Modifier
            .padding(start = iconButtonPaddingStart, end = paddingEnd)
            .size(iconButtonBoxSize)
            .clip(RoundedCornerShape(searchButtonCorners))
            .background(color = ProjectLLBlue)
            .clickable(
                enabled = enable,
                onClick = { onClick() }
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .size(iconSize)
        )
    }
}
