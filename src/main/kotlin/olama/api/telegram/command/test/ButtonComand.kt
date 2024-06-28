package olama.api.telegram.command.test

import olama.api.telegram.command.CommandName
import olama.api.telegram.service.Commander
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class ButtonsCommand(var commander: Commander) : BotCommand(CommandName.BUTTONS.text, "") {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        absSender.execute(
                createMessageWithSimpleButtons(
                        chat.id.toString(),
                        "Нажмите на одну из кнопок.",
                        listOf(
                                listOf("Кнопка 1", "Кнопка 2"),
                                listOf("Кнопка 3", "Кнопка 4"),
                        )
                )
        )
    }

    fun createMessageWithSimpleButtons(chatId: String, text: String, simpleButtons: List<List<String>>) =
            commander.createMessage(chatId, text)
                    .apply {
                        replyMarkup = getSimpleKeyboard(simpleButtons)
                    }

    fun getSimpleKeyboard(allButtons: List<List<String>>): ReplyKeyboard =
            ReplyKeyboardMarkup().apply {
                keyboard = allButtons.map { rowButtons ->
                    val row = KeyboardRow()
                    rowButtons.forEach { rowButton -> row.add(rowButton) }
                    row
                }
                oneTimeKeyboard = true
            }
}