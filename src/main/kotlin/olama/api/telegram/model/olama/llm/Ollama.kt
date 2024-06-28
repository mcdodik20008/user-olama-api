package olama.api.telegram.model.olama.llm

data class Ollama(
        val details: Details,
        val digest: String,
        val model: String,
        val modified_at: String,
        val name: String,
        val size: Long,
        val urls: List<Int>
)