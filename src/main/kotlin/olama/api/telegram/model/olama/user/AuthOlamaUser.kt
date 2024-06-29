package olama.api.telegram.model.olama.user

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthOlamaUser(
    val email: String,
    val id: String,
    val name: String,
    @JsonProperty("profile_image_url")
    val profileImageUrl: String,
    val role: String,
    val token: String,
    @JsonProperty("token_type")
    val tokenType: String
)