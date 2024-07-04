package olama.api.telegram.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OChat(
        val id: String,
        val userId: String,
        val title: String,
        val chat: String,
        val createdAt: Long,
        val updatedAt: Long,
        val shareId: String? = null,
        val archived: Boolean = false
)

data class ChatCreate(val chat: ChatForm)

data class ChatForm(
        val id: String,
        val models: List<String>,
        val timestamp: Int,
        val title: String
)

data class ChatResponse(
        val id: String,
        @JsonProperty(value = "user_id")
        val userId: String,
        val title: String,
        val chat: Map<String, Any>,
        @JsonProperty(value = "updated_at")
        val updatedAt: Long,
        @JsonProperty(value = "created_at")
        val createdAt: Long,
        @JsonProperty(value = "share_id")
        val shareId: String?,
        val archived: Boolean
)