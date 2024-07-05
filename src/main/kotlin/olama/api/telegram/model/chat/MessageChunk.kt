package olama.api.telegram.model.chat

data class MessageChunk (
    val model: String,
    val created_at: String,
    val message: Message,
    val done_reason: String,
    val done: Boolean,
    val total_duration: Long,
    val load_duration: Long,
    val prompt_eval_count: Int,
    val prompt_eval_duration: Long,
    val eval_count: Int,
    val eval_duration: Long
)