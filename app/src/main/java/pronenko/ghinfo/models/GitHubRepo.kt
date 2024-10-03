package pronenko.ghinfo.models

data class GitHubRepo(
    val name: String,
    val description: String?,
    val updated_at: String,
    val default_branch: String,
    val forks_count: Int,
    val stargazers_count: Int,
    val language: String?
)
