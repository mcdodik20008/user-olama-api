package olama.api.telegram.model.chat

data class ChatData(
        val history: History?,
        val id: String,
        val messages: MutableList<Message>,
        val models: List<String>,
        val options: Map<String, Any>,
        val tags: List<String>,
        val timestamp: Long,
        val title: String
)