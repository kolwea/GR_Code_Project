package com.kolbesw.RecordManager

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate
import java.time.format.DateTimeParseException

internal class Context

class RecordManagerTest {

    private var recordService: RecordService = RecordService()
    private val fileName = Context().javaClass.classLoader.getResource("Test_Input_A.txt")

    @Test
    fun `assert correct bool on pass or fail`() {
        assertFalse( recordService.parseFile(File("dasdasfas sadsadasda")))
        assert(recordService.parseFile(File(fileName!!.toURI())))
    }

    @Test
    fun `assert throws exception when parsing line fails`() {
        assertThrows<IndexOutOfBoundsException> { recordService.parseLine("sdasdsa, asadsds") }
    }

    @Test
    fun `assert throws exception when parsing date fails`() {
        assertThrows<DateTimeParseException> { recordService.parseLine("Bray | Patrick | Male | Orange | 12/21972") }
    }

    @Test
    fun `parsing line returns correct record - pipe`() {
        val record = Record("Smith", "Karl", "Male", "Blue", LocalDate.of(2010, 2, 12))
        assertEquals(record, recordService.parseLine("Smith | Karl | Male | Blue | 2/12/2010"))
    }

    @Test
    fun `parsing line returns correct record - comma`() {
        val record = Record("Smith", "Karl", "Male", "Blue", LocalDate.of(2010, 2, 12))
        assertEquals(record, recordService.parseLine("Smith, Karl, Male, Blue, 2/12/2010"))
    }

    @Test
    fun `parsing line returns correct record - space`() {
        val record = Record("Smith", "Karl", "Male", "Blue", LocalDate.of(2010, 2, 12))
        assertEquals(record, recordService.parseLine("Smith Karl Male Blue 2/12/2010"))
    }

    @Test
    fun `when file is parsed correctly returns true`() {
        assert(recordService.parseFile(File(fileName!!.toURI())))
    }

    @Test
    fun `correct number of records are returned`() {
        val manager = RecordService()
        val file = File(fileName!!.toURI())
        var i = 0
        file.forEachLine { i++ }
        manager.parseFile(file)
        var records = manager.getSortedRecords(0)
        assertEquals(i, records.size)
        records = manager.getSortedRecords(1)
        assertEquals(i, records.size)
        records = manager.getSortedRecords(2)
        assertEquals(i, records.size)
    }

    @Test
    fun `returns correct sorted list - option 0`() {
        recordService.parseFile(File(fileName!!.toURI()))
        val records = recordService.`get records sorted by gender`()
        assertEquals(recordService.getSortedRecords(0), records)
    }

    @Test
    fun `returns correct sorted list - option 1`() {
        recordService.parseFile(File(fileName!!.toURI()))
        val records = recordService.`get records sorted by birthday`()
        assertEquals(recordService.getSortedRecords(1), records)
    }

    @Test
    fun `returns correct sorted list - option 2`() {
        recordService.parseFile(File(fileName!!.toURI()))
        val records = recordService.`get records sorted by name`()
        assertEquals(recordService.getSortedRecords(2), records)
    }

    @Test
    fun `adding new entry via rest`() {
        recordService.`create new record from line input`("Green John Male Red 1/4/1932")
        assert(recordService.records.contains(
                Record("Green", "John", "Male", "Red", LocalDate.of(1932, 1, 4))
        ))
    }


}
