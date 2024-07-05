package olama.api.telegram.model

import com.fasterxml.jackson.annotation.JsonProperty

class OUser(
        val id: String = "",
        val name: String = "",
        val email: String = "",
        val role: String = "",
        @JsonProperty("profile_image_url")
        val profileImageUrl: String = "",
        @JsonProperty("last_active_at")
        val lastActiveAt: Long = 1,
        @JsonProperty("updated_at")
        val updatedAt: Long = 1,
        @JsonProperty("created_at")
        val createdAt: Long = 1,
        @JsonProperty("api_key")
        val apiKey: String? = null,
        val settings: Any? = null,  // Replace with appropriate type for settings
        val info: Any? = null,  // Replace with appropriate type for info
        val token: String = "",
        @JsonProperty("token_type")
        val tokenType: String = ""
)