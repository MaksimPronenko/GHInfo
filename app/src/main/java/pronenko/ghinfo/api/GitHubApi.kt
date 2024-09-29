package pronenko.ghinfo.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pronenko.ghinfo.models.GitHubUser
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): List<GitHubUser>
    @GET("users/{login}/followers")
    suspend fun getFollowers(
        @Path("login") login: String
    ): List<GitHubUser>
}

val retrofitGitHub: GitHubApi = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl("https://api.github.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(GitHubApi::class.java)
