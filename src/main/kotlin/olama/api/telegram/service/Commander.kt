package olama.api.telegram.service

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class Commander {

    fun createMessage(chatId: String, text: String) =
            SendMessage(chatId, text)
                    .apply { enableMarkdown(true) }
                    .apply { disableWebPagePreview() }

}