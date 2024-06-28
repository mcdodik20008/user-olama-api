package olama.api.telegram.service

import org.springframework.stereotype.Component

@Component
class TextEditor {

    fun createMarkdownList(strings: List<String>): String {
        val markdownList = strings.map { "- $it" }.joinToString("\n")
        return "```\n$markdownList\n```"
    }

}