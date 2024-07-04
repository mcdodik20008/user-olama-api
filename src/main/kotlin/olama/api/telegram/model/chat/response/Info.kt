package olama.api.telegram.model.chat.response

data class Info(
    val eval_count: Int,
    val eval_duration: Long,
    val load_duration: Int,
    val prompt_eval_count: Int,
    val prompt_eval_duration: Int,
    val total_duration: Long
)