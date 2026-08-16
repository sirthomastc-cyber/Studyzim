package com.zimstudy.app.analytics

object MasteryCalculator {

    fun calculate(
        quizAccuracy: Double,
        pastPaperAccuracy: Double,
        consistency: Double
    ): Double {

        val mastery =
            (quizAccuracy * 0.4) +
            (pastPaperAccuracy * 0.4) +
            (consistency * 0.2)

        return mastery.coerceIn(0.0, 100.0)
    }
}
