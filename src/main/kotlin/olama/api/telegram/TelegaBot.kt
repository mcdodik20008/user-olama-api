package olama.api.telegram

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
        var commander: Commander
) : TelegramLongPollingCommandBot(token) {

    @Value("\${telegram.botName}")
    private val botName: String = ""

    override fun getBotUsername(): String = botName

    override fun processNonCommandUpdate(update: Update) {
        if (update.hasMessage()) {
            val chatId = update.message.chatId.toString()
            if (update.message.hasText()) {
                execute(commander.createMessage(chatId, "Вы написали: *${update.message.text}*"))
            } else {
                execute(commander.createMessage(chatId, "Я понимаю только текст!"))
            }
        }
    }

    init {
        registerAll(*commands.toTypedArray())
    }
}