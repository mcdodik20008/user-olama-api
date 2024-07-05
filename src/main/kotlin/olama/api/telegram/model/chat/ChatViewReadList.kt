package olama.api.telegram.model.chat

class ChatViewReadList : ArrayList<ChatListItem>()

data class ChatListItem(
    val id: String,
    val title: String,
    val updated_at: Long,
    val created_at: Long
)