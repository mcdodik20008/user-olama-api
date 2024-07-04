package olama.api.telegram.model.chat.response

data class Message(
    val childrenIds: List<String>,
    val content: String,
    val context: String,
    val done: Boolean,
    val id: String,
    val info: InfoX,
    val lastSentence: String,
    val model: String,
    val modelName: String,
    val models: List<String>,
    val parentId: String,
    val role: String,
    val timestamp: Int,
    val userContext: String
)