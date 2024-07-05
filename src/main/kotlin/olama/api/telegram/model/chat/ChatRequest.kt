package olama.api.telegram.model.chat

data class ChatRequest(
    val model: String,
    val messages: MutableList<Message>,
    val options: HashMap<String, String>?,
    val citations: Boolean = false,
    val chat_id: String
)