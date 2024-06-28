package olama.api.telegram.command.test

import olama.api.telegram.command.CommandName
import olama.api.telegram.service.Commander
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class StartCommand(var commander: Commander) : BotCommand(CommandName.START.text, "Описание команды") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        absSender.execute(commander.createMessage(chat.id.toString(), "Добро пожаловать!"))
    }

}