package olama.api.telegram.command

enum class CommandName(val text: String) {
    START("start"),
    SUM("sum"),
    BUTTONS("buttons"),
    AVAILABLE_MODELS("availableModels"),
}