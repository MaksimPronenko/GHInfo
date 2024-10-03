package pronenko.ghinfo.domain

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun convertToDate(dateString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateString)
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val formattedDate = zonedDateTime.format(outputFormatter)
    return formattedDate
}