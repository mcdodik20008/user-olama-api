package olama.api.telegram.model.olama.llm

import com.fasterxml.jackson.annotation.JsonProperty

data class Ollama(
        val details: Details,
        val digest: String,
        val model: String,
        @JsonProperty("modified_at")
        val modifiedAt: String,
        val name: String,
        val size: Long,
        val urls: List<Int>
)