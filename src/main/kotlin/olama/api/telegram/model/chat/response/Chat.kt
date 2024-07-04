package olama.api.telegram.model.chat.response

data class Chat(
    val history: History,
    val id: String,
    val messages: List<Message>,
    val models: List<String>,
    val options: Options,
    val tags: List<Any>,
    val timestamp: Long,
    val title: String
)