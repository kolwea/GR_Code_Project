import java.io.File
import java.io.FileNotFoundException
import java.lang.IndexOutOfBoundsException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.collections.ArrayList

fun main() {
    val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")
    val recordManager = RecordManager()

    val fileName = RecordManager().javaClass.classLoader.getResource("Test_Input_A.txt")

    if(recordManager.parseFile(File(fileName!!.toURI()))) {
        val sortedRecords = recordManager.getSortedRecords(1)
        for(record in sortedRecords){
            println("${record.firstName} ${record.lastName} ${record.favoriteColor} ${record.gender} ${record.DOB.format(formatter)}")
        }
    }
}

data class Record(
    var lastName: String = "Doe",
    var firstName: String = "John",
    var gender: String = "Male",
    var favoriteColor: String = "Red",
    var DOB: LocalDate = LocalDate.of(1999,1,1)
)

class RecordManager{
    private val records = ArrayList<Record>()
    private val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")

    fun parseFile(file: File): Boolean {
        try {
            file.forEachLine {
                val record = parseLine(it)
                if (record != null) {
                    records.add(record)
                }
            }
            return true
        } catch (e: FileNotFoundException) {
            println("File not found!")
            throw e
        }
    }

    fun getSortedRecords(option: Int):List<Record>{
        when (option) {
            0 -> {
                val males = records.filter { it.gender=="Male" }
                val females = records.filter { it.gender=="Female" }
                val mSorted = males.sortedBy { it.lastName }
                val fSorted = females.sortedBy { it.lastName }
                return fSorted + mSorted
            }
            1 -> return records.sortedBy { it.DOB }
            2 -> return records.sortedByDescending { it.lastName }
        }
        return records
    }

    fun getRecords():ArrayList<Record>{
        return records
    }

    //Helper functions
    fun parseLine(line: String): Record? {
        var deliminator = "   "
        var record:Record? = null

        if (line.contains("|")) deliminator = " | " else if (line.contains(",")) deliminator = ", "
        try {
            val components = line.split(deliminator)
            record = Record(
                lastName = components[0],
                firstName = components[1],
                gender = components[2],
                favoriteColor = components[3],
                DOB = LocalDate.parse(components[4],formatter)
            )
        } catch (e: IndexOutOfBoundsException) {
            println("Index out of bounds! - ParseLine")
            println(e)
        }catch (d:DateTimeParseException){
            println("Unable to parse date!")
            println(d)
        }
        return record
    }

}