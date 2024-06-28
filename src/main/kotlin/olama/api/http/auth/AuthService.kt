package olama.api.http.auth

import olama.api.http.OlamaWebClient
import olama.api.telegram.model.olama.user.AuthOlamaUser
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Component
class AuthService(val webClient: WebClient) {

    private var olamaUser: AuthOlamaUser? = null;

    fun getAuthUser(): AuthOlamaUser {
        if (olamaUser != null) {
            return olamaUser as AuthOlamaUser;
        
        }
        return authorize();
    }

    // https://docs.openwebui.com/getting-started/env-configuration
    private fun authorize(): AuthOlamaUser {
        // todo: научиться login
        val authUrl = "/api/v1/auths/signin"
        val body = webClient.post()
            .uri(authUrl)
            .body(BodyInserters.fromValue("{\"email\":\"\",\"password\":\"\"}"))
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .retrieve()
            .bodyToMono(AuthOlamaUser::class.java)
            .block()

        olamaUser = body
        return olamaUser as AuthOlamaUser
    }
}