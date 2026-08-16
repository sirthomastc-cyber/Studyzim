package com.zimstudy.app.planner

import com.zimstudy.app.data.PerformanceRepository
import com.zimstudy.app.data.StudyAnalytics
import com.zimstudy.app.data.StudySession


class AdaptivePlanner(
    private val repository: PerformanceRepository,
    private val analytics: StudyAnalytics
) {


    fun generatePlan(
        availableMinutes: Int,
        sessions: List<StudySession> = emptyList()
    ): StudyRecommendation {


        val records =
            repository.getAllRecords()


        val subject =
            records.minByOrNull {

                (
                it.quizAccuracy +
                it.pastPaperAccuracy +
                it.consistency
                ) / 3

            }?.subjectName
                ?: "General"


        return StudyRecommendation(

            subject = subject,

            topic = "Priority Revision",

            reason =
            "AI selected this from your performance data.",

            durationMinutes =
            availableMinutes

        )

    }

}
