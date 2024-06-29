package olama.api.telegram.model.olama.llm

import com.fasterxml.jackson.annotation.JsonProperty

data class Details(
    val families: List<String>,
    val family: String,
    val format: String,
    @JsonProperty("parameter_size")
    val parameterSize: String,
    @JsonProperty("parent_model")
    val parentModel: String,
    @JsonProperty("quantization_level")
    val quantizationLevel: String
)