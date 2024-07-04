package olama.api.telegram.model.chat.response

data class InfoX(
    val eval_count: Int,
    val eval_duration: Long,
    val load_duration: Int,
    val prompt_eval_count: Int,
    val prompt_eval_duration: Long,
    val total_duration: Long
)