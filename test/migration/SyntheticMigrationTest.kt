package migration

import org.junit.Assert.assertEquals
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
        val expected = "activity_constraint_layout"

        val syntheticMigration = SyntheticMigration()

        val filePath = Paths.get("test", "assets", "myFile.kt").toAbsolutePath().toString()

        val content = syntheticMigration.readFile(filePath)

        val actual = syntheticMigration.extractLayoutFileName(content)
        println("Found: $actual")

        assertEquals(expected, actual)
    }

    /**
     * testListAllOcurrencesBasedOnPattern
     */
    @Test
    fun testExtractIdsFromLayout() {
        val expected = listOf(
            "margin_start",
            "margin_end",
            "margin_top",
            "margin_bottom",
            "center_text_view",
            "start_text_view",
            "end_text_view",
            "top_text_view",
            "bottom_text_view",
            "reference_text_view",
            "my_view_snake_component"
        )
        val syntheticMigration = SyntheticMigration()
        val filePath = Paths.get("test", "assets", "activity_constraint_layout.xml").toAbsolutePath().toString()

        val content = syntheticMigration.readFile(filePath)
        val actual = syntheticMigration.extractIdsFromLayout(content)

        assertEquals(expected, actual)
    }


    @Test
    fun testFindAndReplaceContent() {
        val expected = """
            class MyClass {
                fun myFunction() {
                    val binding.myViewSnakeComponent: String = ""
                    var myLayout = "R.layout.activity_constraint_layout"
                    var myLayout2 = "R.layout.my_view_snake2"

                    val nuevo = binding.myViewSnakeComponent.plus(myLayout)
                }
            }
        """.trimIndent()

        val replaceItemList = arrayListOf(ReplaceItem("my_view_snake_component", "binding.myViewSnakeComponent"))

        val syntheticMigration = SyntheticMigration()

        val filePath = Paths.get("test", "assets", "myFile.kt").toAbsolutePath().toString()

        val content = syntheticMigration.readFile(filePath)

        val actual = syntheticMigration.findAndReplaceContent(replaceItemList, content).trimIndent()
        println("Found: $actual")

        assertEquals(expected, actual)
    }

    @Test
    fun testGetFolderPathFromParams() {
        // Add test implementation here
    }
}