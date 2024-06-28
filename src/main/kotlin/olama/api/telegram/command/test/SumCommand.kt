package olama.api.telegram.command.test

import olama.api.telegram.command.CommandName
import olama.api.telegram.service.Commander
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SumCommand(var commander: Commander) : BotCommand(CommandName.SUM.text, "Описание команды") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val numbers = arguments.map { it.toInt() }
        val sum = numbers.sum()
        absSender.execute(
                commander.createMessage(
                        chat.id.toString(),
                        numbers.joinToString(separator = " + ", postfix = " = $sum"),
                )
        )
    }
}