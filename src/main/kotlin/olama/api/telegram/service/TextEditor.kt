package olama.api.telegram.service

import org.springframework.stereotype.Component

@Component
class TextEditor {

    fun createMarkdownList(strings: List<String>): String {
        val markdownList = strings.joinToString("\n") { "- $it" }
        return "```\n$markdownList\n```"
    }

}