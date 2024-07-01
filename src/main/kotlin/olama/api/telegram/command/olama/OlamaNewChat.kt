package olama.api.telegram.command.olama;

import com.fasterxml.jackson.databind.ObjectMapper
import olama.api.http.OlamaWebClient
import olama.api.http.auth.AuthService
import olama.api.telegram.command.CommandName.NEW_CHAT
import olama.api.telegram.model.ChatResponse
import olama.api.telegram.model.OChat
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
        BotCommand(NEW_CHAT.text, "Описание команды") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val oChat = olamaChat(arguments)
        val body = jacksonObjectMapper.writeValueAsString(oChat)
        val modelsMono = olamaWebClient.post("api/v1/chats/new", body, ChatResponse::class.java)

        modelsMono.subscribe { oChat ->
            absSender.execute(commander.createMessage(chat.id.toString(), "Чат ${oChat.title} создан"))
        }


    }

    private fun olamaChat(arguments: Array<out String>): OChat {
        return OChat(
                UUID.randomUUID().toString(),
                authService.getAuthUser().id,
                arguments.joinToString { " " },
                "",
                LocalDate.now().toEpochDay().toString().toLong(),
                LocalDate.now().toEpochDay().toString().toLong(),
        )
    }
}