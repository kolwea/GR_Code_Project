import java.io.File
import java.io.FileNotFoundException

fun main(){
    val parser = FileParser()
    val fileName = FileParser().javaClass.classLoader.getResource("Test_Input_A.txt")
    parser.parse(File(fileName.toURI()))
}

class FileParser{

    fun parse(file: File){
        try {
            file.forEachLine {
                println(it)
            }
        }catch (e:FileNotFoundException){
            println("File not found!")
        }
    }

}