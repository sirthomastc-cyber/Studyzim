package com.zimstudy.app.analytics

object GradePredictor {

    fun predict(mastery: Double): String {

        return when {

            mastery >= 80 ->
                "A"

            mastery >= 65 ->
                "B"

            mastery >= 50 ->
                "C"

            mastery >= 40 ->
                "D"

            else ->
                "Needs Improvement"
        }
    }
}
