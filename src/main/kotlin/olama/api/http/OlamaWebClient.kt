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

    fun <T> get(url: String, aClass: Class<T>): Mono<T> {
        return webClient.get()
                .uri(url)
                .header("authorization", getToken())
                .retrieve()
                .bodyToMono(aClass)
    }

    fun <T> post(url: String, jsonBody: String, aClass: Class<T>): Mono<T> {
        return webClient.post()
                .uri(url)
                .body(BodyInserters.fromValue(jsonBody))
                .header("authorization", getToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(aClass)
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
