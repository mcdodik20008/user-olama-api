package olama.api.telegram.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LlamaData(
        val data: List<LlamaEntry>
)

data class LlamaEntry(
        val id: String,
        val name: String,
        @JsonProperty("object")
        val aObject: String,
        val created: Long,
        @JsonProperty("owned_by")
        val ownedBy: String,
        val ollama: Ollama
)

data class Ollama(
        val name: String,
        val model: String,
        @JsonProperty("modified_at")
        val modifiedAt: String?,
        val size: Long?,
        val digest: String?,
        val details: Details?,
        val urls: List<Int>?
)

data class Details(
        val parentModel: String?,
        val format: String?,
        val family: String?,
        val families: List<String>?,
        @JsonProperty("parameter_size")
        val parameterSize: String?,
        @JsonProperty("quantization_level")
        val quantizationLevel: String?
)

