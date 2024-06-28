package olama.api.http

import com.fasterxml.jackson.databind.ObjectMapper
import olama.api.http.auth.AuthService
import org.jvnet.hk2.annotations.Service
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Service
@Component
class OlamaWebClient(val authService: AuthService, val webClient: WebClient, val objectMapper: ObjectMapper) {

    fun <T> get(url: String, aClass: Class<T>): T {
        val body = webClient.get()
            .uri(url)
            .header("authorization", getToken())
            .retrieve()
            .bodyToMono(aClass)
            .block()
        if (body == null) {
            throw Exception()
        }
        return body
    }

    fun <T> post(url: String, jsonBody:String, aClass: Class<T>): T {
        // todo: научиться login
        val authUrl = "/api/v1/auths/signin"
        val body = webClient.post()
            .uri(url)
            .body(BodyInserters.fromValue(jsonBody))
            .header("authorization", getToken())
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .retrieve()
            .bodyToMono(aClass)
            .block()
        if (body == null) {
            throw Exception()
        }
        return body;
    }

    fun <T> put(url: String, jsonBody:String, aClass: Class<T>): T {
        val body = webClient.put()
            .uri(url)
            .body(BodyInserters.fromValue(jsonBody))
            .header("authorization", getToken())
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .retrieve()
            .bodyToMono(aClass)
            .block()
        if (body == null) {
            throw Exception()
        }
        return body;
    }

    fun <T> del(url: String, aClass: Class<T>): T {
        val body = webClient.delete()
            .uri(url)
            .header("authorization", getToken())
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .retrieve()
            .bodyToMono(aClass)
            .block()
        if (body == null) {
            throw Exception()
        }
        return body;
    }

    private fun getToken():String{
        val authUser = authService.getAuthUser()
        return (authUser.token_type ?: "") + " " + (authUser.token ?: "")
    }
}
