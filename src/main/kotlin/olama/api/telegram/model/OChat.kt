package olama.api.telegram.model

data  class OChat (
        val id: String,
        val userId: String,
        val title: String,
        val chat: String,
        val createdAt: Long,
        val updatedAt: Long,
        val shareId: String? = null,
        val archived: Boolean = false
)

// Chat form
data class ChatForm(
        val chat: Map<String, Any>
)

// Chat response model
data class ChatResponse(
        val id: String,
        val userId: String,
        val title: String,
        val chat: Map<String, Any>,
        val updatedAt: Long,
        val createdAt: Long,
        val shareId: String?,
        val archived: Boolean
)