package com.zimstudy.app.ai

import com.zimstudy.app.data.PerformanceRecord
import com.zimstudy.app.data.PerformanceRepository


class PerformanceTracker(
    private val repository: PerformanceRepository
) {


    fun recordExamResult(
        subject: String,
        evaluation: AnswerEvaluation
    ) {


        val accuracy =
            (evaluation.score.toDouble() /
                    evaluation.totalMarks.toDouble()) * 100



        repository.addRecord(

            PerformanceRecord(

                subjectName = subject,

                quizAccuracy = accuracy,

                pastPaperAccuracy = accuracy,

                consistency = 100.0

            )

        )

    }

}
