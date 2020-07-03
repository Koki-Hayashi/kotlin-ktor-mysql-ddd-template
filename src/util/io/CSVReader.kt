package com.templatePJ.util.io

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.io.Reader

abstract class CSVReader<P>(
    reader: Reader, format: CSVFormat = CSVFormat.DEFAULT
        .withFirstRecordAsHeader(), characterOffSet: Long = 0, recordNumber: Long = 0
) {

    private val parser = CSVParser(reader, format, characterOffSet, recordNumber)

    abstract fun CSVRecord.parse(): P

    fun lines(): Sequence<P> {
        return parser.asSequence().map { it.parse() }
    }

    fun CSVRecord.column(column: String): String {
        return this[column]
    }
}

