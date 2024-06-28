package olama.api.telegram.model.olama.user

data class AuthOlamaUser(
    val email: String,
    val id: String,
    val name: String,
    val profile_image_url: String,
    val role: String,
    val token: String,
    val token_type: String
)