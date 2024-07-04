package olama.api.telegram.service

import olama.api.http.OlamaWebClient
import olama.api.telegram.model.chat.list.ChatList
import org.springframework.stereotype.Component

@Component
class ChatManager(val olamaWebClient: OlamaWebClient) {



    fun sendMessage(chatId: String, request: String?): String {
        var chatId = olamaWebClient.get("/api/v1/chats/", ChatList::class.java).block()?.get(0)?.id;
       // olamaWebClient.get("/api/v1/chats/"+chatId, )
        return "";
    }

}
