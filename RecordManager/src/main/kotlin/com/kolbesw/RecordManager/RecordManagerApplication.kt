package com.kolbesw.RecordManager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

var fileLocation: String? = null
var displayOption: Int = 0

@SpringBootApplication
class RecordManagerApplication {
    private final val recordService = RecordService()

    init {
        if (fileLocation != null) {
            val file = File(fileLocation!!)
            if (file.exists()) {
                recordService.parseFile(file)
                println("Entries:")
                for (entry in recordService.getSortedRecords(displayOption)) {
                    println(entry)
                }
                println()
            }
        }
    }
}

fun main(args: Array<String>) {
    if (args.isNotEmpty())
        fileLocation = args[0]
    if (args.size == 2)
        displayOption = args[1].toInt()
    else {
        println("Invalid input. Try 'target.jar <FILE_LOCATION?> <OUTPUT_OPTION_INT?>'")
        return
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
            throw e
        }
    }

    fun getSortedRecords(option: Int): List<Record> {
        if (records.isEmpty() && fileLocation != null)
            parseFile(File(fileLocation))
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

    //Helper functions
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
        return parseLine(line)
    }

    @GetMapping("/records/gender")
    fun `get records sorted by gender`(@RequestParam(value = "sortBy", defaultValue = "gender") sortBy: String) =
            getSortedRecords(0)

    @GetMapping("/records/birthdate")
    fun `get records sorted by birthday`(@RequestParam(value = "sortBy", defaultValue = "gender") sortBy: String) =
            getSortedRecords(1)

    @GetMapping("/records/name")
    fun `get records sorted by name`(@RequestParam(value = "sortBy", defaultValue = "gender") sortBy: String) =
            getSortedRecords(2)
}