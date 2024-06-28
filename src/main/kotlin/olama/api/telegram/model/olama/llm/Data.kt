package olama.api.telegram.model.olama.llm

data class Data(
        val created: Int,
        val id: String,
        val name: String,
        val `object`: String,
        val ollama: Ollama,
        val owned_by: String
)