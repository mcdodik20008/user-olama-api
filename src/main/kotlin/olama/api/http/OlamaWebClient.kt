package olama.api.http

import com.google.gson.Gson
import olama.api.http.auth.AuthService
import olama.api.telegram.model.chat.MessageChunk
import org.jvnet.hk2.annotations.Service
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@Component
class OlamaWebClient(val authService: AuthService, val webClient: WebClient) {

    fun <T> get(url: String, aClass: Class<T>): Mono<T> {
        return webClient.get()
                .uri(url)
                .header("authorization", getToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(aClass)
    }

    fun <T> post(url: String, jsonBody: String, aClass: Class<T>): Mono<T> {
        println(jsonBody)
        return webClient.post()
                .uri(url)
                .body(BodyInserters.fromValue(jsonBody))
                .header("authorization", getToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(aClass)
    }

    fun fetchDataFromServer(url: String, jsonBody: String): Mono<String> {
        val gson = Gson()
        println(jsonBody)
        val response: Flux<String> = webClient.post()
                .uri("/ollama/api/chat")  // Укажите путь к вашему API endpoint
                .body(BodyInserters.fromValue(jsonBody))
                .header("authorization", getToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToFlux(String::class.java)

        return response
//                .doOnNext { jsonString ->
//                    println("Received chunk: $jsonString")  // Логирование каждого чанка
//                }
                .map { jsonString ->
                    try {
                        gson.fromJson(jsonString, MessageChunk::class.java)
                    } catch (e: Exception) {
                        println("Error parsing chunk: $e")
                        throw e
                    }
                }
                .takeUntil { chunk -> chunk.done }
                .reduce(StringBuilder()) { sb, chunk -> sb.append(chunk.message.content) }
                .map { sb -> sb.toString() }
                .doOnError { e -> println("Error: $e") }
    }

    fun <T> put(url: String, jsonBody: String, aClass: Class<T>): Mono<T> {
        return webClient.put()
                .uri(url)
                .body(BodyInserters.fromValue(jsonBody))
                .header("authorization", getToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(aClass)
    }

    fun <T> del(url: String, aClass: Class<T>): Mono<T> {
        return webClient.delete()
                .uri(url)
                .header("authorization", getToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(aClass)
    }

    private fun getToken(): String {
        val authUser = authService.getAuthUser()
        return authUser.tokenType + " " + authUser.token
    }
}
