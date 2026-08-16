package com.zimstudy.app.ai

import com.zimstudy.app.data.PerformanceRecord
import com.zimstudy.app.data.PerformanceRepository


class LearningProgressManager(
    private val repository: PerformanceRepository
) {


    fun saveExamResult(
        subject: String,
        quizAccuracy: Double,
        pastPaperAccuracy: Double,
        consistency: Double
    ){

        val record = PerformanceRecord(

            subjectName = subject,

            quizAccuracy = quizAccuracy,

            pastPaperAccuracy = pastPaperAccuracy,

            consistency = consistency

        )


        repository.addRecord(record)

    }


}
