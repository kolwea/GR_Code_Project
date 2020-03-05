import java.io.File
import java.io.FileNotFoundException

fun main(){
    val parser = RecordParser()
    val fileName = RecordParser().javaClass.classLoader.getResource("Test_Input_A.txt")
    parser.parseFile(File(fileName!!.toURI()))
}

data class Record( val lastName:String = "Doe", val firstName:String = "John",val gender:String = "Male", val DOB:String = "01/01/1999")

class RecordParser{
    private val records = ArrayList<Record>()

    //Parses given file and adds containing records to list
    fun parseFile(file: File){
        try {
            file.forEachLine {
                val record = parseLine(it)
                records.add(record)
            }
        }catch (e:FileNotFoundException){
            println("File not found!")
        }
    }

    private fun parseLine(line:String):Record{
        val components = line.split(" | ")
        for(part in components){
            println(part)
        }
        return Record(firstName = "Ducky")
    }

    fun sortRecords(option:Int){
        when(option){
            0->{}
            1->{}
            2->{}
        }
    }
}