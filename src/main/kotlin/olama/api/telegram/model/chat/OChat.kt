package olama.api.telegram.model.chat

data class OChat(
    val id: String?,
    val user_id: String?,
    val title: String?,
    val chat: ChatData?,  // Используем существующий класс ChatData
    val tags: List<String>? = null,
    val timestamp: Long? = null,
    val updated_at: Long? = null,
    val created_at: Long? = null,
    val share_id: String? = null,  // Может быть null, поэтому тип String?
    val archived: Boolean = false
)