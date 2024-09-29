package pronenko.ghinfo.data

import pronenko.ghinfo.api.retrofitGitHub
import pronenko.ghinfo.models.GitHubUser
import pronenko.ghinfo.models.User

class Repository {

    private suspend fun getFollowers(login: String) = retrofitGitHub.getFollowers(login)

    suspend fun searchUsers(query: String): List<User> {
        val usersGitHub: List<GitHubUser> = retrofitGitHub.searchUsers(query)
        val users: MutableList<User> = mutableListOf()

        usersGitHub.forEach {
            users.add(
                User(
                    login = it.login,
                    avatar = it.avatar_url,
                    followersCount = getFollowers(it.login).size
                )
            )
        }

        return users.toList()
    }
}
