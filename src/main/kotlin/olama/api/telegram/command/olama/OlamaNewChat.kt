package olama.api.telegram.command.olama;

import com.fasterxml.jackson.databind.ObjectMapper
import olama.api.http.OlamaWebClient
import olama.api.http.auth.AuthService
import olama.api.telegram.command.CommandName.AVAILABLE_MODELS
import olama.api.telegram.model.olama.chat.OlamaChat
import olama.api.telegram.service.Commander
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import java.time.LocalDate
import java.util.*

@Component
class OlamaNewChat(
    var commander: Commander,
    var olamaWebClient: OlamaWebClient,
    var authService: AuthService,
    private val jacksonObjectMapper: ObjectMapper
) :
    BotCommand(AVAILABLE_MODELS.text, "Описание команды") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val oChat = olamaChat(arguments)
        val body = jacksonObjectMapper.writeValueAsString(oChat)
        val modelsMono = olamaWebClient.post("api/v1/chats/new", body, OlamaChat::class.java)
        absSender.execute(commander.createMessage(chat.id.toString(), "Чат ${modelsMono.title} создан"))
    }

    private fun olamaChat(arguments: Array<out String>): OlamaChat {
        return OlamaChat(
            UUID.randomUUID().toString(),
            arguments.joinToString { " " },
            authService.getAuthUser().id,
            false,
            Integer.parseInt(LocalDate.now().toEpochDay().toString()),
            Integer.parseInt(LocalDate.now().toEpochDay().toString())
        )
    }

}