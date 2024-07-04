package olama.api.telegram.model.chat.response

data class History(
    val currentId: String,
    val messages: Map<String, Message>
)