package olama.api.http

import olama.api.http.auth.AuthService
import org.jvnet.hk2.annotations.Service
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
@Component
class OlamaWebClient(val authService: AuthService, val webClient: WebClient) {

    fun get(url: String): Mono<String> {
        return webClient.get()
                .uri(url)
                .header("authorization", getToken())
                .retrieve()
                .bodyToMono(String::class.java)
    }

    fun post(url: String, jsonBody: String): Mono<String> {
        return webClient.post()
                .uri(url)
                .body(BodyInserters.fromValue(jsonBody))
                .header("authorization", getToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(String::class.java)
    }

    fun put(url: String, jsonBody: String): Mono<String> {
        return webClient.put()
                .uri(url)
                .body(BodyInserters.fromValue(jsonBody))
                .header("authorization", getToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(String::class.java)
    }

    fun del(url: String): Mono<String> {
        return webClient.delete()
                .uri(url)
                .header("authorization", getToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(String::class.java)
    }

    private fun getToken(): String {
        val authUser = authService.getAuthUser()
        return authUser.tokenType + " " + authUser.token
    }
}
