package olama.api.telegram.command.olama;

import olama.api.http.OlamaWebClient
import olama.api.telegram.command.CommandName.AVAILABLE_MODELS
import olama.api.telegram.model.LlamaData
import olama.api.telegram.service.Commander
import olama.api.telegram.service.TextEditor
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class AvailableModels(var commander: Commander, var olamaWebClient: OlamaWebClient, var textEditor: TextEditor) :
    BotCommand(AVAILABLE_MODELS.text, "Описание команды") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        try {
            val modelsMono = olamaWebClient.get("/api/models", LlamaData::class.java)
            modelsMono.subscribe { models ->
                val nameModels = models.data.map { x -> x.name }
                val message = textEditor.createMarkdownList(nameModels)
                absSender.execute(commander.createMessage(chat.id.toString(), message))
            }
        } catch (e: Exception) {
            absSender.execute(commander.createMessage(chat.id.toString(), "Произошла ошибка при получении моделей: ${e.message}"))
        }
    }
}
