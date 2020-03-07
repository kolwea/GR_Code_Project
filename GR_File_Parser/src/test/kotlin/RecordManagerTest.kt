import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate
import java.time.format.DateTimeParseException

internal class Context

internal class RecordManagerTest {

    private var manager = RecordManager()
    private val fileName = Context().javaClass.classLoader.getResource("Test_Input_A.txt")

    @Test
    fun `assert throws exception while file does not exist`(){
        assertThrows<FileNotFoundException>{manager.parseFile(File("dasdasfas sadsadasda"))}
    }

    @Test
    fun `assert throws exception when parsing line fails`(){
        assertThrows<IndexOutOfBoundsException>{manager.parseLine("sdasdsa, asadsds")}
    }

    @Test
    fun `assert throws exception when parsing date fails`(){
        assertThrows<DateTimeParseException>{manager.parseLine("Bray | Patrick | Male | Orange | 12/21972")}
    }

    @Test
    fun `parsing line returns correct record - pipe`() {
        val record = Record("Smith","Karl","Male","Blue", LocalDate.of(2010,2,12))
        assertEquals(record,manager.parseLine("Smith | Karl | Male | Blue | 2/12/2010"))
    }

    @Test
    fun `parsing line returns correct record - comma`() {
        val record = Record("Smith","Karl","Male","Blue", LocalDate.of(2010,2,12))
        assertEquals(record,manager.parseLine("Smith, Karl, Male, Blue, 2/12/2010"))
    }

    @Test
    fun `parsing line returns correct record - space`() {
        val record = Record("Smith","Karl","Male","Blue", LocalDate.of(2010,2,12))
        assertEquals(record,manager.parseLine("Smith Karl Male Blue 2/12/2010"))
    }

    @Test
    fun `when file is parsed correctly returns true`(){
        val manager = RecordManager()
        assert(manager.parseFile(File(fileName!!.toURI())))
    }
    @Test
    fun `correct number of records are returned`(){
        val manager = RecordManager()
        val file = File(fileName!!.toURI())
        var i =0
        file.forEachLine { i++ }
        manager.parseFile(file)
        val records = manager.getRecords()
        assertEquals(i, records.size)
    }

    @Test
    fun `returns correct sorted list - option 0`(){
        val manager = RecordManager()
        manager.parseFile(File(fileName!!.toURI()))
        val records = manager.getRecords()
        val sorted = manager.getSortedRecords(0)
    }





//    @Test
//    fun `parse line throws index out of bounds exception`(){
//        assertThrows<IndexOutOfBoundsException>{manager.parseLine("Weathington | Kolbe | Male | Green")}
//    }

//    @Test
//    fun getSortedRecords() {
//    }
//
//    @Test
//    fun getRecords() {
//    }
}