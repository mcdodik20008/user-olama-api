package olama.api.http

import com.fasterxml.jackson.databind.ObjectMapper
import olama.api.telegram.model.olama.user.AuthOlamaUser
import org.jvnet.hk2.annotations.Service
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Service
@Component
class OlamaWebClient(val webClient: WebClient, val objectMapper: ObjectMapper) {

    private var olamaUser : AuthOlamaUser? = null;

    fun authorize() : AuthOlamaUser? {
        val authUrl = "/api/v1/auths/signin"

        val body = webClient.post()
                .uri(authUrl)
                .body(BodyInserters.fromValue("{\"email\":\"\",\"password\":\"\"}"))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(AuthOlamaUser::class.java)
                .block()

        olamaUser = body
        return olamaUser;
    }

    fun <T> get(url: String, aClass: Class<T>): T? {
        if (olamaUser == null) {
            authorize();
        }
        val body = webClient.get()
                .uri(url)
                .header("authorization", (olamaUser?.token_type ?: "" ) + " " + (olamaUser?.token ?: "" ))
                .retrieve()
                .bodyToMono(aClass)
                .block()

        return body;
    }
}
