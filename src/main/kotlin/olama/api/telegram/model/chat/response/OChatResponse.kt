package olama.api.telegram.model.chat.response

data class OChatResponse(
    val archived: Boolean,
    val chat: Chat,
    val created_at: Int,
    val id: String,
    val share_id: Any,
    val title: String,
    val updated_at: Int,
    val user_id: String
)