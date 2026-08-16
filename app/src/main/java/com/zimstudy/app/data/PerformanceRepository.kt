package com.zimstudy.app.data


class PerformanceRepository {


    private val records =
        mutableListOf<PerformanceRecord>()



    fun addRecord(
        record: PerformanceRecord
    ){

        records.removeAll {
            it.subjectName == record.subjectName
        }

        records.add(record)

    }



    fun getRecord(
        subjectName: String
    ): PerformanceRecord? {

        return records.find {
            it.subjectName == subjectName
        }

    }



    fun getAllRecords(): List<PerformanceRecord>{

        return records.toList()

    }


}
