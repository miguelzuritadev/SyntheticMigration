import migration.SyntheticMigration
import java.io.File


/**
 * based on file path received as parameter, read file content, extract layout file name and ids
 * then replace it in the content
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Please provide the file path as a parameter.")
        return
    }
    val layoutFilePath = args[0]
    val filePath = args[1]
    val syntheticMigration = SyntheticMigration()
    val content = syntheticMigration.readFile(filePath)
    val layoutFileName = syntheticMigration.extractLayoutFileName(content)
//    println("layoutFileName:$layoutFileName")
    val layoutFileNamePath = "$layoutFilePath\\$layoutFileName.xml"
//    println("layoutFileNamePath:$layoutFileNamePath")
    val layout = syntheticMigration.readFile(layoutFileNamePath)
    val ids = syntheticMigration.extractIdsFromLayout(layout)
    val replaceItems = syntheticMigration.convertToReplaceItems(ids, syntheticMigration)
    //    println("replaceItems:$replaceItems")
    val newContent = syntheticMigration.findAndReplaceContent(replaceItems, content)
//    println(newContent)
    val file = File(filePath)
    file.writeText(newContent)
}

