import migration.SyntheticMigration
import java.io.File


fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Please provide the file path as a parameter.")
        return
    }
    //based on file path received as parameter, read file content, extract layout file name and ids
    //then replace it in the content
    val filePath = args[0]
    val layoutfilePath = args[1]
    val syntheticMigration = SyntheticMigration()
    val content = syntheticMigration.readFile(filePath)
    val layoutFileName = syntheticMigration.extractLayoutFileName(content)
//    println("layoutFileName:$layoutFileName")
    val layoutFileNamePath = "$layoutfilePath\\$layoutFileName.xml"
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

