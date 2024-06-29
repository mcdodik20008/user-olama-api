package olama.api.telegram.model.olama.llm

import com.fasterxml.jackson.annotation.JsonProperty

data class OlamaUser(
    val email: String,
    val id: String,
    val name: String,
    @JsonProperty("profile_image_url")
    val profileImageUrl: String,
    val role: String
)