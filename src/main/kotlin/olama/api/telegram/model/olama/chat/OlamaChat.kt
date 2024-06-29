package olama.api.telegram.model.olama.chat

import com.fasterxml.jackson.annotation.JsonProperty

data class OlamaChat(
    var id: String,
    var title: String,
    @JsonProperty("user_id")
    var userId: String,
    var archived: Boolean,
    @JsonProperty("created_at")
    var createdAt: Int,
    @JsonProperty("updated_at")
    var updatedAt: Int
)