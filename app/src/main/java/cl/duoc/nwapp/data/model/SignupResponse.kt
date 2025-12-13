package cl.duoc.nwapp.data.model

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("authToken") val authToken: String
)
