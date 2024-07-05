package olama.api.telegram

import olama.api.telegram.service.ChatManager
import olama.api.telegram.service.Commander
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TelegaBot(
    commands: Set<BotCommand>,
    @Value("\${telegram.token}") token: String,
    @Value("\${telegram.botName}") val botName: String = "",
    var commander: Commander,
    val chatManager: ChatManager
) : TelegramLongPollingCommandBot(token) {

    override fun getBotUsername(): String = botName

    override fun processNonCommandUpdate(update: Update) {
        if (update.hasMessage()) {
            val chatId = update.message.chatId.toString()
            if (update.message.hasText()) {
                val oResponse: String = chatManager.sendMessage(update.message.chatId.toString(), update.message.text)
                execute(commander.createMessage(chatId, "Вы написали: *${oResponse}*"))
            } else {
                execute(commander.createMessage(chatId, "Я понимаю только текст!"))
            }
        }
    }

    init {
        registerAll(*commands.toTypedArray())
    }
}