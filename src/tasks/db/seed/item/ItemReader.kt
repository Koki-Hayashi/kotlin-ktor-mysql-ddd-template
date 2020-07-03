package com.templatePJ.tasks.db.seed.item

import com.templatePJ.util.io.CSVReader
import org.apache.commons.csv.CSVRecord
import java.io.Reader

class ItemReader(reader: Reader) : CSVReader<ItemRecord>(reader) {
    override fun CSVRecord.parse(): ItemRecord =
        ItemRecord(
            name = column("name"),
            userId = column("userId").toInt()
        )
}
