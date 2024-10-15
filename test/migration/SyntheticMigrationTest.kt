package migration

import org.junit.Assert.*
import org.junit.Test
import java.nio.file.Paths

class SyntheticMigrationTest {
    @Test
    fun testReadFile() {
        val syntheticMigration = SyntheticMigration()

        val filePath = Paths.get("test", "assets", "myFile.kt").toAbsolutePath().toString()

        val result = syntheticMigration.readFile(filePath)
        println("File content: $result")
    }

    @Test
    fun testGetAllFilenameFromFolder() {

    }

    @Test
    fun testConvertSnakeToCamelCase() {
        val expected = "myViewSnakeComponent"

        val syntheticMigration = SyntheticMigration()
        val actual = syntheticMigration.convertSnakeToCamelCase("my_view_snake_component")

        assertEquals(expected, actual)
    }

    @Test
    fun testExtractLayoutFileName() {
        val expected = "my_view_snake"

        val syntheticMigration = SyntheticMigration()

        val filePath = Paths.get("test", "assets", "myFile.kt").toAbsolutePath().toString()

        val content = syntheticMigration.readFile(filePath)

        val actual = syntheticMigration.extractLayoutFileName(content)
        println("Found: $actual")

        assertEquals(expected, actual)
    }

    @Test
    fun testFindAndReplaceContent() {
        val expected = "myViewSnakeComponent"

        val syntheticMigration = SyntheticMigration()

        val filePath = Paths.get("test", "assets", "myFile.kt").toAbsolutePath().toString()

        val content = syntheticMigration.readFile(filePath)

        val actual = syntheticMigration.findAndReplaceContent(arrayListOf(expected), content)
        println("Found: $actual")

        assertEquals(expected, actual)
    }

    @Test
    fun testListAllOcurrencesBasedOnPattern() {
        // Add test implementation here
    }

    @Test
    fun testGetFolderPathFromParams() {
        // Add test implementation here
    }
}