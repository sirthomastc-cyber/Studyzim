package com.zimstudy.app.analytics

import com.zimstudy.app.data.PerformanceRepository


class MasteryService(
    private val repository: PerformanceRepository
) {


    fun getSubjectMastery(
        subjectName: String
    ): Double {


        val record =
            repository.getRecord(subjectName)
                ?: return 0.0



        return MasteryCalculator.calculate(
            quizAccuracy = record.quizAccuracy,
            pastPaperAccuracy = record.pastPaperAccuracy,
            consistency = record.consistency
        )

    }



    fun getSubjectGrade(
        subjectName: String
    ): String {


        val mastery =
            getSubjectMastery(subjectName)


        return GradePredictor.predict(
            mastery
        )

    }


}
