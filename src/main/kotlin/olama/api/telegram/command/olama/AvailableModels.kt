package olama.api.telegram.command.olama;

import olama.api.http.OlamaWebClient
import olama.api.telegram.command.CommandName.AVAILABLE_MODELS
import olama.api.telegram.model.olama.llm.OlamaModels
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
        val modelsMono = olamaWebClient.get("/api/models", OlamaModels::class.java)
        val nameModels = modelsMono.data.map { x -> x.id }
        val message = textEditor.createMarkdownList(nameModels.orEmpty())
        absSender.execute(commander.createMessage(chat.id.toString(), message))
    }

}