package pronenko.ghinfo.domain

import android.app.Activity
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pronenko.ghinfo.api.retrofitGitHub
import pronenko.ghinfo.api.retrofitService
import pronenko.ghinfo.models.GitHubUser
import pronenko.ghinfo.ui.profile.ProfileScreenViewModel

const val CLIENT_ID = "Ov23lizD9zuIpqQaV6RQ"
const val CLIENT_SECRET = "0d5fe6182c613065d02269aebe8f7d56b4249c55"
const val REDIRECT_URI = "ghinfo://callback"
const val SCOPE = "user:email"

fun getAuthorizationUrl(): String {
    return "https://github.com/login/oauth/authorize" +
            "?client_id=$CLIENT_ID" +
            "&redirect_uri=$REDIRECT_URI" +
            "&scope=$SCOPE"
}

fun exchangeCodeForToken(code: String, activity: Activity, viewModel: ProfileScreenViewModel) {
    CoroutineScope(Dispatchers.IO).launch {
        Log.d("StarResult", "exchangeCodeForToken($code)")
        val responseBody = retrofitService.getAccessToken(code = code)
        val responseString = responseBody.string()
        val params = responseString.split("&").associate {
            val kv = it.split("=")
            kv[0] to kv[1]
        }
        val accessToken = params["access_token"]
        Log.d("StarResult", "accessToken = $accessToken")
        getUserProfile(accessToken) { userProfile ->
            if (userProfile != null) {
                viewModel.updateProfileState(userProfile)
            }
        }
        withContext(Dispatchers.Main) {
            activity.finish()
        }
    }
}

fun getUserProfile(accessToken: String?, onUserProfileFetched: (GitHubUser?) -> Unit) {
    if (accessToken != null) {
        val authHeader = "token $accessToken"
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofitGitHub.getUserProfile(authHeader)
            if (response.isSuccessful) {
                val userProfile = response.body()
                userProfile?.let {
                    Log.d("StarResult", "Username: ${it.login}")
                    Log.d("StarResult", "Avatar URL: ${it.avatar_url}")
                    Log.d("StarResult", "Email: ${it.email}")
                    Log.d("StarResult", "Public Repos: ${it.public_repos}")
                }
                onUserProfileFetched(userProfile)
            } else {
                Log.d("StarResult", "Error fetching user profile: ${response.message()}")
                onUserProfileFetched(null)
            }
        }
    } else {
        onUserProfileFetched(null)
    }
}
