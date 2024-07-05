package olama.api.telegram.model.chat

data class OChat(
    val id: String?,
    val user_id: String?,
    val title: String?,
    val chat: ChatData?,  // Используем существующий класс ChatData
    val tags: List<String>?,
    val timestamp: Long?,
    val updated_at: Long?,
    val created_at: Long?,
    val share_id: String?,  // Может быть null, поэтому тип String?
    val archived: Boolean = false
)