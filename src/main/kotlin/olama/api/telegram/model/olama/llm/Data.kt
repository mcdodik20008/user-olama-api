package olama.api.telegram.model.olama.llm

import com.fasterxml.jackson.annotation.JsonProperty

data class Data(
        val created: Int,
        val id: String,
        val name: String,
        val `object`: String,
        val ollama: Ollama,
        @JsonProperty("owned_by")
        val ownedBy: String
)