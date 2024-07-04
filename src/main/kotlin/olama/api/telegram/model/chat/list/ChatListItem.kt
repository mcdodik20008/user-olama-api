package olama.api.telegram.model.chat.list

data class ChatListItem(
    val created_at: Int,
    val id: String,
    val title: String,
    val updated_at: Int
)