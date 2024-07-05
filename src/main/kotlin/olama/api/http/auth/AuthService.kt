package olama.api.http.auth

import olama.api.telegram.model.OUser
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Component
class AuthService(val webClient: WebClient) {

    private var olamaUser: OUser? = null;

    fun getAuthUser(): OUser {
        return olamaUser ?: authorize()
    }

    private fun authorize(): OUser {
        // todo: научиться login
        val authUrl = "/api/v1/auths/signin"
        val body = webClient.post()
            .uri(authUrl)
            .body(BodyInserters.fromValue("{\"email\":\"\",\"password\":\"\"}"))
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .retrieve()
            .bodyToMono(OUser::class.java)
            .block()

        olamaUser = body
        return olamaUser as OUser
    }

}