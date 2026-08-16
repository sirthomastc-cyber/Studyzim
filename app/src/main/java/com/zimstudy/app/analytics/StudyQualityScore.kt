package com.zimstudy.app.analytics

object StudyQualityScore {

    fun calculate(
        focusTime: Double,
        accuracy: Double,
        consistency: Double
    ): Double {

        val score =
            (focusTime * 0.4) +
            (accuracy * 0.4) +
            (consistency * 0.2)

        return score.coerceIn(0.0,100.0)
    }
}
