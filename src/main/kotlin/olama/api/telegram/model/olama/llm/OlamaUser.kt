package olama.api.telegram.model.olama.llm

data class OlamaUser(
    val email: String,
    val id: String,
    val name: String,
    val profile_image_url: String,
    val role: String
)