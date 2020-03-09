package com.kolbesw.RecordManager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.FileNotFoundException
import java.lang.reflect.InvocationTargetException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private var fileLocation: String? = null
private var displayOption: Int = 0

@SpringBootApplication
class RecordManagerApplication

fun main(args: Array<String>) {
    if (args.isNotEmpty())
        fileLocation = args[0]
    if (args.size == 2)
        try {
            val sortNum = args[1].toInt()
            if(sortNum in 0..2) {
                displayOption = sortNum
            }
        }catch (e:InvocationTargetException){
            println("Failed to parse input ordering argument. Input must be between 0-2. Try:")
            println("java -jar <TARGET_JAR> <TARGET_FILE> <ORDERING_INT>")
        }
    runApplication<RecordManagerApplication>(*args)
}


data class Record(
        var lastName: String = "Doe",
        var firstName: String = "John",
        var gender: String = "Male",
        var favoriteColor: String = "Red",
        var DOB: LocalDate = LocalDate.of(1999, 1, 1)
)

@RestController
class RecordService() {
    final val records = ArrayList<Record>()
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy")

    init {
        println("Starting RESTful service...")
        if(fileLocation != null) {
            val parsed = parseFile(File(fileLocation!!))
            if(parsed){
                println()
                when(displayOption){
                    0-> println("Records sorted by gender:")
                    1-> println("Records sorted by birthday:")
                    2-> println("Records sorted by name:")
                }
                for(record in getSortedRecords(displayOption)){
                    println(record)
                }
                println()
            }
        }
    }

    final fun parseFile(file: File): Boolean {
        return try {
            file.forEachLine {
                val record = parseLine(it)
                if (record != null) {
                    records.add(record)
                }
            }
            println("File successfully parsed!")
            true
        } catch (e: FileNotFoundException) {
            println("File not found!")
            false
        }
    }

    final fun getSortedRecords(option: Int): List<Record> {
        when (option) {
            0 -> {
                val males = records.filter { it.gender == "Male" }
                val females = records.filter { it.gender == "Female" }
                val mSorted = males.sortedBy { it.lastName }
                val fSorted = females.sortedBy { it.lastName }
                return fSorted + mSorted
            }
            1 -> return records.sortedBy { it.DOB }
            2 -> return records.sortedByDescending { it.lastName }
        }
        return records
    }

    fun parseLine(line: String): Record? {
        var deliminator = " "
        val record: Record?

        if (line.contains("|")) deliminator = " | " else if (line.contains(",")) deliminator = ", "

        try {
            val components = line.split(deliminator)
            record = Record(
                    lastName = components[0],
                    firstName = components[1],
                    gender = components[2],
                    favoriteColor = components[3],
                    DOB = LocalDate.parse(components[4], formatter)
            )
        } catch (e: IndexOutOfBoundsException) {
            throw e
        } catch (d: DateTimeParseException) {
            throw d
        }
        return record
    }

    @PostMapping("/records")
    fun `create new record from line input`(@RequestBody line: String): Record? {
        val newRecord = parseLine(line)
        if (newRecord != null)
            records.add(newRecord)
        return newRecord
    }

    @GetMapping("/records/gender")
    fun `get records sorted by gender`() =
            getSortedRecords(0)

    @GetMapping("/records/birthdate")
    fun `get records sorted by birthday`() =
            getSortedRecords(1)

    @GetMapping("/records/name")
    fun `get records sorted by name`() =
            getSortedRecords(2)
}