package migration

import java.io.File
import java.util.ArrayList


class SyntheticMigration {

    val layoutRootPattern = "R.layout."

    /**
     * Read file content from any folder
     */
    fun readFile(fileName: String): String {
        return File(fileName).readText()
    }

    fun convertSnakeToCamelCase(variable: String): String {
        val words = variable.split("_")
        var camelCase = words[0]
        for (i in 1 until words.size) {
            camelCase += words[i].capitalize()
        }
        return camelCase
    }

    private fun findFirstUsageInContent(pattern: String, content: String): String {
        val regex = Regex(pattern)
        val match = regex.find(content)
        return match?.value ?: ""
    }

    fun extractLayoutFileName(content: String): String {
        val findFirstUsageInContent = findFirstUsageInContent("$layoutRootPattern*", content)
        return findFirstUsageInContent.replace(layoutRootPattern, "").replace("\"", "").trim()
    }

    fun findAndReplaceContent(arrayListOf: ArrayList<String>, content: String): String {
        var newContent = content
        for (item in arrayListOf) {
            val snakeCase = item.replace("Component", "").replace("View", "").replace("Fragment", "")
            val camelCase = convertSnakeToCamelCase(snakeCase)
            newContent = newContent.replace(snakeCase, camelCase)
        }
        return newContent
    }

}