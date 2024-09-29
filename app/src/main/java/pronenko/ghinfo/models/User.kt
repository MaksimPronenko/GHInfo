package pronenko.ghinfo.models

data class User(
    val login: String,
    val avatar: String,
    val followersCount: Int
)
