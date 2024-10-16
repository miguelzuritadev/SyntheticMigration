package migration

import java.io.File
import java.util.regex.Pattern


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

    fun findAndReplaceContent(arrayListOf: List<ReplaceItem>, content: String): String {
        var newContent = content
        for (item in arrayListOf) {
            newContent = newContent.replace(item.oldName, item.newName)
        }
        return newContent
    }

    /**
     * Extract all android:id values from the XML content
     */
    fun extractIdsFromLayout(content: String): List<String> {
        val idPattern = Pattern.compile("""android:id="@\+id/(\w+)"""")
        val matcher = idPattern.matcher(content)
        val ids = arrayListOf<String>()

        while (matcher.find()) {
            ids.add(matcher.group(1))
        }

        return ids
    }

    fun convertToReplaceItems(ids: List<String>, syntheticMigration: SyntheticMigration): List<ReplaceItem> {
        val replaceItems = ids.map {
            val oldName = it
            val newName = "binding." + syntheticMigration.convertSnakeToCamelCase(it)
            ReplaceItem(oldName, newName)
        }.toList()
        return replaceItems
    }

}