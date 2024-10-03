package pronenko.ghinfo.models

data class GitHubUser(
    val login: String,
    val avatar_url: String,
    val email: String?,
    val public_repos: Int
)
