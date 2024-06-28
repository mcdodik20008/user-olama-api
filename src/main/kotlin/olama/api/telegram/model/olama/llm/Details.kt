package olama.api.telegram.model.olama.llm

data class Details(
    val families: List<String>,
    val family: String,
    val format: String,
    val parameter_size: String,
    val parent_model: String,
    val quantization_level: String
)