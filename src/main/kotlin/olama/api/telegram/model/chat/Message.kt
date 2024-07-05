package olama.api.telegram.model.chat

import java.util.*

data class Message(
    var content: String,
    val role: String,
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long? = null,
    val childrenIds: List<String>? = null,
    val models: List<String>? = null,
    val parentId: String? = null,
    val model: String? = null,
    val modelName: String? = null,
    val userContext: Any? = null
)