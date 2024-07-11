package olama.api.telegram.command.olama;

import com.fasterxml.jackson.databind.ObjectMapper
import olama.api.http.OlamaWebClient
import olama.api.telegram.command.CommandName.NEW_CHAT
import olama.api.telegram.model.chat.*
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
    private val jacksonObjectMapper: ObjectMapper
) : BotCommand(NEW_CHAT.text, "Описание команды") {

    val nameToChat: MutableMap<String, String> = HashMap()

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        // Создаём чат
        var newChat = createNewChat(arguments, user.userName)
        var body = jacksonObjectMapper.writeValueAsString(newChat)
        // Сохраним созданный чат, либо получим уже существующий
        if (!nameToChat.containsKey(user.userName)) {
            newChat = olamaWebClient.post("api/v1/chats/new", body, OChat::class.java).block()!!
            nameToChat[user.userName] = newChat.id!!;
        } else {
            newChat = olamaWebClient.get("/api/v1/chats/${nameToChat.get(user.userName)}", OChat::class.java).block()!!
        }

        // Отправляем первое сообщение
        val chatRequest: ChatRequest = mapToRequest(newChat)
        body = jacksonObjectMapper.writeValueAsString(chatRequest)
        // Отправляем сообщение, отчет получаем в виде чанков, которые собираются внутри метода
        olamaWebClient.fetchDataFromServer("/ollama/api/chat", body)
            .subscribe { result ->
                // Засылаем в ТГ
                absSender.execute(commander.createMessage(chat.id.toString(), result))
                chatRequest.messages.last().content = result
                body = jacksonObjectMapper.writeValueAsString(chatRequest)
                // Отправляем запрос, что ответ от ламы получен.
                olamaWebClient.post("/api/chat/completed", body, String::class.java)
                    .subscribe { /*res -> println(res.toString()) */ }

                println("Базу вывели")
                olamaWebClient.get("/api/v1/chats/${nameToChat.get(user.userName)}", String::class.java)
                    .subscribe { res -> println(res.toString()) }
            }
    }


    private fun mapToRequest(newChat: OChat): ChatRequest {
        val newChatData = newChat.chat!!
        return ChatRequest(
            newChatData.models?.last()!!,
            newChatData.messages,
            HashMap(),
            false,
            newChat.id!!
        )
    }

    private fun createNewChat(arguments: Array<out String>, userName: String): OChat {

        val userMsg =
            Message(
                arguments.joinToString(" "),
                "user",
                UUID.randomUUID().toString(),
                LocalDate.now().toEpochDay(),
                "llama3:latest",
                "llama3:latest",
                ArrayList(),
                null, null
            )
        val assistMsg =
            Message(
                "",
                "assistant",
                UUID.randomUUID().toString(),
                LocalDate.now().toEpochDay(),
                "llama3:latest",
                "llama3:latest",
                ArrayList(),
                null, userMsg.id
            )
        userMsg.childrenIds = listOf(assistMsg.id)

        val history =
            History(UUID.randomUUID().toString(), mapOf(Pair(assistMsg.id, assistMsg), Pair(userMsg.id, userMsg)))
        val chatData = ChatData("", "",history, listOf(userMsg, assistMsg).toMutableList())
        val oChat = OChat(null, null, null, chatData)

        val requestMessage = UUID.randomUUID()
        val responseMessage = UUID.randomUUID()
        val chatFrom = jacksonObjectMapper.readValue(
            """
{
	"chat": {
		"history": {
			"currentId": "${UUID.randomUUID()}",
			"messages": {
				"${responseMessage}": {
					"childrenIds": [],
					"content": "",
					"id": "${responseMessage}",
					"model": "llama3:latest",
					"modelName": "llama3:latest",
					"parentId": "${requestMessage}",
					"role": "assistant",
					"timestamp": 1720179755,
					"userContext": null
				},
				"${requestMessage}": {
					"childrenIds": [
						"${responseMessage}"
					],
					"content": "${arguments.joinToString(" ")}",
					"id": "${requestMessage}",
					"models": [
						"llama3:latest"
					],
					"parentId": null,
					"role": "user",
					"timestamp": 1720179755
				}
			}
		},
		"id": "",
		"messages": [
			{
				"childrenIds": [
					"${responseMessage}"
				],
				"content": "${arguments.joinToString(" ")}",
				"id": "${requestMessage}",
				"models": [
					"llama3:latest"
				],
				"parentId": null,
				"role": "user",
				"timestamp": 1720179755
			},
			{
				"childrenIds": [],
				"content": "",
				"id": "${responseMessage}",
				"model": "llama3:latest",
				"modelName": "llama3:latest",
				"parentId": "${requestMessage}",
				"role": "assistant",
				"timestamp": 1720179755,
				"userContext": null
			}
		],
		"models": [
			"llama3:latest"
		],
		"options": {},
		"tags": [],
		"timestamp": 1720179755061,
		"title": "${userName}"
	}
}
            """, OChat::class.java
        )
        return chatFrom;
    }
}