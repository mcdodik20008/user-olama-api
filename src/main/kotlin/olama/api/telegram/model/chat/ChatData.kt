package olama.api.telegram.model.chat

data class ChatData(
    val id: String,
    val title: String,
    val history: History?,
    val messages: MutableList<Message>,
    val models: List<String>? = null,
    val options: Map<String, Any>? = null,
    val tags: List<String>? = null,
    val timestamp: Long? = null,

)