import java.io.File
import java.io.FileNotFoundException
import java.lang.IndexOutOfBoundsException

fun main() {
    val fileName = RecordParser().javaClass.classLoader.getResource("Test_Input_A.txt")
    val parser = RecordParser()
    val records = parser.parseFile(File(fileName!!.toURI()))


    val sorted = records!!.sortedBy { it.gender }
    for(name in sorted){
        println(name.gender)
    }
}

data class Record(
    var lastName: String = "Doe",
    var firstName: String = "John",
    var gender: String = "Male",
    var favoriteColor: String = "Red",
    var DOB: String = "01/01/1999"
)

class RecordParser {
    fun parseFile(file: File):ArrayList<Record>?{
        val records = ArrayList<Record>()
        try {
            file.forEachLine {
                val record = parseLine(it)
                if (record != null)
                    records.add(record)
            }
            return records
        } catch (e: FileNotFoundException) {
            println("File not found!")
        }
        return null
    }

    private fun parseLine(line: String): Record? {
        var deliminator = " "
        if (line.contains("|")) deliminator = " | " else if (line.contains(",")) deliminator = ", "
        try {
            val components = line.split(deliminator)
            return Record(
                lastName = components[0],
                firstName = components[1],
                gender = components[2],
                favoriteColor = components[3],
                DOB = components[4]
            )
        } catch (e: IndexOutOfBoundsException) {
            println("Index out of bounds! - ParseLine")
        }
        return null
    }
}