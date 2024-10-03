package pronenko.ghinfo.data

import pronenko.ghinfo.api.retrofitGitHub
import pronenko.ghinfo.models.GitHubRepo
import pronenko.ghinfo.models.GitHubUser
import pronenko.ghinfo.models.GitHubUsersResponse
import pronenko.ghinfo.models.User

class Repository {

    suspend fun searchUsers(query: String): List<User> {
        val response: GitHubUsersResponse? = retrofitGitHub.searchUsers(query)
        val usersGitHub: List<GitHubUser> = response?.items ?: emptyList()
        val users: MutableList<User> = mutableListOf()

        usersGitHub.forEach {
            users.add(
                User(
                    login = it.login,
                    avatar = it.avatar_url,
                    followersCount = getFollowers(
                        it.login
                    ).size
                )
            )
        }

        return users.toList()
    }

    private suspend fun getFollowers(login: String): List<GitHubUser> {
        kotlin.runCatching {
            retrofitGitHub.getFollowers(login)
        }.fold(
            onSuccess = {
                return it ?: emptyList()
            },
            onFailure = {
                return emptyList()
            }
        )
    }

    suspend fun getRepositories(login: String): List<GitHubRepo> {
        kotlin.runCatching {
            retrofitGitHub.getRepositories(login)
        }.fold(
            onSuccess = {
                return it ?: emptyList()
            },
            onFailure = {
                return emptyList()
            }
        )
    }
}
