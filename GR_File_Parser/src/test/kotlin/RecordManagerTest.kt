import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class RecordManagerTest {
    private val manager = RecordManager()
    val fileName = RecordManager().javaClass.classLoader.getResource("Test_Input_A.txt")

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
    fun `when file is parsed a list is returned`(){

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