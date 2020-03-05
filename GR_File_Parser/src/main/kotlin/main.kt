import java.io.File
import java.io.FileNotFoundException

fun main() {
    val parser = RecordParser()
    val fileName = RecordParser().javaClass.classLoader.getResource("Test_Input_A.txt")
    parser.parseFile(File(fileName!!.toURI()))
}

data class Record(
    val lastName: String = "Doe",
    val firstName: String = "John",
    val gender: String = "Male",
    val favoriteColor: String,
    val DOB: String = "01/01/1999"
)

class RecordParser {
    private val records = ArrayList<Record>()

    //Parses given file and adds containing records to list
    fun parseFile(file: File) {
        try {
            file.forEachLine {
                val record = parseLine(it)
                records.add(record)
            }
        } catch (e: FileNotFoundException) {
            println("File not found!")
        }
        for (record in records) {
            print(record.favoriteColor)
        }
    }

    private fun parseLine(line: String): Record {
        var deliminator = " "
        if (line.contains("|")) deliminator = " | " else if (line.contains(",")) deliminator = ", "
        val components = line.split(deliminator)
        return Record(
            lastName = components[0],
            firstName = components[1],
            gender = components[2],
            favoriteColor = components[3],
            DOB = components[4]
        )
    }

    fun sortRecords(option: Int) {
        when (option) {
            0 -> {
            }
            1 -> {
            }
            2 -> {
            }
        }
    }
}