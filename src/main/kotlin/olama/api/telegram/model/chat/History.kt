package olama.api.telegram.model.chat

data class History(
    val currentId: String,
    val messages: Map<String, Message>
)