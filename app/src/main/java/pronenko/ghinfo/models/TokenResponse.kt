package pronenko.ghinfo.models

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token") val access_token: String
)
