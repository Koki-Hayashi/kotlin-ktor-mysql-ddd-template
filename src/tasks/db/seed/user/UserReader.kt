package com.templatePJ.tasks.db.seed.user

import com.templatePJ.util.io.CSVReader
import org.apache.commons.csv.CSVRecord
import java.io.Reader

class UserReader(reader: Reader) : CSVReader<UserRecord>(reader) {
    override fun CSVRecord.parse(): UserRecord =
        UserRecord(
            name = column("name")
        )
}
